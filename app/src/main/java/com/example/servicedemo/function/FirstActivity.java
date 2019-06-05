package com.example.servicedemo.function;

import android.content.Context;
import android.content.Intent;
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
public class FirstActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);


        FunctionManager functionManager = FunctionManager.getInstance();
        functionManager.addFunction(new FunctionNoParamNoResult("NoParamNoResult") {
            @Override
            void function() {
                LogUtils.e("没有参数和没有返回值的方法");
            }
        });

        functionManager.addFunction(new FunctionNoParamHasResult("NoParamHasResult" ) {
            @Override
            public User function() {
                User user = new User("andy",22);
                return user;
            }
        });


        functionManager.addFunction(new FunctionHasParamHasResult<User,String>("HasParamHasResult") {
            @Override
            User function(String params) {
                User user = new User();
                user.setName(params);
                user.setAge(110);
                return user;
            }
        });


    }
    public static void start(Context context) {
        Intent starter = new Intent(context, FirstActivity.class);

        context.startActivity(starter);
    }


    public void goSecond(View view) {
        //跳转到SecondAct
        Intent mIntent = new Intent();
        mIntent.setClass(this,SecondActivity.class);
        startActivity(mIntent);
    }
}
