package com.tao.niosearch;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.util.Set;

public class MainActivity extends AppCompatActivity {
    private String TAG = MainActivity.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        InputStream inputStream;


    }

    private void sd() throws IOException {
        ServerSocketChannel ssc = ServerSocketChannel.open();
        Selector selector = Selector.open();
        SelectionKey selectionKey = ssc.register(selector, SelectionKey.OP_ACCEPT);

        selector.selectNow();
        selector.select();

        Set<SelectionKey> selectionKeys = selector.selectedKeys();
    }

    public void read_file(View view) throws IOException {
        FileInputStream fis = new FileInputStream(new File(getFilesDir(),"test.txt"));
        //1000M 超了
        //100M 被允许
        byte[] buffer = new byte[300 * 1024 * 1024];
        int read = fis.read(buffer);
        Log.i(TAG,"read_file read = "+read);
        FileOutputStream fos = new FileOutputStream(new File(getFilesDir(),"11.txt"));
        fos.write(buffer);

        fis.close();
        fos.close();
    }


    public void copy_file(View view) throws IOException {
        //这种作为资源uri的方式
        //AssetFileDescriptor fileDescriptor = getAssets().openFd("file:///android_asset/test.txt");
        FileInputStream fileInputStream = new FileInputStream(new File(getFilesDir(),"test.txt"));
        FileChannel fcin = fileInputStream.getChannel();
        FileOutputStream fileOutputStream = new FileOutputStream(new File(getFilesDir(),"ddd.txt"));
        FileChannel fcout = fileOutputStream.getChannel();

        ByteBuffer buffer = ByteBuffer.allocate( 1024 );
        /*while (inFileChannel.read(buffer) != -1){
            buffer.flip();
            outFileChannel.write(buffer);
        }*/
        while (true){
            buffer.clear();
            int r1 = fcin.read(buffer);
            if (r1 == -1){
                break;
            }
            buffer.flip();
            fcout.write(buffer);
        }
    }

    public void write_file(View view) throws IOException {
        //nio写文件
        FileOutputStream fileOutputStream  = new FileOutputStream(new File(getFilesDir(),"test.txt"),true);
        FileChannel fileChannel = fileOutputStream.getChannel();
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        byteBuffer.put("12345677\n hello,this is a test txt!o".getBytes());
        byteBuffer.flip();
        fileChannel.write(byteBuffer);
    }
}
