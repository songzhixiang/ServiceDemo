package com.example.pluginlib;

import android.content.pm.PackageInfo;
import android.content.res.AssetManager;
import android.content.res.Resources;

import dalvik.system.DexClassLoader;

/**
 * @author andysong
 * @data 2019-05-30
 * @discription xxx
 */
public class PluginApk {

    //插件APk的实例对象
    public PackageInfo mPackageInfo;
    public Resources mResources;

    public DexClassLoader mClassLoader;

    public PluginApk(PackageInfo packageInfo, Resources resources,  DexClassLoader classLoader) {
        mPackageInfo = packageInfo;
        mResources = resources;

        mClassLoader = classLoader;
    }
}
