package com.example.servicedemo.retrofit;



import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import okhttp3.Call;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;




/**
 * @author andysong
 * @data 2019-06-05
 * @discription xxx
 */
public class Retrofit {

    //缓存请求方法
    //key host.get() value 该方法的属性封装：方法名，方法注解，参数注解，参数
    private final Map<Method,ServiceMethod> mServiceMethodMap = new ConcurrentHashMap<>();


    //OkhttpClient唯一实现
    private Call.Factory mFactory;

    private HttpUrl baseUrl;

    private Retrofit(Builder builder) {
        this.mFactory = builder.mFactory;
        this.baseUrl = builder.baseUrl;
    }


    //对外提供API


    public Call.Factory getFactory() {
        return mFactory;
    }



    public HttpUrl getBaseUrl() {
        return baseUrl;
    }


    @SuppressWarnings("unchecked")
    public <T> T create(final Class<T> service){
        return(T) Proxy.newProxyInstance(service.getClassLoader(), new Class[]{service}, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                ServiceMethod serviceMethod = loadServiceMethod(method);

                return new OkHttpCall(serviceMethod,args);
            }
        });
    }

    private ServiceMethod loadServiceMethod(Method method) {
        //方法的缓存，同一个接口多次请求没必要去创建多次
        ServiceMethod result = mServiceMethodMap.get(method);
        if (result!=null) return result;
        synchronized (mServiceMethodMap){
            result = mServiceMethodMap.get(method);
            if (result == null){
                result = new ServiceMethod.Builder(this,method).build();
                mServiceMethodMap.put(method,result);
            }
        }
        return result;
    }


    public static final class Builder{
        private Call.Factory mFactory;
        private HttpUrl baseUrl;


        public Builder callFactory(Call.Factory mFactory){
            this.mFactory = mFactory;
            return this;
        }

        public Builder baseUrl(String baseUrl){
            if (baseUrl.isEmpty()){
                throw new NullPointerException("baseUrl is Null");
            }

            this.baseUrl = HttpUrl.parse(baseUrl);
            return this;
        }
        //属性的赋值教研和初始化
        public Retrofit build(){
            if (baseUrl == null){
                throw new NullPointerException("baseUrl is Null");
            }

            if (this.mFactory == null){
                this.mFactory  = new OkHttpClient();
            }
            return new Retrofit(this);
        }
    }
}
