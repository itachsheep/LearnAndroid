package com.tao.usecase.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
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

public class AddFragment1 extends BaseFragment {
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.i("Test","AddFragment1 onAttach ");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("Test","AddFragment1 onCreate ");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.i("Test","AddFragment1 onStart ");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i("Test","AddFragment1 onResume ");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.i("Test","AddFragment1 onDestroyView ");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.i("Test","AddFragment1 onPause ");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.i("Test","AddFragment1 onStop ");
    }
    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        Log.i("Test","AddFragment1 onHiddenChanged hidden:  "+hidden);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        Log.i("Test","AddFragment1 setUserVisibleHint isVisibleToUser:  "+isVisibleToUser);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        Log.i("Test","AddFragment1 onSaveInstanceState ");
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.i("Test","AddFragment1 onDetach ");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i("Test","AddFragment1 onDestroy ");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        Log.i("Test","AddFragment1 onCreateView ");
        View view = inflater.inflate(R.layout.fragment_add1,container,false);
        ButterKnife.bind(this,view);
      /*  List<Fragment> frglist = getFragmentManager().getFragments();
        for (Fragment frg:frglist) {
            Log.i("Test","AddFragment1 oncreat frg: "+frg);
        }*/

        btClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    addToBackStack(new AddFragment2(),"tag2");
//                ((FullscreenAndFragmentActivity)getActivity()).showFragment(new AddFragment2());
            }
        });
        return view;
    }
    @BindView(R.id.fg_bt_clickme)Button btClick;
    private void initListener() {

    }

}
