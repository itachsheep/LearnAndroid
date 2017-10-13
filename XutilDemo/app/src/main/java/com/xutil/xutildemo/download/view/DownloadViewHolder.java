package com.xutil.xutildemo.download.view;

import android.view.View;

import com.xutil.xutildemo.download.db.DownloadInfo;

import org.xutils.common.Callback;
import org.xutils.x;

import java.io.File;

/**
 * Created by SDT14324 on 2017/10/12.
 */

public abstract class DownloadViewHolder {
    protected DownloadInfo downloadInfo;

    public DownloadViewHolder(View view, DownloadInfo downloadInfo){
        x.view().inject(this,view);
        this.downloadInfo = downloadInfo;
    }

    public void update(DownloadInfo downloadInfo) {
        this.downloadInfo = downloadInfo;
    }

    public  DownloadInfo getDownloadInfo() {
        return downloadInfo;
    }
    public abstract void onWaiting();

    public abstract void onStarted();

    public abstract void onLoading(long total, long current);

    public abstract void onSuccess(File result);

    public abstract void onError(Throwable ex, boolean isOnCallback);

    public abstract void onCancelled(Callback.CancelledException cex);


}
