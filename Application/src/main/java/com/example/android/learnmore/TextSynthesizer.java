package com.example.android.learnmore;

import android.app.Activity;
import android.speech.tts.TextToSpeech;

import java.util.Locale;

public class TextSynthesizer {
    private static TextSynthesizer textSynthesizer;
    private final TextToSpeech textToSpeech;
    private final Activity activity;

    public static TextSynthesizer get(Activity activity, CharSequence textToSpeak) {
        if (textSynthesizer == null || requireNewTextSynthesizer(activity)) {
            textSynthesizer = new TextSynthesizer(activity, textToSpeak);
        }
        return textSynthesizer;
    }

    private TextSynthesizer(Activity activity, final CharSequence textToSpeak) {
        this.activity = activity;
        this.textToSpeech = new TextToSpeech(activity, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status != TextToSpeech.ERROR) {
                    textToSpeech.setLanguage(Locale.ENGLISH);
                    synthesize(textToSpeak);
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

    private static boolean requireNewTextSynthesizer(Activity activity) {
        return !activity.equals(textSynthesizer.getActivity());
    }

    public Activity getActivity() {
        return this.activity;
    }
}
