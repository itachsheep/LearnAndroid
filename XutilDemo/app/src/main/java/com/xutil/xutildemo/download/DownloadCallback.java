package com.xutil.xutildemo.download;

import org.xutils.common.Callback;

import java.io.File;

/**
 * Created by SDT14324 on 2017/10/12.
 */

public class DownloadCallback implements Callback.CommonCallback<File>
                , Callback.ProgressCallback<File>
                , Callback.Cancelable{

    //****************** Callback.CommonCallback **************** //
    @Override
    public void onSuccess(File result) {

    }

    @Override
    public void onError(Throwable ex, boolean isOnCallback) {

    }

    @Override
    public void onCancelled(CancelledException cex) {

    }

    @Override
    public void onFinished() {

    }


    //****************** Callback.ProgressCallback **************** //
    @Override
    public void onWaiting() {

    }

    @Override
    public void onStarted() {

    }

    @Override
    public void onLoading(long total, long current, boolean isDownloading) {

    }


    //****************** Callback.Cancelable **************** //
    @Override
    public void cancel() {

    }

    @Override
    public boolean isCancelled() {
        return false;
    }
}
