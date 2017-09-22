package com.java.mvvmdemo.base;

import android.app.Activity;
import android.content.Context;

import com.java.mvvmdemo.BR;
import com.java.mvvmdemo.R;

import me.tatarka.bindingcollectionadapter.ItemView;

/**
 * Created by SDT14324 on 2017/9/22.
 */

public class BaseViewModel implements ViewModel{

    private Context context;

    // viewModel for recycler header viewPager
    public final ItemView topItemView = ItemView.of(BR.viewModel, R.layout.viewpager_item_top_news);
    public final
    public BaseViewModel(Activity activity){
        context = activity;
//        Messenger.getDefault().register(activity, NewsViewModel.TOKEN_TOP_NEWS_FINISH,
//                TopNewsModel.News.class,(news) -> {
//                    Observable.just(news)
//                            .doOnNext(m -> )
//                });
    }
}
