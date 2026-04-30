package com.example.eduquizapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity {

    EditText etRegisterName, etRegisterAge, etRegisterPassword;
    Button btnCreateAccount, btnGoLogin;
    TextView tvRegisterMessage, tvLoginInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etRegisterName = findViewById(R.id.etRegisterName);
        etRegisterAge = findViewById(R.id.etRegisterAge);
        etRegisterPassword = findViewById(R.id.etRegisterPassword);
        btnCreateAccount = findViewById(R.id.btnCreateAccount);
        btnGoLogin = findViewById(R.id.btnGoLogin);
        tvRegisterMessage = findViewById(R.id.tvRegisterMessage);
        tvLoginInfo = findViewById(R.id.tvLoginInfo);

        btnCreateAccount.setOnClickListener(v -> createAccount());

        btnGoLogin.setOnClickListener(v -> {
            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        });
    }

    private void createAccount() {
        String name = etRegisterName.getText().toString().trim();
        String ageText = etRegisterAge.getText().toString().trim();
        String password = etRegisterPassword.getText().toString().trim();

        if (name.isEmpty() || ageText.isEmpty() || password.isEmpty()) {
            tvRegisterMessage.setText("Please fill all fields.");
            return;
        }

        if (password.length() < 6) {
            tvRegisterMessage.setText("Password must be at least 6 characters.");
            return;
        }

        int age;

        try {
            age = Integer.parseInt(ageText);
        } catch (NumberFormatException e) {
            tvRegisterMessage.setText("Age must be a number.");
            return;
        }

        if (age < 7) {
            tvRegisterMessage.setText("Age must be 7 or older.");
            return;
        }

        String level;

        if (age <= 10) {
            level = "Beginner";
        } else if (age <= 14) {
            level = "Intermediate";
        } else {
            level = "Advanced";
        }

        SharedPreferences prefs = getSharedPreferences("EduQuizPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        editor.putString("memberName", name);
        editor.putInt("age", age);
        editor.putString("password", password);
        editor.putString("level", level);
        editor.putInt("lastScore", 0);
        editor.putInt("bestScore", 0);
        editor.apply();

        tvRegisterMessage.setText("Account created successfully! Your level is " + level + ".");
        tvLoginInfo.setText("You created the account. Now login to continue.");
    }
}