package com.example.structuremonitoringsystem;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.structuremonitoringsystem.LocalDatabase.DatabaseLogging;
import com.example.structuremonitoringsystem.MPAndroidLineChart.XYZGraphs;
import com.example.structuremonitoringsystem.OtherFunction.NavigationBar;
import com.google.android.material.navigation.NavigationView;

public class Manual extends AppCompatActivity {


    ImageView tutorial1,tutorial2,tutorial3,tutorial4,tutorial5,tutorial6,
            nextBtn;
    TextView section;
    private int counter = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_manual);

        tutorial1 = findViewById(R.id.tutorial1);
        tutorial2 = findViewById(R.id.tutorial2);
        tutorial3 = findViewById(R.id.tutorial3);
        tutorial4 = findViewById(R.id.tutorial4);
        tutorial5 = findViewById(R.id.tutorial5);
        tutorial6 = findViewById(R.id.tutorial6);

        section = findViewById(R.id.section);
        nextBtn = findViewById(R.id.nextBtn);


        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ++counter;
                if (counter == 2) {
                    animateFadeOutAndSlideUp(tutorial1, tutorial2);
                } else if (counter == 3) {
                    animateFadeOutAndSlideUp(tutorial2, tutorial3);
                } else if (counter == 4) {
                    animateFadeOutAndSlideUp(tutorial3, tutorial4);
                } else if (counter == 5) {
                    animateFadeOutAndSlideUp(tutorial4, tutorial5);
                } else if (counter == 6) {
                    animateFadeOutAndSlideUp(tutorial5, tutorial6);
                    section.setText("Bluetooth Setup");
                } else {
                    Intent intent = new Intent(Manual.this, RealtimeMonitoring.class);
                    startActivity(intent);
                }
            }
        });

    }


    private void animateFadeOutAndSlideUp(final ImageView fadeOutView, final ImageView slideUpView) {
        ObjectAnimator fadeOutAnimator = ObjectAnimator.ofFloat(fadeOutView, View.ALPHA, 1.0f, 0.0f);
        fadeOutAnimator.setDuration(200);

        final int originalHeight = slideUpView.getHeight();
        TranslateAnimation slideUpAnimation = new TranslateAnimation(0, 0, 0, -originalHeight);
        slideUpAnimation.setDuration(500);
        slideUpAnimation.setFillAfter(true);

        fadeOutAnimator.start();
        slideUpView.startAnimation(slideUpAnimation);

        fadeOutAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                fadeOutView.setVisibility(View.GONE);
                slideUpView.setVisibility(View.VISIBLE);
            }
        });
    }
}