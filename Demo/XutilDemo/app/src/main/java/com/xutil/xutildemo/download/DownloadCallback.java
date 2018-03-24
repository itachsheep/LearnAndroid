package com.xutil.xutildemo.download;

import com.xutil.xutildemo.download.db.DownloadInfo;
import com.xutil.xutildemo.download.db.DownloadState;
import com.xutil.xutildemo.download.view.DownloadViewHolder;
import com.xutil.xutildemo.utils.LogUtil;

import org.xutils.common.Callback;
import org.xutils.ex.DbException;

import java.io.File;
import java.lang.ref.WeakReference;

/**
 * Created by SDT14324 on 2017/10/12.
 */

public class DownloadCallback implements Callback.CommonCallback<File>
                , Callback.ProgressCallback<File>
                , Callback.Cancelable{
    private WeakReference<DownloadViewHolder> viewHolderRef;
    private DownloadInfo downloadInfo;
    private DownloadManager downloadManager;
    private Cancelable cancelable;

    public DownloadCallback(DownloadViewHolder viewHolder){
        this.viewHolderRef = new WeakReference<DownloadViewHolder>(viewHolder);
        this.downloadInfo = viewHolder.getDownloadInfo();
    }

    public void setDownloadManager(DownloadManager downloadManager) {
        this.downloadManager = downloadManager;
    }

    public void setCancelable(Cancelable cancelable) {
        this.cancelable = cancelable;
    }

    public DownloadViewHolder getViewholder(){
        if(viewHolderRef == null) return null;
        return viewHolderRef.get();
    }


    //****************** Callback.CommonCallback **************** //
    @Override
    public void onSuccess(File result) {
        LogUtil.i("onSuccess ..");
        synchronized (DownloadCallback.class) {
            try {
                downloadInfo.setState(DownloadState.FINISHED);
                downloadManager.updateDownloadInfo(downloadInfo);
            } catch (DbException ex) {
                LogUtil.i("onSuccess error: "+ex);
            }
            DownloadViewHolder viewHolder = this.getViewholder();
            if (viewHolder != null) {
                viewHolder.onSuccess(result);
            }
        }
    }

    @Override
    public void onError(Throwable ex, boolean isOnCallback) {
        LogUtil.i("onError isOnCallback: "+isOnCallback);
        synchronized (DownloadCallback.class) {
            try {
                downloadInfo.setState(DownloadState.ERROR);
                downloadManager.updateDownloadInfo(downloadInfo);
            } catch (DbException e) {
                LogUtil.i("onError e: "+e.getMessage());
            }
            DownloadViewHolder viewHolder = this.getViewholder();
            if (viewHolder != null) {
                viewHolder.onError(ex, isOnCallback);
            }
        }
    }

    @Override
    public void onCancelled(CancelledException cex) {
        LogUtil.i("onCancelled ..");
        synchronized (DownloadCallback.class) {
            try {
                downloadInfo.setState(DownloadState.STOPPED);
                downloadManager.updateDownloadInfo(downloadInfo);
            } catch (DbException ex) {
                LogUtil.i("onCancelled error: "+ex.getMessage());
            }
            DownloadViewHolder viewHolder = this.getViewholder();
            if (viewHolder != null) {
                viewHolder.onCancelled(cex);
            }
        }
    }

    @Override
    public void onFinished() {
        isCancelled = false;
    }


    //****************** Callback.ProgressCallback **************** //
    @Override
    public void onWaiting() {
        LogUtil.i("onWaiting ..");
        try {
            downloadInfo.setState(DownloadState.WAITING);
            downloadManager.updateDownloadInfo(downloadInfo);
        } catch (DbException ex) {
            LogUtil.i("onwaiting error: "+ ex.getMessage());
        }
        DownloadViewHolder viewHolder = this.getViewholder();
        if (viewHolder != null) {
            viewHolder.onWaiting();
        }
    }

    @Override
    public void onStarted() {
        LogUtil.i("onStarted..");
        try {
            downloadInfo.setState(DownloadState.STARTED);
            downloadManager.updateDownloadInfo(downloadInfo);
        } catch (DbException ex) {
            LogUtil.i("onStarted error: "+ ex.getMessage());
        }
        DownloadViewHolder viewHolder = this.getViewholder();
        if (viewHolder != null) {
            viewHolder.onStarted();
        }
    }

    @Override
    public void onLoading(long total, long current, boolean isDownloading) {
        LogUtil.i("onLoading total: "+total+", current: "+current+", isDownloading: "+isDownloading);
        if (isDownloading) {
            try {
                downloadInfo.setState(DownloadState.STARTED);
                downloadInfo.setFileLength(total);
                downloadInfo.setProgress((int) (current * 100 / total));
                downloadManager.updateDownloadInfo(downloadInfo);
            } catch (DbException ex) {
                LogUtil.i("onLoading error: "+ex.getMessage());
            }
            DownloadViewHolder viewHolder = this.getViewholder();
            if (viewHolder != null) {
                viewHolder.onLoading(total, current);
            }
        }
    }

    private boolean isCancelled = false;

    //****************** Callback.Cancelable **************** //
    @Override
    public void cancel() {
        LogUtil.i(" cancel() ..");
        isCancelled = true;
        if(cancelable != null){
            LogUtil.i(" cancel() execute ..");
            cancelable.cancel();
        }
    }

    @Override
    public boolean isCancelled() {
        return false;
    }
}
