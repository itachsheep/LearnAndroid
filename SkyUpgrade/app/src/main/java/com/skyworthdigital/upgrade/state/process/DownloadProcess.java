package com.skyworthdigital.upgrade.state.process;

import android.os.Handler;
import android.text.TextUtils;

import com.skyworthdigital.upgrade.R;
import com.skyworthdigital.upgrade.common.IConstants;
import com.skyworthdigital.upgrade.common.MessageEvent;
import com.skyworthdigital.upgrade.common.UpgradeApp;
import com.skyworthdigital.upgrade.db.UpgradeTask;
import com.skyworthdigital.upgrade.state.StateProcess;
import com.skyworthdigital.upgrade.upload.UploadDownloadComplete;
import com.skyworthdigital.upgrade.utils.CommonUtil;
import com.skyworthdigital.upgrade.utils.LogUtil;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.common.task.PriorityExecutor;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;
import java.util.concurrent.Executor;

import de.greenrobot.event.EventBus;

/**
 * Created by SDT14324 on 2017/9/26.
 */

public class DownloadProcess extends StateProcess {
    private String downloadUil = null;
    private String downloadPath = null;
    private JSONObject json = null;
    private DownloadCallback callback = null;
    private long downloadStartSize = 0;
    private long downloadTotalSize = 0;
    private static int downlowdTimes = 0;
    private static final int MAX_DOWNLOAD_THREAD = 1;
    private final int connectTimeout = 20 * 1000;
    private final int retryCount = 5;
    private Callback.Cancelable cancelable = null;
    private final Executor executor = new PriorityExecutor(MAX_DOWNLOAD_THREAD, true);

    public DownloadProcess(UpgradeTask task) {
        super(task);
        LogUtil.i("DownloadProcess");
    }

    @Override
    public void stopProcess() {
        stopDownload();
        this.setStatus(PROCESS_COMPLETE);
    }

    public void stopDownload() {
        if (cancelable != null) {
            cancelable.cancel();
        }
    }

    @Override
    public void run() {
        startDownload();
    }

    private void startDownload(){
        LogUtil.i("DownloadProcess.startDownload");
        downloadUil = getTask().getPkgurl();
        if (downloadUil == null) {
            return;
        }

        downloadPath = CommonUtil.getSaveFilePath();
        CommonUtil.cleanOld();
        freeSavedPath();
        json = new JSONObject();

        download(downloadUil, downloadPath);
    }

    public synchronized void download(String url, String savePath) {

        callback = new DownloadCallback();

        LogUtil.i("url : " + url);
        LogUtil.i("savePath : " + savePath);
        RequestParams params = new RequestParams(url);
        params.setAutoResume(true);
        params.setAutoRename(true);
        params.setSaveFilePath(savePath);
        params.setHeader("Cache-Control", "no-cache");
        params.setHeader("Pragma", "no-cache");
        params.setExecutor(executor);// 自定义线程池
        params.setConnectTimeout(connectTimeout);// 连接超时时间，默认15 * 1000
        // params.setPriority(Priority.DEFAULT);// 请求优先级
        params.setMaxRetryCount(retryCount);// 最大请求错误重试次数，默认2次
        params.setCancelFast(true);
        cancelable = x.http().get(params, callback);

    }

    public class DownloadCallback implements Callback.CommonCallback<File>, Callback.ProgressCallback<File>{

        public DownloadCallback() {}
        /************************************************************
         * ProgressCallback
         * */
        @Override
        public void onWaiting() {
            LogUtil.i("DownloadProcess.download is waiting");
        }

        @Override
        public void onStarted() {
            LogUtil.i("DownloadProcess download has started");
            downloadStartSize = getTmpFileSize();
            LogUtil.i("DownloadProcess downloadStartSize : " + downloadStartSize);
            LogUtil.i("DownloadProcess getTask().getUploaddownloadsize() : " + getTask().getUploaddownloadsize());
            if (downloadStartSize > getTask().getUploaddownloadsize()) {
                setDownloadSizeForJson(downloadStartSize - getTask().getUploaddownloadsize());
                setDownloadResultForJson(R.string.download_break);//下载过程断电
                setJsonObject();
                LogUtil.i("DownloadProcess onStarted setJsonObject : " + json.toString());
                saveJsonToCollector();
            }
        }

        @Override
        public void onLoading(long total, long current, boolean isDownloading) {
            LogUtil.i("total : " + total + ", current : " + current + ", isDownloading : " + isDownloading);

            if (current == 0) {
                downloadStartSize = current;
                getTask().setUploaddownloadsize(0);
                UpgradeApp.getInstance().getTaskManager().updateObj(getTask());
                LogUtil.i("DownloadProcess downloadStartSize set to 0");
            }

            getTask().setDownloadSize(current);
            if (isDownloading) {
                int progress;
                float per = (float) current / total;
                if (per > 1.0) {
                    per = 1;
                }
                progress = Math.round(per * 100);
                LogUtil.i("download progress is : " + progress);
                postDownloadProgress(progress);

            }

            UpgradeApp.getInstance().getTaskManager().updateObj(getTask());
        }
        /************************************************************
         * CommonCallback
         * */

        @Override
        public void onSuccess(File file) {
            synchronized (DownloadCallback.class) {
                LogUtil.i("DownloadProcess download success");
                // sync
                CommonUtil.syncFile(downloadPath);
                setDownloadSizeForJson(getTask().getPkgsize() - downloadStartSize);
                if (isMD5right()) {
                    getTask().setMD5right(true);
                    setDownloadResultForJson(R.string.download_success);
                } else {
                    postDownloadErrCode(IConstants.ERR_CODE_MD5_ERR);
                    UploadDownloadComplete upload = new UploadDownloadComplete(getTask());
                    upload.uploadDownloadResult();
                    setDownloadResultForJson(R.string.download_md5_err);
                }

                getTask().setDownloadComplete(true);
                UpgradeApp.getInstance().getTaskManager().updateObj(getTask());
            }
        }

        @Override
        public void onError(Throwable ex, boolean isOnCallback) {
            synchronized (DownloadCallback.class) {
                LogUtil.i("DownloadProcess download error : " + ex.getMessage());
                setDownloadResultForJson(R.string.download_err);
                setDownloadSizeForJson(getTmpFileSize() - downloadStartSize);
            }
        }

        @Override
        public void onCancelled(CancelledException cex) {
            synchronized (DownloadCallback.class) {
                LogUtil.i("DownloadProcess download cancelled : " + cex.getMessage());
            }
        }

        @Override
        public void onFinished() {
            setJsonObject();
            LogUtil.i("DownloadProcess setJsonObject : " + json.toString());
            saveJsonToCollector();

            if (getTask().isMD5right()) {
                getTask().setState(StateProcess.STATE_RECOVERY);
                getTask().setHintReboot(true);
                UpgradeApp.getInstance().getTaskManager().updateObj(getTask());
                /* 下载完成之后上报一次下载完成 */
                UploadDownloadComplete upload = new UploadDownloadComplete(getTask());
                upload.uploadDownloadResult();

                DownloadProcess.this.setStatus(StateProcess.PROCESS_COMPLETE);
                EventBus.getDefault().post(
                        MessageEvent.createMsg(MessageEvent.EVENT_UPGRADE_NEXT, MessageEvent.FROM_NORMAL));
            } else {
                downlowdTimes++;
                new Handler(UpgradeApp.getInstance().getMainLooper()).postDelayed(new Runnable() {
                      public void run() {
                          if (downlowdTimes < IConstants.DOWLOAD_TIMES) {
                              DownloadProcess.this.setStatus(StateProcess.PROCESS_COMPLETE);
                              EventBus.getDefault().post(
                                      MessageEvent.createMsg(MessageEvent.EVENT_UPGRADE_NEXT, MessageEvent.FROM_NORMAL));
                          } else {
                              DownloadProcess.this.setStatus(StateProcess.PROCESS_COMPLETE);
                              downlowdTimes = 0;
                              if (!getTask().isDownloadComplete()) {
                                  postDownloadErrCode(IConstants.ERR_CODE_NETWORK);
                              }
                          }
                      }

                  }, 5000);
            }
            LogUtil.i("DownloadProcess download finished");
        }


    }

    private void postDownloadProgress(int progress) {
        MessageEvent event = MessageEvent.createMsg(MessageEvent.MSG_DOWNLOAD_PROGRESS, 0);
        event.setParam(progress);
        EventBus.getDefault().post(event);
        getTask().setDownloadProcess(progress);
    }

    private void postDownloadErrCode(int errCode) {

        MessageEvent event = MessageEvent.createMsg(MessageEvent.MSG_DOWNLOAD_FAILED, 0);
        event.setParam(errCode);
        EventBus.getDefault().post(event);
        // TODO: 2017/9/26 send br to info other app
        /*Intent in = new Intent(IUpgradeBracast.ACTION_DOWNLOAD_FAILED);
        in.putExtra(CommonUtil.ERRCODE, errCode);

        try {
            UpgradeApp.getInstance().sendBroadcast(in);
        }
        catch (Exception e) {
            e.printStackTrace();
        }*/
    }

    public boolean isMD5right() {

        String md5Server = getTask().getPkgmd5();
        String filePath = CommonUtil.getSaveFilePath();
        String calculatedMd5 = CommonUtil.getMD5(filePath);
        LogUtil.i("DownloadProcess md5Server : " + md5Server + "\tcalculatedMd5 : " + calculatedMd5);
        if (md5Server == calculatedMd5 || (md5Server != null && md5Server.equalsIgnoreCase(calculatedMd5))) {
            return true;
        }
        else {
            LogUtil.i("DownloadProcess check MD5 false.");
            return false;
        }
    }



    private void freeSavedPath() {
        long needSize = getTask().getPkgsize() - getTask().getDownloadSize();
        long freeSize = CommonUtil.getFreeSize();
        LogUtil.i("DownloadProcess needSize : " + needSize + " freeSize : " + freeSize);
        if (freeSize < needSize) {
            LogUtil.i("No enough space");
            CommonUtil.clipCacheDir("cache");
        }
    }

    private long getTmpFileSize() {

        if (TextUtils.isEmpty(downloadPath)) {
            return 0;
        }
        String tmpFilePath = downloadPath + ".tmp";

        File tmpFile = new File(tmpFilePath);

        if (tmpFile.exists()) {
            return tmpFile.length();
        }

        return 0;
    }

    private void setDownloadSizeForJson(long size) {

        downloadTotalSize = size;

        if (null == json) {
            return;
        }

        try {
            json.put("downloadsize", size);
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
    }
    private void setDownloadResultForJson(int result) {
        if (null == json) {
            return;
        }

        try {
            json.put("downloadresult", UpgradeApp.getInstance().getResources().getString(result));
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void setJsonObject() {

        if (null == json) {
            return;
        }

        try {
            json.put("localversion", CommonUtil.getSoftVersion());
            json.put("requestversion", getTask().getPkgversion());
            json.put("url", downloadUil);
            json.put("time", System.currentTimeMillis());
            json.put("upgradePackageId", getTask().getUpgradeInfoId());
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void saveJsonToCollector() {

        if (downloadTotalSize > 0) {

            updateUploadDownloadSize();

            boolean ret = false;
            // TODO: 2017/9/26 uploadAgent 上传阉割
//            ret = SkyUploadFlowManager.getInstance().saveString(dataCollectionType, json.toString());
            if (!ret) {
                LogUtil.i("set data collection Json Object error!!!");
            }
        }
    }

    private void updateUploadDownloadSize() {
        long hasDownloadSize = getTask().getUploaddownloadsize();
        hasDownloadSize = hasDownloadSize + downloadTotalSize;
        LogUtil.i("DownloadProcess hasDownloadSize : " + hasDownloadSize);
        getTask().setUploaddownloadsize(hasDownloadSize);
        UpgradeApp.getInstance().getTaskManager().updateObj(getTask());
    }
}
