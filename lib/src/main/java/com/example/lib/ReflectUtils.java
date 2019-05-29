package com.example.lib;

import java.lang.reflect.Field;

/**
 * @author andysong
 * @data 2019-05-28
 * @discription xxx
 */
public class ReflectUtils {

    private static Object getField(Object obj,Class<?> clazz,String field) throws NoSuchFieldException, IllegalAccessException {
        Field localField = clazz.getDeclaredField(field);
        localField.setAccessible(true);
        return localField.get(obj);
    }


    public static void setField(Object obj,Class<?> clazz,Object value) throws NoSuchFieldException, IllegalAccessException {
        Field localField =clazz.getDeclaredField("dexElements");
        localField.setAccessible(true);
        localField.set(obj,value);
    }

    public static Object getPathList(Object baseDexClassLoader) throws ClassNotFoundException, NoSuchFieldException, IllegalAccessException {
        return getField(baseDexClassLoader,Class.forName("dalvik.system.BaseDexClassLoader"),"pathList");
    }

    public static Object getDexElements(Object paramObject) throws NoSuchFieldException, IllegalAccessException {
        return getField(paramObject,paramObject.getClass(),"dexElements");
    }
}
