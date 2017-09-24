package com.java.mvvmdemo.base;

import android.app.Activity;
import android.content.Context;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;

import com.java.mvvmdemo.BR;
import com.java.mvvmdemo.R;
import com.java.mvvmdemo.model.TopItemViewModel;

import me.tatarka.bindingcollectionadapter.ItemView;

/**
 * Created by SDT14324 on 2017/9/22.
 */

public class BaseViewModel implements ViewModel{

    private Context context;

    // viewModel for recycler header viewPager
    public final ItemView topItemView = ItemView.of(BR.viewModel, R.layout.viewpager_item_top_news);
    public final ObservableList<TopItemViewModel> topItemViewModels = new ObservableArrayList<>();
    public BaseViewModel(Activity activity){
        context = activity;
//        Messenger.getDefault().register(activity, NewsViewModel.TOKEN_TOP_NEWS_FINISH,
//                TopNewsModel.News.class,(news) -> {
//                    Observable.just(news)
//                            .doOnNext(m -> )
//                });
    }
}
