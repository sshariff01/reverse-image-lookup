package com.example.android.learnmore;

import android.app.Activity;
import android.media.ImageReader;
import android.os.Handler;

public class OnImageAvailableListenerFactory {

    public static ImageReader.OnImageAvailableListener create(
            final Handler backgroundHandler,
            final Activity activity) {
        return new ImageReader.OnImageAvailableListener() {
            @Override
            public void onImageAvailable(ImageReader reader) {
                backgroundHandler.post(ImageSaverFactory.create(reader.acquireNextImage(), activity));
            }
        };
    }

}
