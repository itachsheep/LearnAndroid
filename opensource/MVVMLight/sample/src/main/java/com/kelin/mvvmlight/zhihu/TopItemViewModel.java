package com.kelin.mvvmlight.zhihu;

import android.content.Context;
import android.databinding.ObservableField;
import android.widget.Toast;

import com.kelin.mvvmlight.base.ViewModel;
import com.kelin.mvvmlight.command.ReplyCommand;
import com.kelin.mvvmlight.zhihu.news.TopNewsService;

/**
 * Created by kelin on 16-4-26.
 */
public class TopItemViewModel implements ViewModel {
    //context
    private Context context;

    //model
    public TopNewsService.News.TopStoriesBean topStoriesBean;

    //field to presenter
    public final ObservableField<String> title = new ObservableField<>();
    public final ObservableField<String> imageUrl = new ObservableField<>();

    public final ReplyCommand topItemClickCommand = new ReplyCommand(() -> {
       /* Intent intent = new Intent(context, NewsDetailActivity.class);
        intent.putExtra(NewsDetailActivity.EXTRA_KEY_NEWS_IDopStoriesBean.getId());
        context.startActivity(intent);*/
        Toast.makeText(context,"hello 你好啊！！",Toast.LENGTH_SHORT).show();
    });

    public TopItemViewModel(Context context, TopNewsService.News.TopStoriesBean topStoriesBean) {
        this.context = context;
        this.topStoriesBean = topStoriesBean;
        title.set(topStoriesBean.getTitle());
        imageUrl.set(topStoriesBean.getImage());
    }
}
