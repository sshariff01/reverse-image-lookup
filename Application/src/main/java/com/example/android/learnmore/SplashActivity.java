package com.example.android.learnmore;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.os.Handler;

public class SplashActivity extends Activity {

    private static int SPLASH_TIMER = 2000;
    private final MainActivityLauncher mainActivityLauncher = new MainActivityLauncher();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(mainActivityLauncher, SPLASH_TIMER);
    }

    private class MainActivityLauncher implements Runnable {

        @Override
        public void run() {
            Intent intent = new Intent(SplashActivity.this, CameraActivity.class);
            startActivity(intent);

            finish();
        }
    }
}
