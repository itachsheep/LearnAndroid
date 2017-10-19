package com.tao.zhihu.viewmodel;

import android.content.Context;
import android.databinding.ObservableField;
import android.widget.Toast;

import com.tao.zhihu.command.ReplyCommand;
import com.tao.zhihu.model.TopNewsService;

/**
 * Created by SDT14324 on 2017/10/19.
 */

public class TopItemViewModel implements ViewModel {

    private Context ctx;

    //model
    public TopNewsService.News.TopStoriesBean topStoriesBean;

    //field to present
    public final ObservableField<String> title = new ObservableField<>();
    public final ObservableField<String> imageUrl = new ObservableField<>();


    public final ReplyCommand topItemClickCommand = new ReplyCommand(() -> {
        Toast.makeText(ctx,"topItemClickCommand !!!",Toast.LENGTH_SHORT).show();
    });


    public TopItemViewModel(Context context,TopNewsService.News.TopStoriesBean bean){
        this.ctx = context;
        this.topStoriesBean = bean;
        title.set(bean.getTitle());
        imageUrl.set(bean.getImage());
    }


}
