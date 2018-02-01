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
import java.net.InetAddress;
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
                PrintWriter outer = null;
                ServerSocket serverSocket = null;
                try {
//                    serverSocket = new ServerSocket(8888);
                    serverSocket=new ServerSocket(8000,10, InetAddress.getByName ("192.168.1.111"));

                    byte[] buffer = new byte[20 * 1024];
                    Socket socket = serverSocket.accept();
                    while (true){
                        Log.i(TAG,"startServer  accept ");

//                        InputStream inputStream = socket.getInputStream();
                        in =new BufferedReader(new InputStreamReader(socket.getInputStream()));
                        outer =new PrintWriter(socket.getOutputStream());
//                        String s = "";
//                        while (inputStream.read(buffer) != -1){
//                            s = new String(buffer);
//                        }
                        String receiveMes = in.readLine();
                        Log.i(TAG,"startServer  receiver s "+receiveMes);
                    }
                } catch (IOException e) {
                    Log.i(TAG,"startServer  IOException "+e.getMessage());
                    e.printStackTrace();
                }finally {
                    try {
                        in.close();
                        outer.close();
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
