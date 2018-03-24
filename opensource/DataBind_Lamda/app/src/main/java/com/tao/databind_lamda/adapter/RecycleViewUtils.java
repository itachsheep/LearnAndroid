package com.tao.databind_lamda.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.tao.databind_lamda.R;
import com.tao.databind_lamda.databinding.RecyUtilsItemBinding;

import java.util.List;

/**
 * Created by SDT14324 on 2017/10/20.
 */

public class RecycleViewUtils<K> {
    private Context context;
    private List<String> mList;

    public RecycleViewUtils(Context ctx, List<String> list){
        context = ctx;
        mList = list;
    }

    public void initRecycleView(RecyclerView recyclerView){
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        Adapter adapter = new Adapter();
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    private class Adapter extends RecyclerView.Adapter<ViewHolder>{

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(context);
            RecyUtilsItemBinding binding = DataBindingUtil.inflate(layoutInflater, R.layout.recy_utils_item,parent,false);

            return new ViewHolder(binding);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            RecyUtilsItemBinding binding = (RecyUtilsItemBinding) holder.getBinding();

            binding.setUvalue(mList.get(position));
            binding.executePendingBindings();
        }


        @Override
        public int getItemCount() {
            return mList.size();
        }
    }


    private class ViewHolder<T extends ViewDataBinding> extends RecyclerView.ViewHolder{
        T binding;
        public ViewHolder(T binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public T getBinding(){
            return binding;
        }
    }
}
