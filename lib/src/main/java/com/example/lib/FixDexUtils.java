package com.example.lib;

import android.content.Context;


import com.example.lib.utils.ArrayUtils;
import com.example.lib.utils.Constants;

import java.io.File;
import java.util.HashSet;

import dalvik.system.DexClassLoader;
import dalvik.system.PathClassLoader;

/**
 * @author andysong
 * @data 2019-05-28
 * @discription xxx
 */
public class FixDexUtils {


    //存放需要修复的dex集合
    private static HashSet<File> loadedDex = new HashSet<>();

    static {
        //修复之前，做清理工作
        loadedDex.clear();
    }


    public static void loadFixedDex(Context mContext) {
        if (mContext == null) {
            return;
        }
        //dex文件目录
        File dir = mContext.getDir(Constants.DEX_DIR, Context.MODE_PRIVATE);
        //遍历私有目录中所有的文件
        File[] files = dir.listFiles();
        for (File file :
                files) {
            if (file.getName().endsWith(Constants.DEX_SUFFIX) && !"classes.dex".equals(file.getName())) {
                //找到修复dex文件，加到集合里面
                loadedDex.add(file);
            }
        }
        //模拟类加载器
        createDexClassLoader(mContext, dir);

    }

    /**
     * 类加载加载的是类，不是Dex，所以需要解压dex
     *
     * @param mContext
     * @param dir
     */
    private static void createDexClassLoader(Context mContext, File dir) {
        //临时的解压目录
        String optDir = dir.getAbsoluteFile() + File.separator + "opt_dex";
        //创建该目录
        File fopt = new File(optDir);
        if (!fopt.exists()) {
            fopt.mkdirs();
        }
        for (File dex :
                loadedDex) {
            //初始化DexClassLoader
            DexClassLoader classLoader = new DexClassLoader(dex.getAbsolutePath(), optDir, null, mContext.getClassLoader());
            //每次遍历一个需要修复的dex文件，就需要插队一次
            hotFix(classLoader, mContext);
        }
    }

    private static void hotFix(DexClassLoader classLoader, Context mContext) {
        //获取系统的classloader
        PathClassLoader pathClassLoader = (PathClassLoader) mContext.getClassLoader();

        try {

            //获取自有的 dexElements数组
            Object myDexElements = ReflectUtils.getDexElements(ReflectUtils.getPathList(classLoader));
            //获取系统的dexElements数组
            Object sysDexElements = ReflectUtils.getDexElements(ReflectUtils.getPathList(pathClassLoader));

            //合并成新的dexElemnts数组，并且设置优先级
            Object dexElements = ArrayUtils.combineArray(myDexElements,sysDexElements);

            //获取系统的PathList对象
            Object sysPathList = ReflectUtils.getPathList(pathClassLoader);
            //重新赋值系统的pathList属性值 --修改的dexElements数组（新合成的）

            ReflectUtils.setField(sysPathList,sysPathList.getClass(),dexElements);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
