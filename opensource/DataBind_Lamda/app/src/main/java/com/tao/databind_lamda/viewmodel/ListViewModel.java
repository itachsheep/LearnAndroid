package com.tao.databind_lamda.viewmodel;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.tao.databind_lamda.adapter.RecyAdapter;
import com.tao.databind_lamda.model.Employee;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by SDT14324 on 2017/10/16.
 */

public class ListViewModel {

    private Context mContext;
    private RecyAdapter<Employee> adapter;
    public ListViewModel(Context context){
        this.mContext = context;
    }

    public void addItem(View view){
        Employee employee = new Employee("haha","haha ,sb");
        adapter.add(employee);
    }

    public void deleteItem(View view){
        adapter.remove();
    }

    public void initReyclerView(RecyclerView recyclerView){
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        List<Employee> demoList = new ArrayList<>();
        demoList.add(new Employee("Zhai", "Mark"));
        demoList.add(new Employee("Zhai2", "Mark2"));
        demoList.add(new Employee("Zhai3", "Mark3"));
        demoList.add(new Employee("Zhai4", "Mark4"));

        adapter = new RecyAdapter<>(mContext,demoList);
        recyclerView.setAdapter(adapter);
        adapter.setItemClickListener(new RecyAdapter.ItemClickListeners() {
            @Override
            public void onItemClick(Employee t) {
                Toast.makeText(mContext,t.getFirstName(),Toast.LENGTH_SHORT).show();
            }
        });
    }

}
