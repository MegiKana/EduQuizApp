package com.example.eduquizapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class QuizActivity extends AppCompatActivity {

    TextView tvProgress, tvQuestion, tvQuizMessage;
    Button btnOption1, btnOption2, btnOption3, btnNext, btnBack;

    int currentQuestion = 0;
    int score = 0;
    boolean answered = false;

    String[] questions;
    String[][] options;
    int[] correctAnswers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        tvProgress = findViewById(R.id.tvProgress);
        tvQuestion = findViewById(R.id.tvQuestion);
        tvQuizMessage = findViewById(R.id.tvQuizMessage);

        btnOption1 = findViewById(R.id.btnOption1);
        btnOption2 = findViewById(R.id.btnOption2);
        btnOption3 = findViewById(R.id.btnOption3);
        btnNext = findViewById(R.id.btnNext);
        btnBack = findViewById(R.id.btnBack);

        SharedPreferences prefs = getSharedPreferences("EduQuizPrefs", MODE_PRIVATE);
        String level = prefs.getString("level", "Beginner");

        setupQuiz(level);
        loadQuestion();

        btnOption1.setOnClickListener(v -> checkAnswer(0));
        btnOption2.setOnClickListener(v -> checkAnswer(1));
        btnOption3.setOnClickListener(v -> checkAnswer(2));

        btnNext.setOnClickListener(v -> {
            if (!answered) {
                tvQuizMessage.setText("Please choose an answer first.");
                return;
            }

            currentQuestion++;

            if (currentQuestion < questions.length) {
                answered = false;
                loadQuestion();
            } else {
                Intent intent = new Intent(QuizActivity.this, ResultActivity.class);
                intent.putExtra("score", score);
                intent.putExtra("total", questions.length);
                startActivity(intent);
                finish();
            }
        });

        btnBack.setOnClickListener(v -> finish());
    }

    private void setupQuiz(String level) {
        if (level.equals("Beginner")) {
            questions = new String[]{
                    "What is a pencil used for?",
                    "What is a book?",
                    "Who helps students learn?",
                    "Where do students study?",
                    "What is breakfast?",
                    "What means feeling good?",
                    "Which word means a person you like and trust?",
                    "Choose the correct meaning of apple.",
                    "What do students use to read lessons?",
                    "Which sentence is correct?"
            };

            options = new String[][]{
                    {"Sleeping", "Writing", "Driving"},
                    {"A food", "Something you read", "A game"},
                    {"Window", "Chair", "Teacher"},
                    {"Kitchen", "Classroom", "Garden"},
                    {"A school subject", "First meal of the day", "A toy"},
                    {"Heavy", "Cold", "Happy"},
                    {"Table", "Friend", "Pencil"},
                    {"A color", "A car", "A fruit"},
                    {"Shoe", "Book", "Ball"},
                    {"I book a read.", "I read a book.", "Read I book."}
            };

            correctAnswers = new int[]{1,1,2,1,1,2,1,2,1,1};

        } else if (level.equals("Intermediate")) {
            questions = new String[]{
                    "What does assignment mean?",
                    "What is a deadline?",
                    "Choose the correct meaning of improve.",
                    "What does explain mean?",
                    "What is a device?",
                    "What does search mean?",
                    "Why should a password be secret?",
                    "Complete: I practice to ___ my English.",
                    "Complete: The project ___ is Friday.",
                    "Which sentence is correct?"
            };

            options = new String[][]{
                    {"A type of food", "A task given to students", "A sport"},
                    {"A school bag", "Final time to finish something", "A classroom"},
                    {"To forget", "To become smaller", "To become better"},
                    {"To make something clear", "To close something", "To buy something"},
                    {"A fruit", "An electronic tool", "A person"},
                    {"To sleep", "To paint", "To look for information"},
                    {"To make it public", "To protect the account", "To lose it"},
                    {"deadline", "improve", "device"},
                    {"password", "friend", "deadline"},
                    {"Explains teacher the lesson.", "The teacher explains the lesson.", "Lesson the explains teacher."}
            };

            correctAnswers = new int[]{1,1,2,0,1,2,1,1,2,1};

        } else {
            questions = new String[]{
                    "What does analyze mean?",
                    "What does evaluate mean?",
                    "What is a concept?",
                    "What is evidence?",
                    "What is an interface?",
                    "What does functionality mean?",
                    "What does secure mean?",
                    "Complete: Students ___ the text carefully.",
                    "Complete: A good app has useful ___.",
                    "Which sentence is most academic?"
            };

            options = new String[][]{
                    {"To delete", "To study carefully", "To guess randomly"},
                    {"To sleep", "To judge quality or value", "To draw"},
                    {"A password", "A button", "An idea or principle"},
                    {"A color", "Information that supports an answer", "A mistake"},
                    {"Part of an app users interact with", "A fruit", "A school subject"},
                    {"A person", "What a system can do", "A classroom"},
                    {"Very expensive", "Very slow", "Protected from danger"},
                    {"forget", "analyze", "break"},
                    {"breakfast", "friend", "functionality"},
                    {"I like game.", "The evidence supports the answer.", "Book read me."}
            };

            correctAnswers = new int[]{1,1,2,1,0,1,2,1,2,1};
        }
    }

    private void loadQuestion() {
        tvProgress.setText("Question " + (currentQuestion + 1) + " of " + questions.length);
        tvQuestion.setText(questions[currentQuestion]);

        btnOption1.setText(options[currentQuestion][0]);
        btnOption2.setText(options[currentQuestion][1]);
        btnOption3.setText(options[currentQuestion][2]);

        tvQuizMessage.setText("");
    }

    private void checkAnswer(int selectedAnswer) {
        if (answered) return;

        answered = true;

        if (selectedAnswer == correctAnswers[currentQuestion]) {
            score++;
            tvQuizMessage.setText("Correct! Great job.");
        } else {
            tvQuizMessage.setText("Incorrect. Review the lesson and try the next one.");
        }
    }
}