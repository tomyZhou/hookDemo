package com.example.hookdemo;

import android.util.Log;
import android.view.View;

public class OnClickListenerProxy implements View.OnClickListener {

    private View.OnClickListener mOriginListener;

    public OnClickListenerProxy(View.OnClickListener listener) {
        this.mOriginListener = listener;
    }

    @Override
    public void onClick(View v) {
        if (mOriginListener != null) {
            mOriginListener.onClick(v);
        }

        String name = (String) v.getTag(v.getId());
        Log.d("LOGCAT", name + "被hooked!");
    }
}
