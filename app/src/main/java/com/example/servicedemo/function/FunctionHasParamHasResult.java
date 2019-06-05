package com.example.servicedemo.function;

/**
 * @author andysong
 * @data 2019-06-05
 * @discription xxx
 */
public abstract class FunctionHasParamHasResult<T,P> extends Function {
    public FunctionHasParamHasResult(String functionName) {
        super(functionName);
    }

    abstract T function(P params);




}
