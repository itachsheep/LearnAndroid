package com.xutil.xutildemo.download;

import com.xutil.xutildemo.download.db.DownloadInfo;
import com.xutil.xutildemo.download.view.DownloadViewHolder;
import com.xutil.xutildemo.utils.LogUtil;
import org.xutils.DbManager;
import org.xutils.common.Callback;
import org.xutils.common.task.PriorityExecutor;
import org.xutils.ex.DbException;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executor;

/**
 * Created by SDT14324 on 2017/10/12.
 */

public class DownloadManager {

    private static volatile DownloadManager instance;
    private final DbManager db;
    private final List<DownloadInfo> downloadInfoList = new ArrayList<DownloadInfo>();
    private final ConcurrentHashMap<DownloadInfo,DownloadCallback> callbackMap =
            new ConcurrentHashMap<DownloadInfo, DownloadCallback>(5);

    private final int MAX_DOWNLOAD_THREAD = 1;
    private final Executor executor = new PriorityExecutor(MAX_DOWNLOAD_THREAD,true);


    public static DownloadManager getInstance() {
        if (instance == null) {
            synchronized (DownloadManager.class) {
                if (instance == null) {
                    instance = new DownloadManager();
                }
            }
        }
        return instance;
    }


    public DownloadManager(){
        DbManager.DaoConfig daoConfig = new DbManager.DaoConfig()
                .setDbName("download")
                .setDbVersion(1);
        db = x.getDb(daoConfig);

        try {
            List<DownloadInfo> infoList = db.selector(DownloadInfo.class).findAll();
            if (infoList != null) {
                for (DownloadInfo info : infoList) {
                    /*if (info.getState().value() < DownloadState.FINISHED.value()) {
                        info.setState(DownloadState.STOPPED);
                    }*/
                    downloadInfoList.add(info);
                }
            }
        } catch (DbException ex) {
            org.xutils.common.util.LogUtil.i(ex.getMessage(), ex);
        }
    }

    public synchronized void startDownload(String url, String label,String savepath,
                                           boolean autoResume, boolean autoRename,
                                           DownloadViewHolder viewHolder) throws DbException{
        String fileSavePath = new File(savepath).getAbsolutePath();
        LogUtil.i("startDownload fileSavePath: "+fileSavePath+", label: "+label);
        DownloadInfo downloadInfo = db.selector(DownloadInfo.class)
                .where("label","=",label)
                .and("fileSavePath","=",fileSavePath)
                .findFirst();
        LogUtil.i("startDownload downloadInfo: "+downloadInfo);
        // create download info
        if(downloadInfo == null){
            downloadInfo = new DownloadInfo();
            downloadInfo.setUrl(url);
            downloadInfo.setAutoRename(autoRename);
            downloadInfo.setAutoResume(autoResume);
            downloadInfo.setLabel(label);
            downloadInfo.setFileSavePath(fileSavePath);
            db.saveBindingId(downloadInfo);
        }

        LogUtil.i("startDownload start downloading ..");
        // start downloading
        viewHolder.update(downloadInfo);
        DownloadCallback callback = new DownloadCallback(viewHolder);
        callback.setDownloadManager(this);


        RequestParams params = new RequestParams(url);
        params.setAutoResume(autoResume);
        params.setAutoRename(autoRename);
        params.setSaveFilePath(fileSavePath);
        params.setExecutor(executor);
        params.setCancelFast(true);

        Callback.Cancelable cancelable = x.http().get(params, callback);
        callback.setCancelable(cancelable);


        //add to callback map
        callbackMap.put(downloadInfo,callback);

        //add to downloadinfolist
        if(!downloadInfoList.contains(downloadInfo)){
            downloadInfoList.add(downloadInfo);
        }
    }

    public void stopDownload(DownloadInfo downloadInfo){
        DownloadCallback callback = callbackMap.get(downloadInfo);
        if(callback != null){
            callback.cancel();
        }
    }

    public void removeDownload(DownloadInfo downloadInfo){
        LogUtil.i("removeDownload ");
        try {
            db.delete(downloadInfo);
            stopDownload(downloadInfo);
        } catch (DbException e) {
            LogUtil.i("removeDownload error: "+e.getMessage());
            e.printStackTrace();
        }
    }

    public int getDownloadlistCount(){
        return  downloadInfoList.size();
    }

    public DownloadInfo getDownloadInfo(int index) {
        return downloadInfoList.get(index);
    }

    public void updateDownloadInfo(DownloadInfo info) throws DbException {
        db.update(info);
    }

    public DownloadViewHolder getViewHolder(DownloadInfo downloadInfo){
        DownloadCallback callback = callbackMap.get(downloadInfo);
        if(callback == null) return  null;
        return callback.getViewholder();
    }

}
