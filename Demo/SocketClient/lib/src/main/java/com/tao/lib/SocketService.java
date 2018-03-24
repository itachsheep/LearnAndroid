package com.tao.lib;

/**
 * Created by SDT14324 on 2018/2/1.
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketService {
    //搭建服务器端
    public static void main(String[] args) throws IOException {
        SocketService socketService = new SocketService();
        socketService.oneServer();
    }
    public  void oneServer(){
        try{
            ServerSocket server =new ServerSocket(5209);
            System.out.println("服务端启动成功");
            Socket socket = server.accept();
            BufferedReader in=new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter writer=new PrintWriter(socket.getOutputStream());
            while(true){
                String str = in.readLine();
                if(str != "" || str != null){
                    System.out.println("Client: "+str);
                }
            }
        }catch(Exception e) {//出错，打印出错信息
            System.out.println("Error."+e);
        }finally {

        }
    }
}
