package com.example.servicedemo.function;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.blankj.utilcode.util.LogUtils;
import com.example.servicedemo.R;

/**
 * @author andysong
 * @data 2019-06-05
 * @discription xxx
 */
public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);


    }


    public void getData(View view) {
        FunctionManager.getInstance().inovkeFunction("NoParamNoResult");
        User user = FunctionManager.getInstance().inovkeFunction("NoParamHasResult", User.class);
        LogUtils.e(user.toString());


        User user1 = FunctionManager.getInstance().inovkeFunction("HasParamHasResult", User.class, "Double Jack");
        LogUtils.e(user1.toString());
    }
}
