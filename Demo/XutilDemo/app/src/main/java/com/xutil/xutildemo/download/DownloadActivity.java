package com.xutil.xutildemo.download;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.xutil.xutildemo.R;
import com.xutil.xutildemo.download.db.DownloadInfo;
import com.xutil.xutildemo.download.db.DownloadState;
import com.xutil.xutildemo.download.view.DownloadViewHolder;
import com.xutil.xutildemo.utils.LogUtil;

import org.xutils.common.Callback;
import org.xutils.ex.DbException;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.io.File;


/**
 * Created by SDT14324 on 2017/9/29.
 */

@ContentView(R.layout.ac_download)
public class DownloadActivity extends Activity  {

    @ViewInject(R.id.ac_dl_start)
    private Button btStart;

    @ViewInject(R.id.ac_dl_lv)
    private ListView listView;

    private DownloadManager downloadManager;
    private DownloadListAdapter adapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        downloadManager = DownloadManager.getInstance();
        adapter = new DownloadListAdapter(this,downloadManager);
        listView.setAdapter(adapter);
    }

    @Event(R.id.ac_dl_start)
    private void startDownload(View view) throws DbException {
        for (int i = 0; i < 5; i++) {
//            String url = "http://dl.bintray.com/wyouflf/maven/org/xutils/xutils/3.5.0/xutils-3.5.0.aar";
//            String url = "http://192.168.0.76:9191/nginxSource/30001/7085/36ed7845-067f-4d23-9edc-d71abe7074f1.zip";
            String url = "http://192.168.56.1:8080/test_upgrade.zip";
            String label = i + "_download_" + System.nanoTime();
            String filepath = getFilesDir().getPath();
            String savePath = filepath + label + ".zip";
            View itemView = LayoutInflater.from(this).inflate(R.layout.download_item,null);
            DownloadItemViewholder viewholder = new DownloadItemViewholder(itemView,adapter,downloadManager,null);

            DownloadManager.getInstance().startDownload(url, label,savePath, true, false, viewholder);
        }
        adapter.notifyDataSetChanged();
    }


     class DownloadListAdapter extends BaseAdapter {

        private Context mContext;
        private DownloadManager downloadManager;

        public DownloadListAdapter(Context context, DownloadManager downloadManager){
            this.mContext = context;
            this.downloadManager = downloadManager;
        }

        @Override
        public int getCount() {
            return downloadManager.getDownloadlistCount();
        }

        @Override
        public Object getItem(int i) {
            return downloadManager.getDownloadInfo(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            DownloadItemViewholder viewholder = null;
            DownloadInfo downloadInfo = downloadManager.getDownloadInfo(i);
            if(view == null){
                synchronized (DownloadListAdapter.class){
                    viewholder = (DownloadItemViewholder) downloadManager.getViewHolder(downloadInfo);
                    if(viewholder == null){
                        view = LayoutInflater.from(mContext).inflate(R.layout.download_item,null);
                        viewholder = new DownloadItemViewholder(view,this,downloadManager,downloadInfo);
                    }else {
                        view = viewholder.getView();
                    }

                    view.setTag(viewholder);
                    viewholder.refresh();
                }

            }else {
                viewholder = (DownloadItemViewholder) view.getTag();
                viewholder.update(downloadInfo);
            }

            return view;
        }
    }



     class DownloadItemViewholder extends DownloadViewHolder {
         @ViewInject(R.id.download_label)
        TextView label;
        @ViewInject(R.id.download_state)
        TextView state;
         @ViewInject(R.id.download_pb)
        ProgressBar progressBar;
        @ViewInject(R.id.download_stop_btn)
        Button stopBtn;

        private DownloadManager downloadManager;
        private BaseAdapter adapter;
        private View mView;

        public void setAdapter(BaseAdapter adapter){
            this.adapter = adapter;
        }

        public void setDownloadManager(DownloadManager downloadManager){
            this.downloadManager = downloadManager;
        }

        public View getView(){
            return mView;
        }

        public DownloadItemViewholder(View view,BaseAdapter adapter,DownloadManager downloadManager,
                                      DownloadInfo downloadInfo) {
            super(view,downloadInfo);
            this.mView = view;
            this.adapter = adapter;
            this.downloadManager = downloadManager;

        }

        @Event(R.id.download_stop_btn)
        private void toggleEvent(View view) {
            DownloadState state = downloadInfo.getState();
            LogUtil.i("toggleEvent state: "+state);
            switch (state){
                case WAITING:
                case STARTED:
                    LogUtil.i("toggleEvent stop ");
                    downloadManager.stopDownload(downloadInfo);
                    break;
                case ERROR:
                case STOPPED:
                    try {
                        LogUtil.i("toggleEvent start filePath:"+downloadInfo.getFileSavePath()
                        +", label: "+downloadInfo.getLabel());
                        downloadManager.startDownload(downloadInfo.getUrl(),
                                downloadInfo.getLabel(),
                                downloadInfo.getFileSavePath(),
                                downloadInfo.isAutoResume(),
                                downloadInfo.isAutoRename(),
                                this);
                    } catch (DbException e) {
                        LogUtil.i("toggleEvent error: "+e.getMessage());
                        e.printStackTrace();
                    }
                    break;
                case FINISHED:

                    break;
            }
        }

        @Override
        public void update(DownloadInfo downloadInfo) {
            super.update(downloadInfo);
            refresh();
        }

        @Event(R.id.download_remove_btn)
        private void removeEvent(View view){
            downloadManager.removeDownload(downloadInfo);
            adapter.notifyDataSetChanged();
        }

        public void refresh(){
            //LogUtil.i("refresh label: "+label+", downloadinfo: "+downloadInfo);
            if(downloadInfo == null) return;
            label.setText(downloadInfo.getLabel());
            state.setText(downloadInfo.getState().toString());
            progressBar.setProgress(downloadInfo.getProgress());

            DownloadState state = downloadInfo.getState();
            stopBtn.setText("停止");
            switch (state) {
                case WAITING:
                case STARTED:
                    stopBtn.setText("停止");
                    break;
                case ERROR:
                case STOPPED:
                    stopBtn.setText("开始下载");
                    break;
                case FINISHED:
//                    stopBtn.setVisibility(View.INVISIBLE);
                    stopBtn.setText("下载完成");
                    break;
                default:
                    stopBtn.setText("开始下载");
                    break;
            }
        }

        @Override
        public void onWaiting() {
            refresh();
        }

        @Override
        public void onStarted() {
            refresh();
        }

        @Override
        public void onLoading(long total, long current) {
            refresh();
        }

        @Override
        public void onSuccess(File result) {
            refresh();
        }

        @Override
        public void onError(Throwable ex, boolean isOnCallback) {
            refresh();
        }

        @Override
        public void onCancelled(Callback.CancelledException cex) {
            refresh();
        }
    }


}
