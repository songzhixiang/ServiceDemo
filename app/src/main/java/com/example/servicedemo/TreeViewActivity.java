package com.example.servicedemo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


/**
 * @author andysong
 * @data 2019-05-28
 * @discription xxx
 */
public class TreeViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_treeview);
    }

    public static void start(Context context) {
        Intent starter = new Intent(context, TreeViewActivity.class);

        context.startActivity(starter);
    }

}
