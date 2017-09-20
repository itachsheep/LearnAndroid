package com.java.mvpdemo.mvp.common;

import android.support.annotation.UiThread;

/**
 * Created by SDT14324 on 2017/9/20.
 */

public interface MvpPresenter<V extends MvpView> {
    @UiThread
    void attachView(V view);

    @UiThread
    void detachView(boolean retainInstance);
}
