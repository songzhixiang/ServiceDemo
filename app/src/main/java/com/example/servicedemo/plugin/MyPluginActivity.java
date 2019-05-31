package com.example.servicedemo.plugin;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import android.os.Environment;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.lib.plugin.PluginManager;
import com.example.lib.plugin.ProxyActivity;
import com.example.servicedemo.R;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author andysong
 * @data 2019-05-30
 * @discription xxx
 */
public class MyPluginActivity extends AppCompatActivity {

    @BindView(R.id.btn_load_apk)
    Button btnLoadApk;
    @BindView(R.id.btn_go_to)
    Button btnGoTo;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plugin);

        ButterKnife.bind(this);
        PluginManager.getInstance().init(this);
    }

    @OnClick({R.id.btn_load_apk, R.id.btn_go_to})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_load_apk:
                File sourceFile = new File(Environment.getExternalStorageDirectory(),"my.apk");
                String apkPath =sourceFile.getAbsolutePath();
                PluginManager.getInstance().loadApk(apkPath);
                break;
            case R.id.btn_go_to:
                Intent intent = new Intent(this, ProxyActivity.class);
                intent.putExtra("className","com.example.pluginapp.PluginMainActivity");
                startActivity(intent);
                break;
        }
    }

    public static void start(Context context) {
        Intent starter = new Intent(context, MyPluginActivity.class);
        context.startActivity(starter);
    }
}
