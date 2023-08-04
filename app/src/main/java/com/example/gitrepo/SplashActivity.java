package com.example.gitrepo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class SplashActivity extends AppCompatActivity {

    private static final int SPLASH_SCREEN_DELAY = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        // Starting  the timer
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Go to the main activity
                Intent intent = new Intent(SplashActivity.this, LandingScreen.class);
                startActivity(intent);

                // Finish the splash activity
                finish();
            }
        }, SPLASH_SCREEN_DELAY);
    }
}
