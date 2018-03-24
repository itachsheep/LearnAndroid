package com.tao.databind_lamda.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.tao.databind_lamda.BR;
import com.tao.databind_lamda.R;
import com.tao.databind_lamda.model.Employee;

import java.util.List;

/**
 * Created by SDT14324 on 2017/10/16.
 */

public class RecyAdapter<T> extends RecyclerView.Adapter<RecyViewHolder> {


    private Context mContext;
    private List<T> mlist ;
    private ItemClickListeners itemClickListener;

    public interface ItemClickListeners {
        void onItemClick(Employee t);
    }

    public void setItemClickListener(ItemClickListeners listener){
        this.itemClickListener = listener;
    }


    public RecyAdapter(Context context,List<T> list){
        this.mContext = context;
        this.mlist = list;
    }

    @Override
    public RecyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        ViewDataBinding binding = DataBindingUtil.inflate(inflater, R.layout.recy_item,parent,false);
        return new RecyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(RecyViewHolder holder, int position) {
        final T t = mlist.get(position);
        ViewDataBinding binding =  holder.getmBinding();
//        binding.setItem((Employee) t);
//        binding.setVariable(BR.item,)
        binding.setVariable(BR.item,t);
        binding.executePendingBindings();

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(itemClickListener != null){
                    itemClickListener.onItemClick((Employee) t);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return mlist == null ? 0: mlist.size();
    }

    public void add(T t){
        mlist.add(t);
        notifyDataSetChanged();
    }

    public void remove(){
        mlist.remove(mlist.size()-1);
        notifyDataSetChanged();
    }
}
