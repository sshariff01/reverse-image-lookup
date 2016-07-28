package com.example.android.learnmore;

import android.app.Activity;
import android.speech.tts.TextToSpeech;
import android.speech.tts.UtteranceProgressListener;

import java.util.Locale;

public class TextSynthesizer {
    private static TextSynthesizer textSynthesizer;
    private final TextToSpeech textToSpeech;
    private final TextToSpeechListener listener;
    private final Activity activity;

    public interface TextToSpeechListener {
        void highlightWord(int wordStartIndex, int wordEndIndex);
    }

    public static TextSynthesizer get(Activity activity, CharSequence textToSpeak) {
        if (textSynthesizer == null || requireNewTextSynthesizer(activity)) {
            textSynthesizer = new TextSynthesizer(activity, textToSpeak);
        }
        return textSynthesizer;
    }

    private TextSynthesizer(Activity activity, final CharSequence textToSpeak) {
        this.activity = activity;
        this.listener = (TextToSpeechListener) getActivity();
        this.textToSpeech = new TextToSpeech(activity, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status != TextToSpeech.ERROR) {
                    textToSpeech.setPitch(1.1f);
                    textToSpeech.setSpeechRate(1.1f);
                    textToSpeech.setLanguage(Locale.US);
                    synthesize(textToSpeak);
                }
            }
        });
        this.textToSpeech.setOnUtteranceProgressListener(new UtteranceProgressListener() {
            @Override
            public void onStart(String utteranceId) {
                String[] characterIndeces = utteranceId.split(":");
                int wordStartIndex = Integer.parseInt(characterIndeces[0]);
                int wordEndIndex = Integer.parseInt(characterIndeces[1]);
                listener.highlightWord(wordStartIndex, wordEndIndex);
            }

            @Override
            public void onDone(String utteranceId) {

            }

            @Override
            public void onError(String utteranceId) {

            }
        });
    }

    private void synthesize(CharSequence text) {
        String[] words = text.toString().split(" ");
        int wordStartIndex;
        int wordEndIndex = -1;
        for (String word : words) {
            wordStartIndex = wordEndIndex + 1;
            wordEndIndex = wordStartIndex + word.length();
            speakWord(word, wordStartIndex, wordEndIndex);
        }
    }

    private void speakWord(CharSequence word, int wordStartIndex, int wordEndIndex) {
        textToSpeech.speak(word, TextToSpeech.QUEUE_ADD, null, wordStartIndex + ":" + wordEndIndex);
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
