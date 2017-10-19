package com.tao.zhihu.fragment;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tao.zhihu.R;
import com.tao.zhihu.databinding.FragmentNewListBinding;
import com.tao.zhihu.viewmodel.NewsViewModel;
import com.trello.rxlifecycle.components.RxFragment;

/**
 * Created by SDT14324 on 2017/10/19.
 */

public class NewsListFragment extends RxFragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        FragmentNewListBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_new_list, container, false);
        binding.setViewModel(new NewsViewModel(this));
        initView(binding);
        return binding.getRoot();

    }

    private void initView(FragmentNewListBinding binding) {
        binding.recyclerView.addItemDecoration(new DividerItemDecoration(this.getActivity()));
    }

    public static class DividerItemDecoration extends RecyclerView.ItemDecoration {
        private Drawable mDivider;

        public DividerItemDecoration(Context context) {
            mDivider = context.getResources().getDrawable(R.drawable.divider);
        }

        @Override
        public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
            int left = parent.getPaddingLeft();
            int right = parent.getWidth() - parent.getPaddingRight();

            int childCount = parent.getChildCount();
            for (int i = 0; i < childCount; i++) {
                View child = parent.getChildAt(i);

                RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();

                int top = child.getBottom() + params.bottomMargin;
                int bottom = top + mDivider.getIntrinsicHeight();

                mDivider.setBounds(left, top, right, bottom);
                mDivider.draw(c);
            }
        }
    }
}
