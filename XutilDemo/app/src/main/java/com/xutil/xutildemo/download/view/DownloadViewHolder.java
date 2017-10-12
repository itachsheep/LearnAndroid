package com.xutil.xutildemo.download.view;

import android.view.View;

import com.xutil.xutildemo.download.db.DownloadInfo;

import org.xutils.x;

/**
 * Created by SDT14324 on 2017/10/12.
 */

public class DownloadViewHolder {
    protected DownloadInfo downloadInfo;

    public DownloadViewHolder(View view, DownloadInfo downloadInfo){
        this.downloadInfo = downloadInfo;
        x.view().inject(view);
    }



}
