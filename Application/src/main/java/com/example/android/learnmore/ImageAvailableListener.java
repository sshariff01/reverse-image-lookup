package com.example.android.learnmore;

import android.app.Activity;
import android.media.ImageReader;
import android.os.Handler;
import android.os.Message;

public class ImageAvailableListener implements ImageReader.OnImageAvailableListener {

    final Handler backgroundHandler;
    private Handler progressDialogHandler;
    final Activity activity;

    public ImageAvailableListener(Handler handler, Handler progressDialogHandler, Activity activity) {
        this.backgroundHandler = handler;
        this.progressDialogHandler = progressDialogHandler;
        this.activity = activity;
    }

    @Override
    public void onImageAvailable(ImageReader imageReader) {
        Message message = progressDialogHandler.obtainMessage();
        message.obj = "display";
        progressDialogHandler.sendMessage(message);
        backgroundHandler.post(ImageSaver.get(imageReader.acquireNextImage(), progressDialogHandler, activity));
    }
}
