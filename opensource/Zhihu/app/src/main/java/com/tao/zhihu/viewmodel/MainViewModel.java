package com.tao.zhihu.viewmodel;

import android.app.Activity;
import android.content.Context;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;

import com.tao.zhihu.BR;
import com.tao.zhihu.R;
import com.tao.zhihu.ZhihuApp;
import com.tao.zhihu.messenger.Messenger;
import com.tao.zhihu.model.TopNewsService;

import me.tatarka.bindingcollectionadapter.ItemView;
import rx.Observable;

/**
 * Created by SDT14324 on 2017/10/18.
 */

public class MainViewModel implements ViewModel{

    // Token to Messenger append package name to be unique
    public static final String TOKEN_UPDATE_INDICATOR = "token_update_indicator" + ZhihuApp.sPackageName;

    //context
    private Context context;


    // viewModel for recycler header viewPager
    public final ItemView topItemView = ItemView.of(BR.viewModel, R.layout.viewpager_item_top_news);

    public final ObservableList<TopItemViewModel> topItemViewModel = new ObservableArrayList<>();


    public MainViewModel(Activity activity){
        context=activity;
        Messenger.getDefault().register(activity, NewsViewModel.TOKEN_TOP_NEWS_FINISH, TopNewsService.News.class, (news) -> {
            Observable.just(news)
                    .doOnNext(m -> topItemViewModel.clear())
                    .flatMap(n -> Observable.from(n.getTop_stories()))
                    .doOnNext(m -> topItemViewModel.add(new TopItemViewModel(context,m)))
                    .toList()
                    .subscribe((l) -> Messenger.getDefault().sendNoMsgToTargetWithToken(TOKEN_UPDATE_INDICATOR, activity));
        });
    }


}
