package com.xutil.xutildemo.download;

import com.xutil.xutildemo.download.db.DownloadInfo;
import com.xutil.xutildemo.download.db.DownloadState;
import com.xutil.xutildemo.download.view.DownloadViewHolder;

import org.xutils.DbManager;
import org.xutils.common.util.LogUtil;
import org.xutils.ex.DbException;
import org.xutils.x;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by SDT14324 on 2017/10/12.
 */

public class DownloadManager {

    private static volatile DownloadManager instance;
    private final DbManager db;
    private final List<DownloadInfo> downloadInfoList = new ArrayList<DownloadInfo>();
    private final ConcurrentHashMap<DownloadInfo,DownloadCallback> callbackMap =
            new ConcurrentHashMap<DownloadInfo, DownloadCallback>(5);

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
                    if (info.getState().value() < DownloadState.FINISHED.value()) {
                        info.setState(DownloadState.STOPPED);
                    }
                    downloadInfoList.add(info);
                }
            }
        } catch (DbException ex) {
            LogUtil.e(ex.getMessage(), ex);
        }
    }

    public synchronized void startDownload(String url, String label,String savepath,
                                           boolean autoResume, boolean autoRename,
                                           DownloadViewHolder viewHolder){
        String fileSavePath = new File(savepath).getAbsolutePath();
        DownloadInfo downloadInfo = db.selector(DownloadInfo.class,)
                .where("lable","=",label)
                .and("fileSavePath","=",fileSavePath)
                .findFirst();

        if(downloadInfo != null){
            D
        }

    }



}
