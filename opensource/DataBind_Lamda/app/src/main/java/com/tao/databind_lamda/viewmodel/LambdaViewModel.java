package com.tao.databind_lamda.viewmodel;

import android.content.Context;
import android.view.View;
import android.widget.Toast;

import com.tao.databind_lamda.model.Employee;

/**
 * Created by SDT14324 on 2017/10/17.
 */

public class LambdaViewModel {

    private Context mContext;
    private Employee employee;

    public LambdaViewModel(Context ctx){
        this.mContext = ctx;
    }

    public void test(View view){
        Toast.makeText(mContext,"test click !!",Toast.LENGTH_SHORT).show();
    }


    public void onEmployeeClick(Employee employee){
        Toast.makeText(mContext," employee click !!!",Toast.LENGTH_SHORT).show();
    }

    public void onEmployeeLongClick(Employee employee,Context context){
        Toast.makeText(mContext," employee long click : "+employee.getFirstName(),Toast.LENGTH_SHORT).show();
    }

}
