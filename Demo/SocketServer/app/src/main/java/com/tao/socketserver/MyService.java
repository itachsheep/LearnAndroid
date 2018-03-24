package com.tao.socketserver;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by SDT14324 on 2018/2/1.
 */

public class MyService extends Service {
    private String TAG = MyService.class.getSimpleName();
    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(TAG,"onCreate  ");

        new Thread(){
            @Override
            public void run() {
                Log.i(TAG,"startServer  ");
                BufferedReader in = null;
                PrintWriter writer = null;
                ServerSocket serverSocket = null;
                int i = 0;
                try {
                    ServerSocket server =new ServerSocket(5209);
                    Log.i(TAG,"服务端启动成功");
                    Socket socket = server.accept();
                    in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    writer = new PrintWriter(socket.getOutputStream());
                    while(true){
                        String str = in.readLine();
                        if(str != "" || str != null){
                            Log.i(TAG,"Client: "+str);
                            writer.println("hello !! I am server "+(i++));
                            writer.flush();
                        }
                    }
                } catch (IOException e) {
                    Log.i(TAG,"startServer  IOException "+e.getMessage());
                    e.printStackTrace();
                }finally {
                    try {
                        in.close();
                        writer.close();
                        serverSocket.close();
                    } catch (IOException e) {
                        Log.i(TAG,"startServer  close IOException "+e.getMessage());
                        e.printStackTrace();
                    }
                }
            }
        }.start();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
