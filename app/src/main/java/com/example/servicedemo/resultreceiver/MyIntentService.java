package com.example.servicedemo.resultreceiver;

import android.app.IntentService;
import android.content.Intent;
import android.os.ResultReceiver;
import android.util.Log;

/**
 * @author andysong
 * @data 2019/4/19
 * @discription xxx
 */
public class MyIntentService extends IntentService {


    public MyIntentService() {
        super("default");
    }

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public MyIntentService(String name) {
        super(name);
    }



    @Override
    protected void onHandleIntent( Intent intent) {
        Log.e("szx","onHandleIntent");
        ResultReceiver resultReceiver = (ResultReceiver) intent.getExtras().get("result_receiver");
        if (resultReceiver!=null){
            resultReceiver.send(19,null);
        }
    }
}
