package com.example.android.learnmore;

import android.app.Activity;
import android.media.Image;
import android.util.Base64;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

public class ImageSaver implements Runnable {
    private final Image mImage;
    private File mFile;
    private ImageUploader imageUploader;

    public static ImageSaver get(Image image, Activity activity) {
        File file = new File(activity.getExternalFilesDir(null), "pic.jpg");
        HttpClient httpClient = new HttpClient();
        ContentSummarizer contentSummarizer = new ContentSummarizer(httpClient, activity);
        ReverseImageSearcher reverseImageSearcher = new ReverseImageSearcher(httpClient, contentSummarizer);
        ToastDisplayer toastDisplayer = new ToastDisplayer(activity);
        ImageUploader imageUploader = new ImageUploader(
                httpClient,
                reverseImageSearcher,
                toastDisplayer);

        return new ImageSaver(image, file, imageUploader);
    }

    private ImageSaver(Image image,
                       File file,
                       ImageUploader imageUploader) {
        this.mImage = image;
        this.mFile = file;
        this.imageUploader = imageUploader;
    }

    @Override
    public void run() {
        ByteBuffer buffer = mImage.getPlanes()[0].getBuffer();
        byte[] bytes = new byte[buffer.remaining()];
        buffer.get(bytes);
        save(bytes);
        String base64EncodedBytes = Base64.encodeToString(bytes, Base64.DEFAULT);
        imageUploader.upload(base64EncodedBytes);
    }

    private void save(byte[] bytes) {
        FileOutputStream output = null;
        try {
            output = new FileOutputStream(mFile);
            output.write(bytes);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            mImage.close();
            if (null != output) {
                try {
                    output.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
