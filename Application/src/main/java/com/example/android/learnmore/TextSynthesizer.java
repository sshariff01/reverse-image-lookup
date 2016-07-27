package com.example.android.learnmore;

import android.app.Activity;
import android.speech.tts.TextToSpeech;

import java.util.Locale;

public class TextSynthesizer {
    private final TextToSpeech textToSpeech;
    private final Activity activity;

    public TextSynthesizer(Activity activity) {
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

    public Activity getActivity() {
        return this.activity;
    }

    public void synthesize(CharSequence text) {
        textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null, "utteranceId");
    }

    public void shutdown() {
        textToSpeech.shutdown();
    }
}
