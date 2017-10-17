package com.tao.zhihu.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.tao.zhihu.R;

import java.io.File;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //case 1 轻量级的语法，从而解决了匿名内部类带来的“高度问题”。
        Thread t1 = new Thread(() -> System.out.println("haha !!!"));


        //case 2 捕获非final的局部变量
        int x = 1,y = 2;
        AddListener listener = ((x1, y1) -> x+y);
        int res = listener.add(x,y);

        //case 3
        TestCallable<String> callable = () -> "done";

        //case 4
        TestFileFilter filter = f -> f.getName();

        // case 6
        Comparator<String> c = (String s1,String s2) -> s1.compareTo(s2);

        //case 7
        TestFileFilter[] filter3 = new TestFileFilter[] {
            f -> f.getName(), f -> f.getAbsolutePath()
        };

        //case 8
        List<Person> ps = new ArrayList<>();
        Stream<String> names = ps.stream().map(p -> p.getName());
    }

    //case 5
    public Runnable getRunnable(){
        return () -> {System.out.println("get runnable");};
    }

    class Person{
        private String name;
        public String getName(){
            return name;
        }
    }

    interface TestFileFilter {
        String filename(File file);
    }

    interface TestCallable<String> {
        String call();
    }



    interface AddListener{
        int add(int x,int y);
    }

    public String doPrivileged(String name){
        return  name+"111";
    }

}
