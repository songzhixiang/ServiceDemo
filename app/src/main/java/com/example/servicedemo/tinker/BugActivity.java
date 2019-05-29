package com.example.servicedemo.tinker;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.blankj.utilcode.util.FileUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.example.lib.FixDexUtils;
import com.example.lib.utils.Constants;
import com.example.servicedemo.R;

import java.io.File;

/**
 * @author andysong
 * @data 2019-05-28
 * @discription xxx
 */
public class BugActivity extends AppCompatActivity implements View.OnClickListener {

    Button mFix;
    Button mBug;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bug);
        mFix = findViewById(R.id.btn_fix);
        mBug = findViewById(R.id.btn_bug);
        mFix.setOnClickListener(this);
        mBug.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_fix:
                //通过接口下载修复好的dex文件，将文件放置到sd卡(暂定classes2.dex)
                File sourceFile = new File(Environment.getExternalStorageDirectory(), Constants.DEX_NAME);
                //目标文件,私有目录
                File targetFile  = new File(getDir(Constants.DEX_DIR,Context.MODE_PRIVATE).getAbsoluteFile()
                        +File.separator+Constants.DEX_NAME);
                //如果存在，清理之前修复过程
                if (targetFile.exists()){
                    targetFile.delete();
                    ToastUtils.showShort("删除了");
                }
                //复制过程
                boolean b = FileUtils.copyFile(sourceFile, targetFile);
                ToastUtils.showShort("复制dex完成"+b);


                //开始修复dex
                FixDexUtils.loadFixedDex(this);
                break;

            case R.id.btn_bug:
                ParamsSort paramsSort = new ParamsSort();
                paramsSort.math(this);
                break;
        }
    }


    public static void start(Context context) {
        Intent starter = new Intent(context, BugActivity.class);

        context.startActivity(starter);
    }
}
