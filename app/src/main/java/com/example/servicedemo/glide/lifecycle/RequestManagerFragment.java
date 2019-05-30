package com.example.servicedemo.glide.lifecycle;


import android.content.Context;

import androidx.fragment.app.Fragment;


/**
 * @author andysong
 * @data 2019-05-29
 * @discription xxx
 */
public class RequestManagerFragment extends Fragment {

    private int activityCode;


    LifecycleObservable mLifecycleObservable = LifecycleObservable.getInstance();


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activityCode = context.hashCode();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mLifecycleObservable.onDestory(activityCode);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }
}
