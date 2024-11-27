package com.example.textstream;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class LoaderActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loader); // Set your loader layout

        // Initialize views
        ImageView logo = findViewById(R.id.logo);
        TextView appName = findViewById(R.id.app_name);

        // Load animations
        Animation rotateAnimation = AnimationUtils.loadAnimation(this, R.anim.rotate_animation);
        Animation fadeInAnimation = AnimationUtils.loadAnimation(this, R.anim.rotate_animation);

        // Apply animations
        logo.startAnimation(rotateAnimation);
        appName.startAnimation(fadeInAnimation);

        // Delay for 3 seconds and redirect to LoginActivity
        new Handler().postDelayed(() -> {
            Intent intent = new Intent(LoaderActivity.this, SignUpActivity.class);
            startActivity(intent);
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            finish();
        }, 3000); // 3000 milliseconds = 3 seconds
    }
}