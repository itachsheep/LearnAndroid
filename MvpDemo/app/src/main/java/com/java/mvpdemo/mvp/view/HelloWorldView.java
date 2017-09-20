package com.java.mvpdemo.mvp.view;

import com.java.mvpdemo.mvp.common.MvpView;

/**
 * Created by SDT14324 on 2017/9/20.
 */

public interface HelloWorldView extends MvpView {
    void showHello(String txt);
    void showGoodbye(String txt);
}
