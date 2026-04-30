package com.example.eduquizapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class SettingsActivity extends AppCompatActivity {

    TextView tvProfileInfo, tvProgressInfo, tvAdvice, tvSettingsMessage;
    Button btnResetProgress, btnOpenResource, btnLogout, btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        tvProfileInfo = findViewById(R.id.tvProfileInfo);
        tvProgressInfo = findViewById(R.id.tvProgressInfo);
        tvAdvice = findViewById(R.id.tvAdvice);
        tvSettingsMessage = findViewById(R.id.tvSettingsMessage);

        btnResetProgress = findViewById(R.id.btnResetProgress);
        btnOpenResource = findViewById(R.id.btnOpenResource);
        btnLogout = findViewById(R.id.btnLogout);
        btnBack = findViewById(R.id.btnBack);

        loadInfo();

        btnResetProgress.setOnClickListener(v -> {
            SharedPreferences prefs = getSharedPreferences("EduQuizPrefs", MODE_PRIVATE);
            prefs.edit()
                    .putInt("lastScore", 0)
                    .putInt("bestScore", 0)
                    .apply();

            tvSettingsMessage.setText("Progress reset successfully.");
            loadInfo();
        });

        btnOpenResource.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("https://www.englishclub.com/vocabulary/"));
            startActivity(intent);
        });

        btnLogout.setOnClickListener(v -> {
            Intent intent = new Intent(SettingsActivity.this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        });

        btnBack.setOnClickListener(v -> finish());
    }

    private void loadInfo() {
        SharedPreferences prefs = getSharedPreferences("EduQuizPrefs", MODE_PRIVATE);

        String name = prefs.getString("memberName", "Student");
        int age = prefs.getInt("age", 0);
        String level = prefs.getString("level", "Beginner");
        int lastScore = prefs.getInt("lastScore", 0);
        int bestScore = prefs.getInt("bestScore", 0);

        tvProfileInfo.setText("Member Name: " + name + "\nAge: " + age + "\nLevel: " + level);
        tvProgressInfo.setText("Last Score: " + lastScore + " / 10\nBest Score: " + bestScore + " / 10");

        if (bestScore >= 8) {
            tvAdvice.setText("Advice: Great progress! Continue practicing harder vocabulary.");
        } else if (bestScore >= 5) {
            tvAdvice.setText("Advice: You are improving. Review the lessons and retake the quiz.");
        } else {
            tvAdvice.setText("Advice: Focus on the lesson examples first, then try the quiz again.");
        }
    }
}