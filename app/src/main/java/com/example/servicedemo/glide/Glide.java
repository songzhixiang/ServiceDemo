package com.example.servicedemo.glide;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.example.servicedemo.glide.lifecycle.RequestManagerFragment;

/**
 * @author andysong
 * @data 2019-05-27
 * @discription xxx
 */
public class Glide {

    public static BitmapRequest with(AppCompatActivity mContext){
        FragmentManager fragmentManager = mContext.getSupportFragmentManager();
        RequestManagerFragment current = (RequestManagerFragment) fragmentManager.findFragmentByTag("glide");
        if (current == null){
            current =  new RequestManagerFragment();
            fragmentManager.beginTransaction().add(current,"glide").commitAllowingStateLoss();
        }

        return new BitmapRequest(mContext);
    }
}
