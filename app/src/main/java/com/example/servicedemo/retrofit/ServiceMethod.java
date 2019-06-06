package com.example.servicedemo.retrofit;

import com.example.servicedemo.retrofit.http.Field;
import com.example.servicedemo.retrofit.http.GET;
import com.example.servicedemo.retrofit.http.POST;
import com.example.servicedemo.retrofit.http.Query;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.net.URI;
import java.util.Map;
import java.util.regex.Matcher;

import okhttp3.Call;
import okhttp3.Headers;
import okhttp3.HttpUrl;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * @author andysong
 * @data 2019-06-05
 * @discription xxx
 */
public class ServiceMethod {

    Call.Factory mFactory;

    //请求方式，，get,,post
    String httpMethod;

    //请求体
    boolean hasBody;

    HttpUrl baseUrl;

    //方法参数的数组（每个数组内：参数注解值，参数值）
    ParameterHandler[] parameterHandlers;


    //方法注解的值
    String relativeUrl;

    private ServiceMethod(Builder builder) {
        this.mFactory = builder.retrofit.getFactory();
        this.baseUrl = builder.retrofit.getBaseUrl();
        this.httpMethod = builder.httpMethod;
        this.parameterHandlers = builder.parameterHandlers;
        this.relativeUrl = builder.relativeUrl;
        this.hasBody = builder.hasBody;
    }

    //发起者
    public Call toCall(Object[] args) {
        RequestBuilder requestBuilder = new RequestBuilder(httpMethod, baseUrl, relativeUrl, hasBody);

        ParameterHandler[] handlers = parameterHandlers;

        //通过method找出两个参数，而我们自己添加的数据对象如果不等于2，说明异常
        int argumentCount = args != null ? args.length : 0;
        if (argumentCount != handlers.length) {
            throw new IllegalArgumentException("Argument count (" + argumentCount
                    + ") doesn't match expected count (" + handlers.length + ")");
        }
        //参数赋值，循环拼接每个参数名和参数值
        for (int p = 0; p < argumentCount; p++) {
            handlers[p].apply(requestBuilder, args[p].toString());
        }

        return mFactory.newCall(requestBuilder.build());
    }

    static final class Builder {
        //okhttp封装类
        final Retrofit retrofit;
        //带rest注解的方法
        final Method method;
        //方法的所有注解
        final Annotation[] methodAnnotations;
        //方法参数的所有注解（一个方法有多个参数，一个参数有多个注解）
        final Annotation[][] parameterAnnotationsArray;

        //请求方式，，get,,post
        String httpMethod;

        //请求体
        boolean hasBody;

        //方法注解的值
        String relativeUrl;

        //方法参数的数组（每个数组内：参数注解值，参数值）
        ParameterHandler[] parameterHandlers;


        Builder(Retrofit retrofit, Method method) {
            this.retrofit = retrofit;
            this.method = method;
            this.methodAnnotations = method.getAnnotations();
            this.parameterAnnotationsArray = method.getParameterAnnotations();
        }


        public ServiceMethod build() {

            for (Annotation annotation : methodAnnotations) {
                parseMethodAnnotation(annotation);
            }


            //拿到参数注解长度
            int parameterCount = parameterAnnotationsArray.length;
            //定义方法参数的数组
            parameterHandlers = new ParameterHandler[parameterCount];

            //遍历方法的参数
            for (int p = 0; p < parameterCount; p++) {
                //获取每个参数的所有注解
                Annotation[] parameterAnnotations = parameterAnnotationsArray[p];
                if (parameterAnnotations == null) {
                    throw new IllegalArgumentException("No Retrofit annotation found.");
                }

                //获取参数的注解值，参数值
                parameterHandlers[p] = parseParameter(parameterAnnotations);
            }


            return new ServiceMethod(this);
        }

        //解析参数的所有注解（嵌套循环）
        private ParameterHandler parseParameter(Annotation[] annotations) {
            ParameterHandler result = null;
            //遍历参数的注解（每一个参数的每一个注解）
            for (Annotation annotation : annotations) {
                ParameterHandler annotationAction = parseParameterAnnotation(annotation);
                //为了找不到继续找，增强代码健壮性
                if (annotationAction == null) {
                    continue;
                }


                //赋值
                result = annotationAction;
            }
            //如果参数没有任何注解
            if (result == null) {
                throw new IllegalArgumentException("No Retrofit annotation found.");
            }

            return result;
        }

        //解析参数注解，可能是Query或者Field（自动识别）
        private ParameterHandler parseParameterAnnotation(Annotation annotation) {
            if (annotation instanceof Query) {
                //@Query("ip")
                Query query = (Query) annotation;
                //"ip"
                String name = query.value();
                return new ParameterHandler.Query(name);


            } else if (annotation instanceof Field) {
                //@Field("key")
                Field field = (Field) annotation;
                //"key"
                String name = field.value();

                return new ParameterHandler.Field(name);


            }

            return null; // Not a Retrofit annotation.
        }

        private void parseMethodAnnotation(Annotation annotation) {
            if (annotation instanceof GET) {
                //GET方式没有请求体
                parseHttpMethodAndPath("GET", ((GET) annotation).value(), false);
            } else if (annotation instanceof POST) {
                parseHttpMethodAndPath("POST", ((POST) annotation).value(), true);
            }
        }

        //通过方法的注解获取方法的请求方式，注解值
        private void parseHttpMethodAndPath(String httpMethod, String value, boolean hasBody) {
            this.httpMethod = httpMethod;//请求方式
            this.hasBody = hasBody;//是否有请求体
            this.relativeUrl = value;//方法的注解值，用来拼接的baseUrl+xxxxx  例如 http://www.baidu.com/ip/ipnews

        }

    }


}
