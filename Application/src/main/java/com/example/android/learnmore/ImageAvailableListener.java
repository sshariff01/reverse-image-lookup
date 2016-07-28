package com.example.android.learnmore;

import android.app.Activity;
import android.media.ImageReader;
import android.os.Handler;

public class ImageAvailableListener implements ImageReader.OnImageAvailableListener {

    final Handler backgroundHandler;
    final Activity activity;

    public ImageAvailableListener(Handler handler, Activity activity) {
        this.backgroundHandler = handler;
        this.activity = activity;
    }

    @Override
    public void onImageAvailable(ImageReader imageReader) {
        backgroundHandler.post(ImageSaver.get(imageReader.acquireNextImage(), activity));
    }
}
