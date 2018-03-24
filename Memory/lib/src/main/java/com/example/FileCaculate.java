package com.example;

import java.io.File;
import java.text.Format;
import java.util.Formatter;

public class FileCaculate {
    private static long cacheSize;
    private static long appSize;
    public static void main(String[] args){
        System.out.println("1111...");

        File data = new File("D:\\111");

        System.out.println("data length: "+data.length());
        caculateAppAndCacheSize();
    }

    /**
     * 手动计算app 和 cache 大小
     */
    private static void caculateAppAndCacheSize(){
        new Thread(){
            @Override
            public void run() {
                File dataFile = new File("D:\\111");
                File[] apps = dataFile.listFiles();
                if(null != apps){
                    appSize = getFolderSize(dataFile);
                    System.out.println("appSize: "+ appSize / 1024/1024 +"Mb");
                }
                if(null != dataFile){
                    cacheSize = caculateCacheSize(dataFile);
                    System.out.println("cacheSize: "+ cacheSize / 1024/1024 +"Mb");
                }

                //mHandler.sendEmptyMessage(1);
            }
        }.start();
    }

    /**
     *计算
     * @param dataFile
     */
    private static long caculateCacheSize(File dataFile) {
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
                                System.out.println("caculateCacheSize child: "+child);
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
    private static long getFolderSize(File file){
        long size = 0;
        System.out.println("getFolderSize file ----> "+file);
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
}
