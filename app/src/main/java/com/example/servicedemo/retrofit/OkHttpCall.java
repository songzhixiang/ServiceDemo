package com.example.servicedemo.retrofit;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.Response;
import okio.Timeout;

/**
 * @author andysong
 * @data 2019-06-05
 * @discription xxx
 */
class OkHttpCall implements Call {

    private final ServiceMethod mServiceMethod;
    private final Object[] args;
    private Call rawCall;

    public OkHttpCall(ServiceMethod serviceMethod, Object[] args) {
        this.mServiceMethod = serviceMethod;
        this.args = args;
        this.rawCall = serviceMethod.toCall(args);
    }

    @Override
    public Request request() {
        return rawCall.request();
    }

    @Override
    public Response execute() throws IOException {
        return rawCall.execute();
    }

    @Override
    public void enqueue(Callback responseCallback) {
        rawCall.enqueue(responseCallback);
    }

    @Override
    public void cancel() {
        rawCall.cancel();
    }

    @Override
    public boolean isExecuted() {
        return rawCall.isExecuted();
    }

    @Override
    public boolean isCanceled() {
        return rawCall.isCanceled();
    }

    @Override
    public Timeout timeout() {
        return rawCall.timeout();
    }

    @Override
    public Call clone() {
        return new OkHttpCall(mServiceMethod,args);
    }
}
