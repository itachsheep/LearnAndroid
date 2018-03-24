package com.tao.zhihu.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.tao.zhihu.R;

import java.io.File;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class LambdaDeomActivity extends AppCompatActivity {


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

        //case 3 变量初始化
        TestCallable<String> callable = () -> "done";

        //case 4 变量初始化
        TestFileFilter filter = f -> f.getName();

        // case 6 变量初始化
        Comparator<String> c = (String s1,String s2) -> s1.compareTo(s2);

        //case 7 数组初始化
        TestFileFilter[] filter3 = new TestFileFilter[] {
            f -> f.getName(), f -> f.getAbsolutePath()
        };

        //case 8 map(Function<Person,String>)将person转化为string对象
        List<Person> ps = new ArrayList<>();
        Stream<String> names = ps.stream().map(p -> p.getName());

        //case 9 通过外部目标类型推导出其内部的返回类型
        Supplier<Runnable> sr = new Supplier<Runnable>() {
            @Override
            public Runnable get() {
                return null;
            }
        };

        Supplier<Runnable> sp = () -> () -> System.out.println("ddd");

        //case 10 条件表达式
        Callable<Integer> cl = new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                return null;
            }
        };

        boolean flag = true;
        Callable<Integer> cla = flag ? () -> 23 : () -> 24 ;


        //case 11 强制类型转换
        Object o = (Runnable)() -> {System.out.println("haha!!");System.out.println("hehe!!");};

        //case 12 'this'关键字及其引用在lambda表达式内部和外部也拥有相同的语义。
        Runnable rn = () -> {System.out.println(this.toString());}; //this 指向MainActivity而不是Runnable

        //case 13 有效只读（Effectively final）的局部变量,如果一个局部变量在初始化后从未被修改过,那么它就符合有效只读的要求
        String s = "hello world! ";
        Runnable ra = () -> System.out.println(s);

        //case 14  方法引用, 如果我们想要调用的方法拥有一个名字，我们就可以通过它的名字直接调用它
        /*Comparator<Person> cp = Comparator.comparing(new Function<Person, String>() {
            @Override
            public String apply(Person person) {
                return null;
            }
        });*/
        Function<Person,String> rr = p -> p.getName();

        /*Comparator<Person> cpa = Comparator.comparing(p -> p.getName());

        Comparator<Person> cmp = Comparator.comparing(Person::getName);*/

        TestFileFilter fi = File::getAbsolutePath;

        //case 15
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
//        default void test_default(){};
//        static void test_static(){};
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
