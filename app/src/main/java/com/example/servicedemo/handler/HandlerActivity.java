package com.example.servicedemo.handler;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.servicedemo.R;

/**
 * @author andysong
 * @data 2019/4/18
 * @discription xxx
 */
public class HandlerActivity extends AppCompatActivity {

    private Button mButton;


    private Handler mHandler;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handler);
        //1.在主线程中创建Handler实例
//        mHandler = new Handler();
        mButton = findViewById(R.id.btn_handler);
//        mButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                new Thread(){
//                    @Override
//                    public void run() {
//                        try {
//                            Thread.sleep(3000);
//                        } catch (InterruptedException e) {
//                            e.printStackTrace();
//                        }
//                        //步骤2
//                        mHandler.post(new Runnable() {
//                            @Override
//                            public void run() {
//                                mButton.setText("Handler.post");
//                            }
//                        });
//                    }
//                }.start();// 步骤3：开启工作线程（同时启动了Handler）
//
//            }
//        });
        mHandler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what)
                {
                    case 22:
                        mButton.setText("xxx");
                        break;

                        default:
                            super.handleMessage(msg);
                            break;
                }

            }
        };


        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        //这两种方法都比较好，比起直接new Message，节省了内存空间开辟的消耗
//                        Message msg = mHandler.obtainMessage();
                        Message msg = Message.obtain();

                        mHandler.sendMessage(msg);
                    }
                }).start();
            }
        });












        //sendMessage

    }



}
