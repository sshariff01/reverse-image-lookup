package com.example.android.camera2basic;

import android.app.Activity;
import android.widget.Toast;

public class ToastDisplayer {
    private final Activity activity;

    public ToastDisplayer(Activity activity) {
        this.activity = activity;
    }

    public void showToast(final String text) {
        if (activity == null) return;
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(activity, text, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
