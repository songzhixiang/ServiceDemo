package com.example.pluginapp;

import android.os.Bundle;

import com.example.lib.plugin.PluginActivity;


public class PluginMainActivity extends PluginActivity {

    @Override
    public void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_main_plugin);
    }


}
