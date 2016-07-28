package com.example.android.learnmore;

import android.app.Activity;
import android.media.Image;
import android.util.Base64;

import java.nio.ByteBuffer;

public class ImageSaver implements Runnable {
    private final Image mImage;
    private ImageUploader imageUploader;

    public static ImageSaver get(Image image, Activity activity) {
        HttpClient httpClient = new HttpClient();
        TextSynthesizer textSynthesizer = TextSynthesizer.get(activity);
        ContentSummarizer contentSummarizer = new ContentSummarizer(httpClient, textSynthesizer);
        ReverseImageSearcher reverseImageSearcher = new ReverseImageSearcher(httpClient, contentSummarizer);
        ToastDisplayer toastDisplayer = new ToastDisplayer(activity);
        ImageUploader imageUploader = new ImageUploader(
                httpClient,
                reverseImageSearcher,
                toastDisplayer);

        return new ImageSaver(image, imageUploader);
    }

    private ImageSaver(Image image,
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