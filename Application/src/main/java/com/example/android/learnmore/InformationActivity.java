package com.example.android.learnmore;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.text.Spannable;
import android.text.style.ForegroundColorSpan;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;

public class InformationActivity extends Activity implements TextSynthesizer.TextToSpeechListener {
    private TextView textView;
    private ImageView imageView;
    private TextSynthesizer textSynthesizer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);
        textView = (TextView) findViewById(R.id.text_information);
        imageView = (ImageView) findViewById(R.id.captured_image);
        updateTextView(getIntent());
        updateImageView();
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

    private void updateImageView() {
        File imageFile = new File(this.getExternalFilesDir(null), "capturedImage.jpg");
        if (imageFile.exists()){
            imageView.setImageURI(Uri.fromFile(imageFile));
        }
    }

    private void synthesizeText() {
        textSynthesizer = TextSynthesizer.get(this, textView.getText().toString());
    }

    public void highlightWord(int wordStartIndex, int wordEndIndex) {
        String text = textView.getText().toString();
        final Spannable spanText = Spannable.Factory.getInstance().newSpannable(text);
        spanText.setSpan(new ForegroundColorSpan(Color.RED), wordStartIndex, wordEndIndex, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        InformationActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                textView.setText(spanText);
            }
        });
    }
}
