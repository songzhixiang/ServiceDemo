package com.example.servicedemo;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button mStartButton;
    Button mButtonStop;
    Button mStartButtonBind;
    Button mButtonStopBind;
    MyBinder mBinder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mStartButton = findViewById(R.id.btn_start);
        mButtonStop = findViewById(R.id.btn_stop);
        mStartButtonBind = findViewById(R.id.btn_start_bind);
        mButtonStopBind = findViewById(R.id.btn_stop_bind);
        mStartButton.setOnClickListener(this);
        mButtonStop.setOnClickListener(this);
        mStartButtonBind.setOnClickListener(this);
        mButtonStopBind.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this,MyService.class);
        switch (v.getId())
        {
            case R.id.btn_start:

                startService(intent);

                break;

            case R.id.btn_stop:
                stopService(intent);
                break;

            case R.id.btn_start_bind:
                bindService(new Intent(this,BindService.class),mConnection, Context.BIND_AUTO_CREATE);
                break;

            case R.id.btn_stop_bind:
                unbindService(mConnection);
                break;
        }
    }


    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            // 绑定成功后回调
            //1 ,获取Binder接口对象
            mBinder = (MyBinder) service;
            //2, 从服务获取数据
            String content = mBinder.getStringInfo();
            // 3,界面提示
            Toast.makeText(MainActivity.this,content,Toast.LENGTH_SHORT).show();

        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mBinder = null;
        }
    };
}
