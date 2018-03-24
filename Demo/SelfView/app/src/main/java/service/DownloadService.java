package service;

import android.app.IntentService;
import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.RemoteCallbackList;
import android.os.RemoteException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import andorid.taow.selfview.IDownload;
import andorid.taow.selfview.IDownloadCallback;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import util.LogUtil;

/**
 * Created by taow on 2017/6/5.
 */

public class DownloadService extends Service {
    private IDownloadCallback downloadCallback;
    private DownloadBinder downloadBinder = new DownloadBinder();
//    private Handler mHandler = new Handler();
    private long startLocation = 0;
    private InputStream in;
    private RandomAccessFile raf;
    @Override
    public void onCreate() {
        LogUtil.i("DownloadService.onCreate");
        super.onCreate();
        RemoteCallbackList
        sendDownloadProgress();
        try {
            String path = getDownloadPath();
            raf = new RandomAccessFile(path,"rwd");
            raf.setLength(171318727);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getDownloadPath() {
        String path = getCacheDir().getAbsolutePath()+"/WebStorm11zh.rar";
        LogUtil.i("DownloadService.getDownloadPath path: "+path);
        return path;
    }
    private Handler threadHandler = new Handler();

    class LooperThread extends Thread {

        private Runnable progressRunnable = new Runnable() {
            @Override
            public void run() {
                startLocation++;
                if(null != downloadBinder){
                    LogUtil.i("DownloadService.progressRunnable progress: "+startLocation);
                    downloadBinder.sendDownloadState(0, (int) startLocation);
                }
                threadHandler.postDelayed(progressRunnable,1500);
            }
        };

        @Override
        public void run() {
//            Looper.prepare();
            threadHandler.postDelayed(progressRunnable,1500);
            //开始下载
            /*String url = "http://10.180.90.105:8080/WebStorm11zh.rar";
            OkHttpClient httpClient = new OkHttpClient.Builder()
                    .followRedirects(true)
                    .followSslRedirects(true)
                    .retryOnConnectionFailure(true)
                    .connectTimeout(5, TimeUnit.SECONDS).build();
            Request request = new Request.Builder()
                    .url(url)
                    .method("GET", null)
                    .header("Range","bytes=" + startLocation + "-")
                    .build();
            Call newCall = httpClient.newCall(request);
            LogUtil.i("DownloadService.sendDownloadProgress download thread... startlocation: "+startLocation);
            newCall.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    LogUtil.i("DownloadService.onFailure");
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    LogUtil.i("DownloadService.onResponse..");
                    in = response.body().byteStream();
                    byte[] buffer = new byte[64*1024];
                    int length = 0;
                    int time = 0;
                    raf.seek(startLocation);
                    while ((length = in.read(buffer)) != -1){
//                        LogUtil.i("DownloadService.onResponse ****************************** length: "+length
//                                +", time: "+time
//                                +", statlocation: "+startLocation);
                        raf.write(buffer,0,length);
                        time++;
                        startLocation += length;
                    }
                }
            });*/
//            Looper.loop();
        }

        private void stopCurrent(){
            threadHandler.removeCallbacks(progressRunnable);
        }

        private void continueCurrent(){
            threadHandler.post(progressRunnable);
        }

    };
    private LooperThread downloadThread;
    private void sendDownloadProgress() {
        LogUtil.i("DownloadService.sendDownloadProgress");
        downloadThread = new LooperThread();
        downloadThread.start();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        LogUtil.i("DownloadService.onStartCommand");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        LogUtil.i("DownloadService.onBind..");
        return downloadBinder;
    }

    @Override
    public void onDestroy() {
        LogUtil.i("DownloadService.onDestroy");
        super.onDestroy();
        if(null != downloadThread){
            downloadThread.stopCurrent();
        }
    }

    @Override
    public boolean onUnbind(Intent intent) {
        LogUtil.i("DownloadService.onUnbind");
        if(null != downloadThread){
            downloadThread.stopCurrent();
        }
        return super.onUnbind(intent);
    }

    public class DownloadBinder extends IDownload.Stub{

        @Override
        public int getIpState() throws RemoteException {
            return 0;
        }

        @Override
        public void setIpUpgradeCallback(IDownloadCallback listener) throws RemoteException {
            LogUtil.i("DownloadServiceset.DownloadBinder.setIpUpgradeCallback ");
            downloadCallback = listener;
        }

        @Override
        public void operateIpService(int opCode) throws RemoteException {
            LogUtil.i("DownloadServiceset.DownloadBinder.operateIpService opCode: "+opCode);
            if(opCode == 0){
                waitThread();
            }else if (opCode == 1){
                notifyThread();
            }else if(opCode == 2){
                sleepSomeTime();
            }
        }

        private void sendDownloadState(int state, int progress){
            LogUtil.i("DownloadServiceset.DownloadBinder.sendDownloadState state: "+state+", progress: "+progress);
            if(null != downloadCallback){
                try {
                    downloadCallback.onResult(state,progress);
                } catch (RemoteException e) {
                    LogUtil.i("DownloadServiceset.DownloadBinder.sendDownloadState exception...... ");
                    e.printStackTrace();
                }
            }else {
                LogUtil.i("DownloadServiceset.DownloadBinder.sendDownloadState callback null... ");
            }
        }
    }
    private Object lockObj = new Object();
    private void waitThread(){
        LogUtil.i("DownloadServiceset.waitThread ");
        synchronized (lockObj){
            try {
                LogUtil.i("DownloadServiceset.waitThread 111");
                downloadThread.stopCurrent();
                lockObj.wait();
                LogUtil.i("DownloadServiceset.waitThread 222");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void notifyThread(){
        LogUtil.i("DownloadServiceset.notifyThread ");
        synchronized (lockObj){
            LogUtil.i("DownloadServiceset.notifyThread 111");
            lockObj.notifyAll();

        }
    }

    private void sleepSomeTime(){
        LogUtil.i("DownloadServiceset.sleepSomeTime ");
        try {
            //睡眠1小时
            LogUtil.i("DownloadServiceset.sleepSomeTime 111");
//            downloadThread.sleep( 60*60 * 1000);
            downloadThread.stopCurrent();
//            Thread.sleep(60*60*1000);
            LogUtil.i("DownloadServiceset.sleepSomeTime 222");

            LogUtil.i("DownloadServiceset.sleepSomeTime 333");

        } catch (Exception e) {

            e.printStackTrace();
            LogUtil.i("DownloadServiceset.sleepSomeTime 444");
        }
    }
}
