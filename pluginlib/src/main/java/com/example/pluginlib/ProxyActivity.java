package com.example.pluginlib;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * @author andysong
 * @data 2019-05-30
 * @discription 处理跳转逻辑
 */
public class ProxyActivity extends AppCompatActivity {

    private String mClassName;
    private PluginApk mPluginApk;
    private IPlugin mIPlugin;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mClassName = getIntent().getStringExtra("className");
        mPluginApk = PluginManager.getInstance().getPluginApk();

        launchPluginActivity();

    }

    private void launchPluginActivity() {
        if (mPluginApk == null){
            throw new RuntimeException("请先加载插件apk");
        }
        try {
            Class<?> clazz = mPluginApk.mClassLoader.loadClass(mClassName);
            //这里就是Activtiy的实例对象,这里的class没有生命周期，没有context
            Object o = clazz.newInstance();
            if (o instanceof IPlugin){
                mIPlugin = (IPlugin) o;
                mIPlugin.attach(this);
                Bundle bundle = new Bundle();
                bundle.putInt("FROM",IPlugin.FROM_EXTERNL);
                mIPlugin.onCreate(bundle);
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
    }
}
