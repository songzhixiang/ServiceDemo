package com.example.servicedemo.retrofit;

import java.io.IOException;

/**
 * @author andysong
 * @data 2019-06-05
 * @discription 保存参数的注解值，参数值。最后用于完整的请求拼装
 */
public abstract class ParameterHandler {

    abstract void apply(RequestBuilder builder, String value);

    //抽象的方法，自我实现，由外面赋值和调用
    static final class Query extends ParameterHandler {
        private final String name;

        //传过来的是注解的值
        Query(String name) {
            if (name.isEmpty())
            {
                throw  new IllegalArgumentException("name = null");
            }
            this.name = name;
        }

        @Override
        void apply(RequestBuilder builder, String value) {
            //拼接Query参数，name：注解的值  @Query("ip")  value : 参数值
            builder.addQueryParam(name, value);
        }
    }


    static final class Field extends ParameterHandler {
        private final String name;


        Field(String name) {
            if (name.isEmpty())
            {
                throw  new IllegalArgumentException("name = null");
            }
            this.name = name;
        }

        @Override
        void apply(RequestBuilder builder, String value) {
            //拼接Query参数，name：注解的值  @Query("ip")  value : 参数值
            builder.addFormField(name, value);
        }
    }
}
