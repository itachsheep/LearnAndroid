package activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;

import andorid.taow.selfview.R;
import util.LogUtil;
import view.AutoTextView;
import view.PageTextView;

/**
 * Created by taow on 2017/5/31.
 */

public class TextViewActivity extends Activity implements View.OnClickListener {
    private Button bt;
    private Button btExec;
    private AutoTextView autoTextView;
    private PageTextView pageTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_textview);
        initView();
        initListener();
        initData();
    }

    private void initData() {
        pageTextView.setText("版权声明：本文为博主原创文章，未经博主允许不得转载。\n" +
                "\n" +
                "写了一个非常小的阅读器。在实现分页功能时，一直没有思路。后来想了一个非常特别的方法。经过测试可以完美的实现分页功能。\n" +
                "\n" +
                "主要思路：\n" +
                "\n" +
                "1、将文本内容填充到TextView中，调用setText一句搞定。\n" +
                "\n" +
                "2、计算TextView的高度范围内可显示的行数。如果TextView占据整个屏幕则计算屏幕范围可显示的的函数。\n" +
                "\n" +
                "利用TextView 的getLineBounds 函数可以计算每行占据的高度h。利用 h 和TextView的高度 H 就可以很方便计算可显示的行数。\n" +
                "\n" +
                "3、最关键的一步。计算TextView   n 行显示的字体个数。\n" +
                "\n" +
                "这是最关键的一个API，能够实现这个功能主要靠它。而且TextView本身也是借助这个API实现自动换行的。\n" +
                "\n" +
                "这就是StatiLayout。StatiLayout有一个函数getLineEnd（n）可以计算从0到n行字体的个数。TextView 一页显示的行数是固定的，\n" +
                "\n" +
                "分页的难点就是每行的字体个数不固定。通过getLineEnd 就可以非常简单的计算每页的字体个数。\n" +
                "\n" +
                "4、通过每页的字体个数从文本内容中截取每页的内容。\n" +
                "\n" +
                "使用了一个PagerAdapter 将文本内容创建为一个TextView，这样就可以滑动分页了.\n" +
                "\n" +
                "关键代码：\n" +
                "\n" +
                "        说明：代码主要是说明分页思路，其中有不少bug");
    }

    private void initView() {
        bt = (Button) findViewById(R.id.textact_bt);
        btExec = (Button) findViewById(R.id.tva_bt_exec);
        autoTextView = (AutoTextView) findViewById(R.id.atv_tv);
        pageTextView = (PageTextView) findViewById(R.id.tva_pageTv);
    }

    private void initListener() {
        bt.setOnClickListener(this);
        btExec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


//        makeFilePath(getCacheDir().getAbsolutePath(),"test.sh");
//        writeTxtToFile("#!/bin/bash\nbusybox ifconfig eth0 down",getCacheDir().getAbsolutePath(),"test.sh");
//                ProcessBuilder cmd,cmd2;
//                LogUtil.i("TestActivity.onClick line: " + getCacheDir().getAbsolutePath()+"/test.sh");
//                String[] args = { "/system/bin/touch",getCacheDir().getAbsolutePath()+"/test.sh"};
//                cmd = new ProcessBuilder(args);
//                try {
//                    cmd.start();
//                    //echo "busybox ifconfig eth0 down" > test.sh
//                    Runtime.getRuntime().exec("busybox echo 'busybox ifconfig eth0 down'>/data/data/andorid.taow.selfview/cache/test.sh");
//            String[] args2 = { "echo 'busybox ifconfig eth0 down' > /data/data/andorid.taow.selfview/cache/test.sh"};
//            cmd2 = new ProcessBuilder(args2);
//            cmd2.start();
//
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }

                try {
//                    Runtime.getRuntime().exec("ifconfig eth0 down",null,new File("/system/bin"));

//                    Process pp = Runtime.getRuntime().exec(new String[]{"/system/bin/","/system/bin/ifconfig eth0 down"});
//                    Process pp = Runtime.getRuntime().exec(new String[]{"su -c ifconfig eth0 down"});
                    ProcessBuilder cmd;
                    //"eth0 down"
                    String[] args = { "/system/bin/ifconfig ","eth0 down"};
                    cmd = new ProcessBuilder(args);
                    Process pp = cmd.start();
                    InputStream in = pp.getInputStream();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));
                    StringBuilder stringBuilder = new StringBuilder();
                    String line;
                    while ((line = bufferedReader.readLine()) != null) {
                        LogUtil.i("TestActivity.onClick line: " + line);
                        stringBuilder.append(line);
                    }
                    LogUtil.i("TestActivity.onClick---------------sb :---------" + stringBuilder.toString());

                } catch (Exception e) {
                    LogUtil.i("TestActivity.onClick-----runtime exec failed----" + e.getMessage());    //write into flash
                }
            }
        });
    }


    public void writeTxtToFile(String strcontent, String filePath, String fileName) {
        //生成文件夹之后，再生成文件，不然会出错
        makeFilePath(filePath, fileName);

        String strFilePath = filePath+"/"+fileName;
        // 每次写入时，都换行写
        String strContent = strcontent + "\r\n";
        try {
            File file = new File(strFilePath);
            if (!file.exists()) {
                Log.d("TestFile", "Create the file:" + strFilePath);
                file.getParentFile().mkdirs();
                file.createNewFile();
            }
            RandomAccessFile raf = new RandomAccessFile(file, "rwd");
            raf.seek(file.length());
            raf.write(strContent.getBytes());
            raf.close();
        } catch (Exception e) {
            Log.e("TestFile", "Error on write File:" + e);
        }
    }


    // 生成文件
    public void makeFilePath(String filePath, String fileName) {
        ProcessBuilder cmd;
        LogUtil.i("TestActivity.onClick line: " + getCacheDir().getAbsolutePath()+"/test.sh");
        String[] args = { "/system/bin/touch",getCacheDir().getAbsolutePath()+"/test.sh"};
        cmd = new ProcessBuilder(args);
        try {
            Process pp = cmd.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
//        File file = null;
////        makeRootDirectory(filePath);
//        try {
//            file = new File(filePath+"/"+fileName);
//            if (!file.exists()) {
//                file.createNewFile();
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return file;
    }

    // 生成文件夹
    public static void makeRootDirectory(String filePath) {
        File file = null;
        try {
            file = new File(filePath);
            if (!file.exists()) {
                file.mkdir();
            }
        } catch (Exception e) {
            Log.i("error:", e+"");
        }
    }










    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.textact_bt){
//            pageTextView.resize();
        }
    }
}
