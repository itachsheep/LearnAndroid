package com.java.mvpdemo.mvp.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.TextView;

import com.java.mvpdemo.R;
import com.java.mvpdemo.mvp.common.MvpActivity;
import com.java.mvpdemo.mvp.presenter.HelloWorldPresenter;
import com.java.mvpdemo.mvp.view.HelloWorldView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HelloWorldActivity extends MvpActivity<HelloWorldView,HelloWorldPresenter>
    implements HelloWorldView{
    String tag = HelloWorldActivity.class.getSimpleName();

    @BindView(R.id.greetingTextView)
    TextView greetingTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @NonNull
    @Override
    public HelloWorldPresenter createPresenter() {
        return new HelloWorldPresenter();
    }

    @OnClick(R.id.helloButton)
    public void hello(){
        Log.i(tag,"hello click ....");
        presenter.greetHello();;
    }

    @OnClick(R.id.goodbyeButton)
    public void bye(){
        Log.i(tag,"bye click ....");
        presenter.greetGoodBye();
    }


    @Override
    public void showHello(String txt) {
        greetingTextView.setTextColor(Color.RED);
        greetingTextView.setText(txt);
    }

    @Override
    public void showGoodbye(String txt) {
        greetingTextView.setTextColor(Color.BLUE);
        greetingTextView.setText(txt);
    }
}
