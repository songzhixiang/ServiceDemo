package com.example.servicedemo.function;

/**
 * @author andysong
 * @data 2019-06-05
 * @discription xxx
 */
public abstract class FunctionHasParamNoResult<T> extends Function {

    public FunctionHasParamNoResult(String functionName) {
        super(functionName);
    }

    abstract void function(T param);
}
