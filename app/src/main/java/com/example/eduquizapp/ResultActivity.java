package com.example.eduquizapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class ResultActivity extends AppCompatActivity {

    TextView tvResult, tvBestScore, tvFeedback;
    Button btnTryAgain, btnBackHome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        tvResult = findViewById(R.id.tvResult);
        tvBestScore = findViewById(R.id.tvBestScore);
        tvFeedback = findViewById(R.id.tvFeedback);
        btnTryAgain = findViewById(R.id.btnTryAgain);
        btnBackHome = findViewById(R.id.btnBackHome);

        int score = getIntent().getIntExtra("score", 0);
        int total = getIntent().getIntExtra("total", 10);

        SharedPreferences prefs = getSharedPreferences("EduQuizPrefs", MODE_PRIVATE);
        int bestScore = prefs.getInt("bestScore", 0);

        prefs.edit().putInt("lastScore", score).apply();

        if (score > bestScore) {
            bestScore = score;
            prefs.edit().putInt("bestScore", bestScore).apply();
        }

        tvResult.setText("Your Score: " + score + " / " + total);
        tvBestScore.setText("Best Score: " + bestScore + " / " + total);

        if (score >= 8) {
            tvFeedback.setText("Excellent work! You understand this level very well.");
        } else if (score >= 5) {
            tvFeedback.setText("Good effort! Review the lesson and try again to improve.");
        } else {
            tvFeedback.setText("Keep practicing. Start with the lesson again before retaking the quiz.");
        }

        btnTryAgain.setOnClickListener(v -> {
            startActivity(new Intent(ResultActivity.this, QuizActivity.class));
            finish();
        });

        btnBackHome.setOnClickListener(v -> {
            Intent intent = new Intent(ResultActivity.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        });
    }
}