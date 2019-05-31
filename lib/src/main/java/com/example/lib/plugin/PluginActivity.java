package com.example.lib.plugin;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;




/**
 * @author andysong
 * @data 2019-05-30
 * @discription xxx
 */
public class PluginActivity extends Activity implements IPlugin {

    private Activity mProxyActivity;
    private int mFrom = FROM_INTERNAL;//内部

    @Override
    public void attach(Activity activity) {
        mProxyActivity = activity;
    }

    @Override
    public void onCreate(Bundle saveInstanceState) {
        if (saveInstanceState!=null){
            mFrom = saveInstanceState.getInt("FROM");
        }
        if (mFrom == FROM_INTERNAL){
            super.onCreate(saveInstanceState);
            mProxyActivity = this;

        }
    }

    @Override
    public void setContentView(int layoutResID) {
        if (mFrom == FROM_INTERNAL){
            super.setContentView(layoutResID);
        }else {
            mProxyActivity.setContentView(layoutResID);
        }

    }

    @Override
    public void onStart() {
        if (mFrom == FROM_INTERNAL){
            super.onStart();
        }
    }

    @Override
    public void onResume() {
        if (mFrom == FROM_INTERNAL){
            super.onResume();
        }
    }

    @Override
    public void onRestart() {
        if (mFrom == FROM_INTERNAL){
            super.onRestart();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (mFrom == FROM_INTERNAL){
            super.onActivityResult(requestCode,resultCode,data);
        }
    }

    @Override
    public void onDestroy() {
        if (mFrom == FROM_INTERNAL){
            super.onDestroy();
        }
    }

    @Override
    public void onPause() {
        if (mFrom == FROM_INTERNAL){
            super.onPause();
        }
    }
}
