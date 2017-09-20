package com.java.mvpdemo.mvp.presenter;

import com.java.mvpdemo.mvp.model.GreetingGeneratorTask;
import com.java.mvpdemo.mvp.view.HelloWorldView;

/**
 * Created by SDT14324 on 2017/9/20.
 */

public class HelloWorldPresenter extends MvpBasePresenter<HelloWorldView> {
    private GreetingGeneratorTask greetingTask;

    private void cancelGreetingTaksIfRunning(){
        if(greetingTask != null){
            greetingTask.cancel(true);
        }
    }


    public void greetHello(){
        cancelGreetingTaksIfRunning();

        greetingTask = new GreetingGeneratorTask("Hello", new GreetingGeneratorTask.GreetingTaskListener() {
            @Override
            public void onGreetingGenerated(String txt) {
                if(isViewAttached()){
                    getView().showHello(txt);
                }
            }
        });
        greetingTask.execute();
    }


    public void greetGoodBye(){
        cancelGreetingTaksIfRunning();
        greetingTask = new GreetingGeneratorTask("GoodBye", new GreetingGeneratorTask.GreetingTaskListener() {
            @Override
            public void onGreetingGenerated(String txt) {
                if(isViewAttached()){
                    getView().showGoodbye(txt);
                }
            }
        });
        greetingTask.execute();
    }


    @Override
    public void detachView(boolean retainInstance) {
        super.detachView(retainInstance);
        if(!retainInstance){
            cancelGreetingTaksIfRunning();
        }
    }
}
