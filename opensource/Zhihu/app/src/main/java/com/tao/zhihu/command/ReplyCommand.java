package com.tao.zhihu.command;

import rx.functions.Action0;
import rx.functions.Action1;

/**
 * Created by SDT14324 on 2017/10/19.
 */

public class ReplyCommand<T> {

    private Action0 action0;
    private Action1<T> action1;

    public ReplyCommand(Action1<T> action1){
        this.action1 = action1;
    }

    public void execute(T t){
        if(action1 != null)
            action1.call(t);
    }

    public  ReplyCommand(Action0 action){
        this.action0 = action;
    }

    public void execute(){
        if(action0 != null)
            action0.call();
    }
}
