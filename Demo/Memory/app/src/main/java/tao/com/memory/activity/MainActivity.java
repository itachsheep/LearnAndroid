package tao.com.memory.activity;

import android.app.Activity;
import android.content.Context;
import android.content.pm.IPackageDataObserver;
import android.content.pm.IPackageStatsObserver;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageStats;

import android.os.Handler;

import android.os.Message;
import android.os.RemoteException;

import android.os.Bundle;
import android.text.format.Formatter;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import tao.com.memory.R;
import tao.com.memory.utils.LogUtil;

public class MainActivity extends Activity {
    private TextView tvCacheSize;
    private long cacheSize;
    private long appSize;
    private Button btCaculate;
    private Button btClear;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvCacheSize = (TextView) findViewById(R.id.main_tv_cachesize);
        btCaculate = (Button) findViewById(R.id.bt_caculate);
        btClear = (Button) findViewById(R.id.bt_clear);


        //获取手机内部剩余存储空间
        long avaInternalMem = FileSizeUtils.getAvailableInternalMemorySize();
        //获取手机内部总的存储空间
        long totalInternalMem = FileSizeUtils.getTotalInternalMemorySize();
        //获取系统总内存
        long totalMemorySize = FileSizeUtils.getTotalMemorySize(MainActivity.this);
        //获取当前可用内存
        long availableMemory = FileSizeUtils.getAvailableMemory(MainActivity.this);

        LogUtil.i("手机内部剩余存储空间: "+FileSizeUtils.formatFileSize(avaInternalMem,true) +"\n"+
                "手机内部总的存储空间: "+FileSizeUtils.formatFileSize(totalInternalMem,true)+"\n"+
                "系统总内存: "+FileSizeUtils.formatFileSize(totalMemorySize,true)+"\n"+
                "当前可用内存: "+FileSizeUtils.formatFileSize(availableMemory,true)+"\n"
        );



        setListener();
    }


    private void setListener() {
        btCaculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                caculateCache();
                //caculateAppAndCacheSize();
            }
        });

        btClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PackageManager pm = getPackageManager();
                List<PackageInfo> packages = pm.getInstalledPackages(PackageManager.GET_UNINSTALLED_PACKAGES);
                for (PackageInfo pg : packages
                        ) {
                    cleanPkgCache(pg.packageName);
                }
            }
        });
    }

    /**
     * 手动计算app 和 cache 大小
     */
    private void caculateAppAndCacheSize(){
        new Thread(){
            @Override
            public void run() {
                File dataFile = new File("/data/data");
                File[] apps = dataFile.listFiles();
                if(null != apps){
                    appSize = getFolderSize(dataFile);
                }
                if(null != dataFile){
                    cacheSize = caculateCacheSize(dataFile);
                }

                mHandler.sendEmptyMessage(1);
            }
        }.start();
    }

    /**
     *计算
     * @param dataFile
     */
    private long caculateCacheSize(File dataFile) {
        long size = 0;
        File[] apps = dataFile.listFiles();
        if(null == apps){
            return 0;
        }

        for(int i = 0 ; i < apps.length ; i++){
            if(null != apps[i]){
                if(apps[i].isDirectory()){
                    File[] appDataFiles = apps[i].listFiles();
                    if(null != appDataFiles){
                        for(int j = 0 ; j < appDataFiles.length ; j++){
                            File child = appDataFiles[j];
                            if(null != child && child.getAbsolutePath().endsWith("cache")){
                                LogUtil.i("caculateCacheSize child ----> "+child);
                                size += getFolderSize(child);
                            }
                        }
                    }

                }
            }
        }

        return size;
    }

    /**
     * 计算某个文件及其子目录大小
     * @param file
     * @return
     */
    private long getFolderSize(File file){
        long size = 0;
        LogUtil.i("getFolderSize file ----> "+file);
        if(null == file){
            return 0;
        }
        if(file.isDirectory()){
            File[] childs = file.listFiles();
            if(null != childs){
                for(int i = 0; i < childs.length ; i++){
                    if(null != childs[i]){
                        size += getFolderSize(childs[i]);
                    }
                }
            }

        }else {
            size += file.length();
        }
        return size;
    }

    /**
     * 计算cache大小
     */
    private void caculateCache() {
        cacheSize = 0L;
        appSize = 0L;
        PackageManager pm = getPackageManager();
        List<PackageInfo> packages = pm.getInstalledPackages(PackageManager.GET_UNINSTALLED_PACKAGES);
        for(int i = 0 ; i < packages.size(); i++){

            try {
                getPkgSize(MainActivity.this,packages.get(i).packageName, i == packages.size() -1);
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

    }


    /**
     * 获取Android Native App的缓存大小、数据大小、应用程序大小
     *
     * @param context
     *            Context对象
     * @param pkgName
     *            需要检测的Native App包名
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    private void getPkgSize(final Context context, final String pkgName, final boolean isEnd) throws NoSuchMethodException,
            InvocationTargetException,
            IllegalAccessException {
        // getPackageSizeInfo是PackageManager中的一个private方法，所以需要通过反射的机制来调用
        Method method = PackageManager.class.getMethod("getPackageSizeInfo",
                new Class[] { String.class, IPackageStatsObserver.class });
        // 调用 getPackageSizeInfo 方法，需要两个参数：1、需要检测的应用包名；2、回调
        method.invoke(context.getPackageManager(), pkgName,
                new IPackageStatsObserver.Stub() {
                    @Override
                    public void onGetStatsCompleted(PackageStats pStats, boolean succeeded) throws RemoteException {

                        cacheSize += pStats.cacheSize;
                        appSize += pStats.cacheSize + pStats.dataSize + pStats.codeSize;
                        LogUtil.i("getPkgSize cacheSise: "+cacheSize);
                        LogUtil.i("getPkgSize pkgName: "+pkgName+
                                ", 缓存大小=" + Formatter.formatFileSize(context, pStats.cacheSize) +
                                ", 数据大小=" + Formatter.formatFileSize(context, pStats.dataSize) +
                                ", 程序大小=" + Formatter.formatFileSize(context, pStats.codeSize)+"\n");
                        if(isEnd){
                            mHandler.sendEmptyMessage(0);
                        }
                    }
                });



    }

    private void cleanPkgCache(final String pkgName){
        try {
            Method method = PackageManager.class.getMethod("deleteApplicationCacheFiles", String.class,
                    IPackageDataObserver.class);
            PackageManager pm = getPackageManager();
            method.invoke(pm, pkgName, new IPackageDataObserver.Stub() {
                @Override
                public void onRemoveCompleted(String packageName, boolean succeeded) throws RemoteException {
                    LogUtil.i("cleanPkgCache pkgName: "+pkgName+" clean cache succeed ! \n"
                    );
                }
            });
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 0:
                    LogUtil.i("getPkgSize end, appSize: "+appSize+", cacheSize: "+cacheSize);
                    tvCacheSize.setText("应用大小： "+Formatter.formatFileSize(MainActivity.this,
                            appSize)+" ,缓存大小："+Formatter.formatFileSize(MainActivity.this,
                            cacheSize));
                    break;
                case 1:
                    LogUtil.i("caculate app and cache size end, appsize:"+appSize+
                            ", cachesize:"+cacheSize);
                    tvCacheSize.setText("应用大小： "+Formatter.formatFileSize(MainActivity.this,
                            appSize)+" ,缓存大小："+Formatter.formatFileSize(MainActivity.this,
                            cacheSize));
                    break;
                default:
                    break;
            }
        }
    };

}
