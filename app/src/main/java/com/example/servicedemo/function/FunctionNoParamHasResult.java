package com.example.servicedemo.function;

/**
 * @author andysong
 * @data 2019-06-05
 * @discription xxx
 */
public abstract class FunctionNoParamHasResult<T> extends Function {
    public FunctionNoParamHasResult(String functionName) {
        super(functionName);
    }

    public abstract T function();
}
