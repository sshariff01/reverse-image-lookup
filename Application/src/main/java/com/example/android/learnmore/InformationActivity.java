package com.example.android.learnmore;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.widget.TextView;

public class InformationActivity extends Activity {
    private TextView textView;
    private TextSynthesizer textSynthesizer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);
        textView = (TextView) findViewById(R.id.text_information);
        updateTextView(getIntent());
        synthesizeText();
    }

    @Override
    protected void onPause() {
        super.onPause();
        textSynthesizer.shutdown();
    }

    private void updateTextView(Intent intent) {
        textView.setText(intent.getStringExtra("data"));
    }

    private void synthesizeText() {
        textSynthesizer = TextSynthesizer.get(this, textView.getText().toString());
    }
}
