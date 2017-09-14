package com.tao.usecase.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.tao.usecase.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by SDT14324 on 2017/9/14.
 */

public class TestButterKnifeFragment extends BaseFragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_test,container,false);
        ButterKnife.bind(this,view);

        /*List<Fragment> frglist = getFragmentManager().getFragments();
        for (Fragment frg:frglist) {
            Log.i("Test","TestButterKnifeFragment oncreate frg: "+frg);
        }*/
        btClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                synchronized (TestButterKnifeFragment.class){


//                    ((FullscreenActivity)getActivity()).showFragment(new AddFragment1());
                    addToBackStack(new AddFragment1(),"tag");
                }

            }
        });
        return view;
    }
    @BindView(R.id.fg_bt_clickme)Button btClick;
    private void initListener() {

    }
}
