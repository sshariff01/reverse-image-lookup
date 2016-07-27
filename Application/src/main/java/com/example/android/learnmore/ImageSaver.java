package com.example.android.learnmore;

import android.media.Image;
import android.util.Base64;

import java.nio.ByteBuffer;

public class ImageSaver implements Runnable {
    private final Image mImage;
    private ImageUploader imageUploader;

    public ImageSaver(Image image,
                      ImageUploader imageUploader) {
        this.mImage = image;
        this.imageUploader = imageUploader;
    }

    @Override
    public void run() {
        ByteBuffer buffer = mImage.getPlanes()[0].getBuffer();
        byte[] bytes = new byte[buffer.remaining()];
        buffer.get(bytes);
        String base64EncodedBytes = Base64.encodeToString(bytes, Base64.DEFAULT);
        imageUploader.upload(base64EncodedBytes);
    }
}