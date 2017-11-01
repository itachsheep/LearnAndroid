package com.tao.testunit;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.tao.testunit.databinding.ActivityUnitTestBinding;

public class UnitTestActivity extends AppCompatActivity {

    ActivityUnitTestBinding binding;
    UnitTestViewModel viewmodel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_unit_test);
        viewmodel = new UnitTestViewModel(getApplicationContext());
        binding.setViewmodel(viewmodel);

    }

    public void sayHello(View view){
//        binding.utaText.setText("Hello,"+binding.utaEdit.getText().toString());
        binding.utaText.setText("Hello, " + binding.utaEdit.getText().toString() + "!");
    }


}
