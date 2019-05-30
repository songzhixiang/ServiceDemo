package com.example.servicedemo.messenger;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;

import android.util.Log;

import androidx.annotation.Nullable;

/**
 * @author andysong
 * @data 2019/4/11
 * @discription Messager以串行的方式处理客户端发来的消息，不适合有大量并发的请求，只能传递消息，不能跨进程调用方法
 */
public class MessengerService extends Service {

    public static final int MESSAGE_FROM_CLIENT = 11;
    public static final int MESSAGE_FROM_SERVICE = 22;

    //2.构建Messenger对象
    private final Messenger messenger = new Messenger(new MessengerHandler());


    //3.将Messenger对象的Binder返回给客户端
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return messenger.getBinder();
    }

    //1.建立Handler，用于处理来自客户端的消息
    public static class MessengerHandler extends Handler{
        @Override
        public void handleMessage(Message message) {

            switch (message.what){
                case MESSAGE_FROM_CLIENT:

                    Log.e("szx",message.getData().getString("data"));
                    //获取客户端传递过来的Messenger，通过这个Messenger回传消息给客户端
                    Messenger client = message.replyTo;
                    //当然，回传消息还是要通过message
                    Message msg = Message.obtain(null, MESSAGE_FROM_SERVICE);
                    Bundle bundle = new Bundle();
                    bundle.putString("msg", "hello client, I have received your message!");
                    msg.setData(bundle);
                    try {
                        client.send(msg);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }



                    break;
                default:
                    super.handleMessage(message);
                    break;
            }
        }


    }
}
