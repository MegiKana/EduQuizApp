package com.example.eduquizapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    TextView tvWelcome, tvLevel, tvBestScore, tvMotivation;
    Button btnLessons, btnQuiz, btnSettings;
    Button navHome, navLessons, navQuiz, navProgress, navLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvWelcome = findViewById(R.id.tvWelcome);
        tvLevel = findViewById(R.id.tvLevel);
        tvBestScore = findViewById(R.id.tvBestScore);
        tvMotivation = findViewById(R.id.tvMotivation);

        btnLessons = findViewById(R.id.btnLessons);
        btnQuiz = findViewById(R.id.btnQuiz);
        btnSettings = findViewById(R.id.btnSettings);

        navHome = findViewById(R.id.navHome);
        navLessons = findViewById(R.id.navLessons);
        navQuiz = findViewById(R.id.navQuiz);
        navProgress = findViewById(R.id.navProgress);
        navLogout = findViewById(R.id.navLogout);

        SharedPreferences prefs = getSharedPreferences("EduQuizPrefs", MODE_PRIVATE);
        String name = prefs.getString("memberName", "Student");
        String level = prefs.getString("level", "Beginner");
        int bestScore = prefs.getInt("bestScore", 0);

        tvWelcome.setText("Welcome, " + name + "!");
        tvLevel.setText("Your learning level: " + level);
        tvBestScore.setText("Best score: " + bestScore + " / 10");
        tvMotivation.setText("Learn, practice, and improve your English step by step.");

        btnLessons.setOnClickListener(v -> startActivity(new Intent(this, LessonActivity.class)));
        btnQuiz.setOnClickListener(v -> startActivity(new Intent(this, QuizActivity.class)));
        btnSettings.setOnClickListener(v -> startActivity(new Intent(this, SettingsActivity.class)));

        navHome.setOnClickListener(v -> {});
        navLessons.setOnClickListener(v -> startActivity(new Intent(this, LessonActivity.class)));
        navQuiz.setOnClickListener(v -> startActivity(new Intent(this, QuizActivity.class)));
        navProgress.setOnClickListener(v -> startActivity(new Intent(this, SettingsActivity.class)));

        navLogout.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        SharedPreferences prefs = getSharedPreferences("EduQuizPrefs", MODE_PRIVATE);
        int bestScore = prefs.getInt("bestScore", 0);
        tvBestScore.setText("Best score: " + bestScore + " / 10");
    }
}