package com.tao.zhihu.utils;

import android.util.Log;

import com.tao.zhihu.retrofit.ApiException;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import rx.Observable;
import rx.subjects.ReplaySubject;

/**
 * Created by SDT14324 on 2017/10/19.
 */

public class CommonUtils {
    public static boolean isTomorrow(String date) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        return new SimpleDateFormat("yyyyMMdd").format(calendar.getTime()).equals(date);
    }

    public static void dealWithResponseError(Observable<Throwable> throwableObservable) {

        ReplaySubject<Throwable> throwableReplaySubject = ReplaySubject.create();

        throwableObservable.subscribe(throwableReplaySubject);

        throwableReplaySubject
                .repeat(5)
                .scan((n, c) -> n.getCause())
                .takeUntil(n -> n.getCause() == null)
                .filter(n -> n instanceof ApiException)
                .cast(ApiException.class)
                .subscribe(e -> Log.v("error", e.msg));


    }

}
