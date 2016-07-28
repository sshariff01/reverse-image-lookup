package com.example.android.learnmore;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.widget.TextView;

public class InformationActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);
        updateTextView(getIntent());
    }

    private void updateTextView(Intent intent) {
        String data = intent.getStringExtra("data");
        TextView textView = (TextView) findViewById(R.id.text_information);
        textView.setText(data);
    }
}
