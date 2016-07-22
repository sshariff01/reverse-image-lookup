package com.example.android.camera2basic;

import android.app.Activity;
import android.media.Image;

public class ImageSaverFactory {

    public static ImageSaver create(Image image, Activity activity) {
        HttpClient httpClient = new HttpClient();
        TextSynthesizer textSynthesizer = TextSynthesizerFactory.get(activity);
        ContentSummarizer contentSummarizer = new ContentSummarizer(httpClient, textSynthesizer);
        ReverseImageSearcher reverseImageSearcher = new ReverseImageSearcher(httpClient, contentSummarizer);
        ToastDisplayer toastDisplayer = new ToastDisplayer(activity);
        ImageUploader imageUploader = new ImageUploader(
                httpClient,
                reverseImageSearcher,
                toastDisplayer);

        return new ImageSaver(image, imageUploader);
    }

}
