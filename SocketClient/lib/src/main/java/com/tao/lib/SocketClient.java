package com.tao.lib;

/**
 * Created by SDT14324 on 2018/2/1.
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class SocketClient {
    // 搭建客户端
    public static void main(String[] args) throws IOException {
        PrintWriter write = null;
        BufferedReader in = null;
        Socket socket = null;
        try {
            socket = new Socket("127.0.0.1", 5209);
            System.out.println("客户端启动成功");
            write = new PrintWriter(socket.getOutputStream());
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            int i = 0;
            while (true){
                write.println("hello i am client "+(i++));
                write.flush();
                Thread.sleep(1000);
            }
        } catch (Exception e) {
            System.out.println("can not listen to:" + e);// 出错，打印出错信息
        }finally {
            write.close(); // 关闭Socket输出流
            in.close(); // 关闭Socket输入流
            socket.close(); // 关闭Socket
        }
    }

}
