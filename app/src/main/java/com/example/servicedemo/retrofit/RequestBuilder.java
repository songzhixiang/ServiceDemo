package com.example.servicedemo.retrofit;

import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * @author andysong
 * @data 2019-06-05
 * @discription xxx
 */
public  class RequestBuilder {


    private final String httpMethod;
    private final HttpUrl baseUrl;
    private  String relativeUrl;

    private HttpUrl.Builder  urlBuilder;
    private FormBody.Builder formBuilder;
    private final Request.Builder requestBuilder;


    public RequestBuilder(String httpMethod, HttpUrl baseUrl, String relativeUrl, boolean hasBody) {
        this.httpMethod = httpMethod;
        this.baseUrl = baseUrl;
        this.relativeUrl = relativeUrl;
        this.requestBuilder = new Request.Builder();
        //判断是否有请求体
        if (hasBody){
            formBuilder = new FormBody.Builder();
        }




    }

    //注解的值，参数的值
    public void addQueryParam(String name, String value) {
        if (relativeUrl != null) {
            // 将http://www.baidu.com拼装
            urlBuilder = baseUrl.newBuilder(relativeUrl);
            if (urlBuilder == null) {
                throw new IllegalArgumentException(
                        "Malformed URL. Base: " + baseUrl + ", Relative: " + relativeUrl);
            }
            //重置，因为每次请求都new了一个RequestBuilder
            relativeUrl = null;
        }


            //noinspection ConstantConditions Checked to be non-null by above 'if' block.
            urlBuilder.addQueryParameter(name, value);
    }

    public void addFormField(String name, String value) {

            formBuilder.add(name, value);

    }

    public Request build() {
        HttpUrl url;
        if (urlBuilder != null) {
            url = urlBuilder.build();
        } else {
            url = baseUrl.resolve(relativeUrl);
            if (url == null) {
                throw new IllegalArgumentException(
                        "Malformed URL. Base: " + baseUrl + ", Relative: " + relativeUrl);
            }
        }

        //如果有请求体，构造方法中会初始化Form表单构建者，然后  再实例化请求体
        RequestBody body = null;
        if (formBuilder != null) {
            body = formBuilder.build();
        }


        //构建完整请求体
        return requestBuilder
                .url(url)
                .method(httpMethod, body)
                .build();
    }
}
