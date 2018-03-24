package com.tao.viewlearn.handler;

/**
 * Created by taow on 2017/8/8.
 */

public class CrashHandler implements Thread.UncaughtExceptionHandler {
    @Override
    public void uncaughtException(Thread t, Throwable e) {
        //异常处理线程 t, 未捕获异常 e
        this.uncaughtException(t,e);
        //将异常e 信息写入文件或者上传服务器。
    }
}
