package com.example.lib.plugin;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

/**
 * @author andysong
 * @data 2019-05-30
 * @discription xxx
 */
public interface IPlugin {

    int FROM_INTERNAL = 0;
    int FROM_EXTERNAL = 1;

    void attach(Activity activity);

    void onCreate(Bundle saveInstanceState);


    void onStart();


    void onResume();


    void onRestart();


    void onActivityResult(int requestCode, int resultCode, Intent data);

    void onDestroy();

    void onPause();
}
