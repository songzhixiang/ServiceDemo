package com.example.servicedemo.retrofit.http;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @author andysong
 * @data 2019-06-05
 * @discription xxx
 */
@Target(PARAMETER)
@Retention(RUNTIME)
public @interface Query {

    String value();

    boolean encoded() default false;
}