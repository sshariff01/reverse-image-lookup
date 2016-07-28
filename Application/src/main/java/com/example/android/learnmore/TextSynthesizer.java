package com.example.android.learnmore;

import android.app.Activity;
import android.speech.tts.TextToSpeech;

import java.util.Locale;

public class TextSynthesizer {
    private static TextSynthesizer textSynthesizer;
    private final TextToSpeech textToSpeech;
    private final Activity activity;

    public static TextSynthesizer get(Activity activity) {
        if (textSynthesizer == null || requireNewTextSynthesizer(activity)) {
            textSynthesizer = new TextSynthesizer(activity);
        }
        return textSynthesizer;
    }

    private TextSynthesizer(Activity activity) {
        this.activity = activity;
        this.textToSpeech = new TextToSpeech(activity, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status != TextToSpeech.ERROR) {
                    textToSpeech.setLanguage(Locale.ENGLISH);
                }
            }
        });
    }

    public void synthesize(CharSequence text) {
        textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null, "utteranceId");
    }

    public void shutdown() {
        textToSpeech.shutdown();
    }

    public static void destroyInstance() {
        if (textSynthesizer != null) {
            textSynthesizer = null;
        }
    }

    private static boolean requireNewTextSynthesizer(Activity activity) {
        return !activity.equals(textSynthesizer.getActivity());
    }

    public Activity getActivity() {
        return this.activity;
    }
}
