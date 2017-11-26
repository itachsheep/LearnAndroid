package com.tao.aidlpa;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;

/**
 * Created by taowei on 2017/11/15.
 * 2017-11-15 22:38
 * AIDLPa
 * com.tao.aidlpa
 */

public class MyService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        /**
         * 进程a ： stub + proxy,
         *          其中stub 服务端
         *          proxy：客户端
         *
         *
         * 进程b：stub + proxy,
         *          其中stub 服务端
         *          proxy：客户端
         */

        IMyTest.Stub stub = new IMyTest.Stub() {

            @Override
            public void testBinder() throws RemoteException {

            }

            @Override
            public String getBinderString() throws RemoteException {
                return null;
            }
        };
        return stub;
    }


}
