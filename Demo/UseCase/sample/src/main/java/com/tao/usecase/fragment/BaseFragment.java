package com.tao.usecase.fragment;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.Fragment;
import com.tao.usecase.R;

/**
 * Created by SDT14324 on 2017/9/14.
 */

public class BaseFragment extends Fragment {


    public void addToBackStack(BaseFragment target,String tag){
        FragmentTransaction ft = getFragmentManager().beginTransaction();

        ft.replace(R.id.container_fragment,target);
        ft.addToBackStack(tag);
        ft.commit();
    }

    public void popBackStack(){
        FragmentManager fm = getFragmentManager();
        fm.popBackStack();
    }

   /* protected BaseFragment mCurrentFragment;
    private String FRAGMENT_TAG = "simple";*/
    /*protected void showFragment(BaseFragment fragment){
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        if(!fragment.isAdded()){
            ft.add(R.id.container_fragment,fragment,FRAGMENT_TAG);
        }

        if(mCurrentFragment != null){
            ft.hide(mCurrentFragment);
        }

        ft.show(fragment);
        ft.commitAllowingStateLoss();
        mCurrentFragment = fragment;
    }

    protected void hideFragment(){
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        if(null != mCurrentFragment){
            ft.hide(mCurrentFragment);
        }
        ft.commitAllowingStateLoss();
        mCurrentFragment = null;
    }*/
}
