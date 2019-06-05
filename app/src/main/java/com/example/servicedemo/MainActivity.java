package com.example.servicedemo;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.os.ResultReceiver;

import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.text.PrecomputedTextCompat;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.PermissionUtils;
import com.example.servicedemo.function.FirstActivity;
import com.example.servicedemo.glide.Glide;

import com.example.servicedemo.glide.RequestListener;
import com.example.servicedemo.job.DemoSyncJob;
import com.example.servicedemo.jobscheduler.DemoJobService;
import com.example.servicedemo.messenger.MessengerService;
import com.example.servicedemo.plugin.MyPluginActivity;
import com.example.servicedemo.resultreceiver.MyIntentService;
import com.example.servicedemo.tinker.BugActivity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.Future;

import static com.example.servicedemo.messenger.MessengerService.MESSAGE_FROM_CLIENT;
import static com.example.servicedemo.messenger.MessengerService.MESSAGE_FROM_SERVICE;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button mStartButton;
    Button mButtonStop;
    Button mStartButtonBind;
    Button mButtonStopBind;
    Button mButtonJob;
    Button mButtonJobStop;
    Button mButtonIntentService;
    Button mButtonAndroidJob;
    Button mButtonStartImage;
    Button mButtonStartTreeView;
    ImageView mImageView;
    MyBinder mBinder;
    AppCompatTextView mAppCompatTextView;
    private Messenger mService;
    private Messenger mClientMessenger = new Messenger(new MessengerHandler());


    private Handler mHandler;
    private ResultReceiver mResultReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mHandler = new Handler();
        mResultReceiver = new ResultReceiver(mHandler){
            @Override
            protected void onReceiveResult(int resultCode, Bundle resultData) {
                Toast.makeText(MainActivity.this, "receive " + resultCode, Toast.LENGTH_LONG).show();

            }
        };

        mStartButton = findViewById(R.id.btn_start);
        mButtonStop = findViewById(R.id.btn_stop);
        mStartButtonBind = findViewById(R.id.btn_start_bind);
        mButtonStopBind = findViewById(R.id.btn_stop_bind);
        mButtonJob = findViewById(R.id.btn_start_jobservice);
        mButtonAndroidJob = findViewById(R.id.btn_android_job);
        mButtonJobStop = findViewById(R.id.btn_stop_jobservice);
        mButtonIntentService = findViewById(R.id.btn_intentservice_resultreceiver);
        mAppCompatTextView = findViewById(R.id.tv_content);
        mButtonStartImage = findViewById(R.id.btn_loadImage);
        mButtonStartTreeView = findViewById(R.id.btn_start_treeview);
        mImageView = findViewById(R.id.iv);
        Future<PrecomputedTextCompat> future = PrecomputedTextCompat
                .getTextFuture("HelloWorld", mAppCompatTextView.getTextMetricsParamsCompat(), null);

        mAppCompatTextView.setTextFuture(future);

        mStartButton.setOnClickListener(this);
        mButtonStop.setOnClickListener(this);
        mStartButtonBind.setOnClickListener(this);
        mButtonStopBind.setOnClickListener(this);
        mButtonJob.setOnClickListener(this);
        mButtonJobStop.setOnClickListener(this);
        mButtonIntentService.setOnClickListener(this);
        mButtonAndroidJob.setOnClickListener(this);
        mButtonStartImage.setOnClickListener(this);
        mButtonStartTreeView.setOnClickListener(this);

        PermissionUtils.permission().request();

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

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
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

            case R.id.btn_start_jobservice:
                scheduleJob();
                break;

            case R.id.btn_stop_jobservice:
                JobScheduler js  = (JobScheduler) getSystemService(Context.JOB_SCHEDULER_SERVICE);
                js.cancelAll();
                break;

            case R.id.btn_intentservice_resultreceiver:
                Intent i = new Intent(MainActivity.this, MyIntentService.class);
                i.putExtra("result_receiver",mResultReceiver);
                startService(i);
                break;
            case R.id.btn_android_job:
                DemoSyncJob.scheduleJob();
                break;

            case R.id.btn_loadImage:
                Glide.with(this).
                        load("https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=3230577747,3091740460&fm=26&gp=0.jpg")
                        .loading(R.mipmap.ic_launcher).listenter(new RequestListener() {
                    @Override
                    public boolean onSuccess(Bitmap bitmap) {
                        LogUtils.e("加载完成");
                        return false;
                    }

                    @Override
                    public boolean onFail() {
                        return false;
                    }
                }).into(mImageView);

                break;
            case R.id.btn_start_treeview:
//                TreeViewActivity.start(this);
//                BugActivity.start(this);
//                MyPluginActivity.start(this);
                FirstActivity.start(this);
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


    ///////////////////////////////////////////////////////////////////////////
    // jobscheduler
    ///////////////////////////////////////////////////////////////////////////
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void scheduleJob(){
        JobInfo.Builder builder = new JobInfo.Builder(0,new ComponentName(this, DemoJobService.class));
        builder.setBackoffCriteria(1000,JobInfo.BACKOFF_POLICY_LINEAR);//重试机制
        builder.setRequiresCharging(true);//是否要求充电的时候执行
        builder.setMinimumLatency(5000);//设置至少多少毫秒后执行
        builder.setOverrideDeadline(30000);//设置最多延迟多少毫秒之后执行
        JobInfo jobInfo = builder.build();
        JobScheduler js  = (JobScheduler) getSystemService(Context.JOB_SCHEDULER_SERVICE);
        if (js!=null){
            js.schedule(jobInfo);
        }

        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
        Date date = new Date(System.currentTimeMillis());
        String timestamp = format.format(date);

        Log.e("szx","begin:"+timestamp);
    }


















}
