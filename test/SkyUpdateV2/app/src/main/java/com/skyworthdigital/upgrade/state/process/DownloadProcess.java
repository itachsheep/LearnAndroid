package com.skyworthdigital.upgrade.state.process;

import android.content.Intent;
import android.os.Handler;
import android.text.TextUtils;

import com.skyworthdigital.upgrade.R;
import com.skyworthdigital.upgrade.UpgradeApp;
import com.skyworthdigital.upgrade.model.UpgradeTask;
import com.skyworthdigital.upgrade.port.IUpgradeBracast;
import com.skyworthdigital.upgrade.state.StateProcess;
import com.skyworthdigital.upgrade.state.msg.MessageEvent;
import com.skyworthdigital.upgrade.util.CommonUtil;
import com.skyworthdigital.upgrade.util.LogUtil;
import com.skyworthdigital.upgrade.util.SkyUploadFlowManager;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.common.task.PriorityExecutor;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;
import java.util.concurrent.Executor;

import de.greenrobot.event.EventBus;

public class DownloadProcess extends StateProcess {

    private String downloadUil = null;
    private String downloadPath = null;
    private Callback.Cancelable cancelable = null;
    private final int connectTimeout = 20 * 1000;
    private final int retryCount = 5;
    private static int downlowdTimes = 0;
    private final String dataCollectionType = "otaThroughput";

    private static final int MAX_DOWNLOAD_THREAD = 1;

    private final Executor executor = new PriorityExecutor(MAX_DOWNLOAD_THREAD, true);
    private DownloadCallback callback = null;
    private JSONObject json = null;
    private long downloadStartSize = 0;
    private long downloadTotalSize = 0;

    public DownloadProcess(UpgradeTask task) {
        super(task);
        LogUtil.log("DownloadProcess");
    }

    @Override
    public void run() {
        // TODO Auto-generated method stub
        startDownload();
    }

    @Override
    public void stopProcess() {
        // TODO Auto-generated method stub
        stopDownload();
        this.setStatus(PROCESS_COMPLETE);

    }

    private void startDownload() {
        LogUtil.log("startDownload");
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

        LogUtil.log("url : " + url);
        LogUtil.log("savePath : " + savePath);
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

    public void stopDownload() {
        if (cancelable != null) {
            cancelable.cancel();
        }
    }

    public class DownloadCallback implements Callback.CommonCallback<File>, Callback.ProgressCallback<File> {

        public DownloadCallback() {}

        @Override
        public void onWaiting() {
            LogUtil.log("download is waiting");
        }

        @Override
        public void onStarted() {
            LogUtil.log("download has started");
            downloadStartSize = getTmpFileSize();
            LogUtil.log("downloadStartSize : " + downloadStartSize);
            LogUtil.log("getTask().getUploaddownloadsize() : " + getTask().getUploaddownloadsize());
            if (downloadStartSize > getTask().getUploaddownloadsize()) {
                setDownloadSizeForJson(downloadStartSize - getTask().getUploaddownloadsize());
                setDownloadResultForJson(R.string.download_break);
                setJsonObject();
                LogUtil.log("onStarted setJsonObject : " + json.toString());
                saveJsonToCollector();
            }
        }

        @Override
        public void onLoading(long total, long current, boolean isDownloading) {
            LogUtil.log("total : " + total + "current : " + current + "isDownloading : " + isDownloading);

            if (current == 0) {
                downloadStartSize = current;
                getTask().setUploaddownloadsize(0);
                UpgradeApp.getInstance().getTaskManager().updateObj(getTask());
                LogUtil.log("downloadStartSize set to 0");
            }

            getTask().setDownloadSize(current);
            if (isDownloading) {
                int progress;
                float per = (float) current / total;
                if (per > 1.0) {
                    per = 1;
                }
                progress = Math.round(per * 100);
                LogUtil.log("download progress is : " + progress);
                postDownloadProgress(progress);

            }

            UpgradeApp.getInstance().getTaskManager().updateObj(getTask());
        }

        @Override
        public void onSuccess(File result) {
            synchronized (DownloadCallback.class) {
                LogUtil.log("download success");
                // sync
                CommonUtil.syncFile(downloadPath);
                setDownloadSizeForJson(getTask().getPkgsize() - downloadStartSize);
                if (isMD5right()) {
                    getTask().setMD5right(true);
                    setDownloadResultForJson(R.string.download_success);
                }
                else {
                    postDownloadErrCode(CommonUtil.ERR_CODE_MD5_ERR);
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
                LogUtil.log("download error : " + ex.getMessage());
                setDownloadResultForJson(R.string.download_err);
                setDownloadSizeForJson(getTmpFileSize() - downloadStartSize);
            }
        }

        @Override
        public void onCancelled(CancelledException cex) {
            synchronized (DownloadCallback.class) {
                LogUtil.log("download cancelled : " + cex.getMessage());
            }
        }

        @Override
        public void onFinished() {

            setJsonObject();
            LogUtil.log("setJsonObject : " + json.toString());
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
            }
            else {

                downlowdTimes++;

                new Handler(UpgradeApp.getInstance().getMainLooper()).postDelayed(new Runnable() {

                    public void run() {

                        if (downlowdTimes < CommonUtil.DOWLOAD_TIMES) {
                            DownloadProcess.this.setStatus(StateProcess.PROCESS_COMPLETE);
                            EventBus.getDefault().post(
                                MessageEvent.createMsg(MessageEvent.EVENT_UPGRADE_NEXT, MessageEvent.FROM_NORMAL));
                        }
                        else {
                            DownloadProcess.this.setStatus(StateProcess.PROCESS_COMPLETE);
                            downlowdTimes = 0;
                            if (!getTask().isDownloadComplete()) {
                                postDownloadErrCode(CommonUtil.ERR_CODE_NETWORK);
                            }
                        }
                    }

                },
                    5000);

            }
            LogUtil.log("download finished");
        }

        public boolean isMD5right() {

            String md5Server = getTask().getPkgmd5();
            String filePath = CommonUtil.getSaveFilePath();
            String calculatedMd5 = CommonUtil.getMD5(filePath);
            LogUtil.log("md5Server : " + md5Server + "\tcalculatedMd5 : " + calculatedMd5);
            if (md5Server == calculatedMd5 || (md5Server != null && md5Server.equalsIgnoreCase(calculatedMd5))) {
                return true;
            }
            else {
                LogUtil.log("check MD5 false.");
                return false;
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

            Intent in = new Intent(IUpgradeBracast.ACTION_DOWNLOAD_FAILED);
            in.putExtra(CommonUtil.ERRCODE, errCode);

            try {
                UpgradeApp.getInstance().sendBroadcast(in);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void freeSavedPath() {
        long needSize = getTask().getPkgsize() - getTask().getDownloadSize();
        long freeSize = CommonUtil.getFreeSize();
        LogUtil.log("needSize : " + needSize + " freeSize : " + freeSize);
        if (freeSize < needSize) {
            LogUtil.log("No enough space");
            CommonUtil.clipCacheDir("cache");
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
            ret = SkyUploadFlowManager.getInstance().saveString(dataCollectionType, json.toString());
            if (!ret) {
                LogUtil.log("set data collection Json Object error!!!");
            }
        }
    }

    private void updateUploadDownloadSize() {
        long hasDownloadSize = getTask().getUploaddownloadsize();
        hasDownloadSize = hasDownloadSize + downloadTotalSize;
        LogUtil.log("hasDownloadSize : " + hasDownloadSize);
        getTask().setUploaddownloadsize(hasDownloadSize);
        UpgradeApp.getInstance().getTaskManager().updateObj(getTask());
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
            // TODO Auto-generated catch block
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
            // TODO Auto-generated catch block
            e.printStackTrace();
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

}
