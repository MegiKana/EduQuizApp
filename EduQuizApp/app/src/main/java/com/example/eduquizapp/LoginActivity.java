package com.example.eduquizapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    EditText etLoginName, etLoginPassword;
    Button btnLogin, btnGoCreateAccount;
    TextView tvLoginMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etLoginName = findViewById(R.id.etLoginName);
        etLoginPassword = findViewById(R.id.etLoginPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnGoCreateAccount = findViewById(R.id.btnGoCreateAccount);
        tvLoginMessage = findViewById(R.id.tvLoginMessage);

        btnLogin.setOnClickListener(v -> loginUser());

        btnGoCreateAccount.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
        });
    }

    private void loginUser() {
        String name = etLoginName.getText().toString().trim();
        String password = etLoginPassword.getText().toString().trim();

        if (name.isEmpty() || password.isEmpty()) {
            tvLoginMessage.setText("Please enter member name and password.");
            return;
        }

        SharedPreferences prefs = getSharedPreferences("EduQuizPrefs", MODE_PRIVATE);

        String savedName = prefs.getString("memberName", "");
        String savedPassword = prefs.getString("password", "");

        if (savedName.isEmpty()) {
            tvLoginMessage.setText("No account found. Please create an account first.");
            return;
        }

        if (!name.equals(savedName)) {
            tvLoginMessage.setText("Incorrect member name.");
            return;
        }

        if (!password.equals(savedPassword)) {
            tvLoginMessage.setText("Incorrect password.");
            return;
        }



        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}