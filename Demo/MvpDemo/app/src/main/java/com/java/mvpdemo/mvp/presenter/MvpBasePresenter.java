package com.java.mvpdemo.mvp.presenter;

import android.support.annotation.UiThread;

import com.java.mvpdemo.mvp.common.MvpPresenter;
import com.java.mvpdemo.mvp.common.MvpView;

import java.lang.ref.WeakReference;

/**
 * Created by SDT14324 on 2017/9/20.
 */

public class MvpBasePresenter<V extends MvpView> implements MvpPresenter<V> {
    private WeakReference<V> viewRef;
    @Override
    public void attachView(V view) {
        viewRef = new WeakReference<V>(view);
    }

    @UiThread
    public V getView(){
        return viewRef == null ? null : viewRef.get();
    }

    @UiThread
    public boolean isViewAttached() {
        return viewRef != null && viewRef.get() != null;
    }

    @Override
    public void detachView(boolean retainInstance) {
        if (viewRef != null) {
            viewRef.clear();
            viewRef = null;
        }
    }
}
