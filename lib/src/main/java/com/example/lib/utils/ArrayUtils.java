package com.example.lib.utils;

import java.lang.reflect.Array;

/**
 * @author andysong
 * @data 2019-05-28
 * @discription xxx
 */
public class ArrayUtils {

    public static Object combineArray(Object arrayLhs,Object arrayRhs){
        Class<?> localClazz = arrayLhs.getClass().getComponentType();

        int i  = Array.getLength(arrayLhs);

        int j = i + Array.getLength(arrayRhs);

        Object result = Array.newInstance(localClazz,j);

        for (int k = 0; k < j; ++k) {
            if (k<i){
                Array.set(result,k,Array.get(arrayLhs,k));
            }else {
                Array.set(result,k,Array.get(arrayRhs,k-i));
            }
        }
        return result;
    }
}
