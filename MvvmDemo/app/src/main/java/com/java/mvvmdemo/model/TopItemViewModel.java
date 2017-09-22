package com.java.mvvmdemo.model;

import android.content.Context;
import android.content.Intent;
import android.databinding.ObservableField;

import com.java.mvvmdemo.base.ViewModel;
import com.java.mvvmdemo.lib.command.ReplyCommand;
import com.java.mvvmdemo.view.NewsDetailActivity;

/**
 * Created by SDT14324 on 2017/9/22.
 */

public class TopItemViewModel implements ViewModel{
    //context
    private Context context;
    //model
    public TopNewsModel.News.TopStoriesBean topStoriesBean;

    //field to presenter
    public final ObservableField<String> title = new ObservableField<>();
    public final ObservableField<String> imageUrl = new ObservableField<>();

    public final ReplyCommand topItemClickCommand = new ReplyCommand(() -> {
        Intent intent = new Intent(context, NewsDetailActivity.class);
        intent.putExtra(NewsDetailActivity.EXTRA_KEY_NEWS_ID, topStoriesBean.getId());
        context.startActivity(intent);
    });

    public TopItemViewModel(Context context, TopNewsModel.News.TopStoriesBean topStoriesBean) {
        this.context = context;
        this.topStoriesBean = topStoriesBean;
        title.set(topStoriesBean.getTitle());
        imageUrl.set(topStoriesBean.getImage());
    }
}
