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
public @interface Field {
    String value();

    /** Specifies whether the {@linkplain #value() name} and value are already URL encoded. */
    boolean encoded() default false;
}
