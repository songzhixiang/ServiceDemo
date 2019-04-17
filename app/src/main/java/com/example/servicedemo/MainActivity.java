package com.example.servicedemo;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.servicedemo.messenger.MessengerService;

import static com.example.servicedemo.messenger.MessengerService.MESSAGE_FROM_CLIENT;
import static com.example.servicedemo.messenger.MessengerService.MESSAGE_FROM_SERVICE;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button mStartButton;
    Button mButtonStop;
    Button mStartButtonBind;
    Button mButtonStopBind;
    MyBinder mBinder;

    private Messenger mService;
    private Messenger mClientMessenger = new Messenger(new MessengerHandler());
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

    //用于构建客户端的Messenger对象，并处理服务端的消息
    private static class MessengerHandler extends Handler{
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case MESSAGE_FROM_SERVICE:
                    Log.e("szx",msg.getData().getString("msg"));
                    break;


                default:
                    super.handleMessage(msg);

                    break;
            }
        }
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
                //用启动采用binder
//                bindService(new Intent(this,BindService.class),mConnection, Context.BIND_AUTO_CREATE);
                //用启动采用Messenger
                //绑定服务
                bindService(new Intent(this, MessengerService.class), mMessengerConnection, Context.BIND_AUTO_CREATE);


                break;

            case R.id.btn_stop_bind:
//                unbindService(mConnection);
                unbindService(mMessengerConnection);
                break;
        }
    }

    //使用Binder
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

    //使用Messenger
    private ServiceConnection mMessengerConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {

            Log.e("szx", "ServiceConnection-->" + System.currentTimeMillis());

            //1.通过服务端返回的Binder创建Messenger
            mService = new Messenger(service);
            //2.构建message
            Message message = Message.obtain(null,MESSAGE_FROM_CLIENT);
            Bundle bundle = new Bundle();
            bundle.putString("data","hello world");
            message.setData(bundle);

            //如果需要回传，则需要这个，replyTo字段用于传输Messager对象，以便进程间相互通信
            message.replyTo = mClientMessenger;


            //3.向服务端发送数据
            try {
                mService.send(message);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.e("szx", "onServiceDisconnected-->binder died");
        }
    };


















}
