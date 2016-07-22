package com.example.android.camera2basic;

import android.app.Activity;

public class TextSynthesizerFactory {
    private static TextSynthesizer textSynthesizer;

    public static TextSynthesizer get(Activity activity) {
        if (textSynthesizer == null || requireNewTextSynthesizer(activity)) {
            textSynthesizer = new TextSynthesizer(activity);
        }
        return textSynthesizer;
    }

    public static void destroyInstance() {
        if (textSynthesizer != null) {
            textSynthesizer = null;
        }
    }

    private static boolean requireNewTextSynthesizer(Activity activity) {
        return !activity.equals(textSynthesizer.getActivity());
    }
}
