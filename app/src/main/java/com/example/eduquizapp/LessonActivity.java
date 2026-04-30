package com.example.eduquizapp;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Locale;

public class LessonActivity extends AppCompatActivity {

    TextView tvLessonTitle, tvLessonContent;
    Button btnSpeakLesson, btnBack;
    TextToSpeech textToSpeech;
    String lessonText;
    boolean isTtsReady = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lesson);

        tvLessonTitle = findViewById(R.id.tvLessonTitle);
        tvLessonContent = findViewById(R.id.tvLessonContent);
        btnSpeakLesson = findViewById(R.id.btnSpeakLesson);
        btnBack = findViewById(R.id.btnBack);

        btnSpeakLesson.setEnabled(false);
        btnSpeakLesson.setText("Preparing Voice...");

        SharedPreferences prefs = getSharedPreferences("EduQuizPrefs", MODE_PRIVATE);
        String level = prefs.getString("level", "Beginner");

        tvLessonTitle.setText(level + " English Lessons");

        if (level.equals("Beginner")) {
            lessonText =
                    "Lesson 1: School Vocabulary\n\n" +
                            "1. Pencil = an object used for writing.\nExample: I write with a pencil.\n\n" +
                            "2. Book = something you read.\nExample: I read my English book.\n\n" +
                            "3. Teacher = a person who helps students learn.\nExample: The teacher explains the lesson.\n\n" +
                            "4. Classroom = a room where students study.\nExample: We sit in the classroom.\n\n" +
                            "Lesson 2: Daily Words\n\n" +
                            "5. Breakfast = the first meal of the day.\nExample: I eat breakfast in the morning.\n\n" +
                            "6. Friend = a person you like and trust.\nExample: My friend helps me study.\n\n" +
                            "7. Happy = feeling good.\nExample: I am happy when I learn something new.";
        } else if (level.equals("Intermediate")) {
            lessonText =
                    "Lesson 1: Student Life Vocabulary\n\n" +
                            "1. Assignment = a task given to students.\nExample: The teacher gave us a writing assignment.\n\n" +
                            "2. Deadline = the final time to finish something.\nExample: The deadline for the project is Friday.\n\n" +
                            "3. Improve = to become better.\nExample: I practice every day to improve my English.\n\n" +
                            "4. Explain = to make something clear.\nExample: Can you explain this word to me?\n\n" +
                            "Lesson 2: Technology Vocabulary\n\n" +
                            "5. Device = an electronic tool, such as a phone or computer.\nExample: A tablet is a useful device for studying.\n\n" +
                            "6. Search = to look for information.\nExample: Students search online for new words.\n\n" +
                            "7. Password = a secret word used to protect an account.\nExample: Never share your password with others.";
        } else {
            lessonText =
                    "Lesson 1: Advanced Academic Vocabulary\n\n" +
                            "1. Analyze = to study something carefully.\nExample: Students analyze the text before answering questions.\n\n" +
                            "2. Evaluate = to judge the value or quality of something.\nExample: The teacher evaluates our project.\n\n" +
                            "3. Concept = an idea or principle.\nExample: This lesson explains an important concept.\n\n" +
                            "4. Evidence = information that supports an answer.\nExample: Use evidence from the text.\n\n" +
                            "Lesson 2: Advanced Technology Vocabulary\n\n" +
                            "5. Interface = the part of an app that users interact with.\nExample: A good interface is easy to use.\n\n" +
                            "6. Functionality = what a system or app can do.\nExample: The app has useful functionality for students.\n\n" +
                            "7. Secure = protected from danger or unauthorized access.\nExample: A strong password keeps your account secure.";
        }

        tvLessonContent.setText(lessonText);

        textToSpeech = new TextToSpeech(this, status -> {
            if (status == TextToSpeech.SUCCESS) {
                int result = textToSpeech.setLanguage(Locale.ENGLISH);

                if (result != TextToSpeech.LANG_MISSING_DATA &&
                        result != TextToSpeech.LANG_NOT_SUPPORTED) {
                    textToSpeech.setSpeechRate(0.85f);
                    textToSpeech.setPitch(1.0f);
                    isTtsReady = true;
                    btnSpeakLesson.setEnabled(true);
                    btnSpeakLesson.setText("Listen to Lesson");
                }
            }
        });

        btnSpeakLesson.setOnClickListener(v -> {
            if (isTtsReady) {
                textToSpeech.stop();
                textToSpeech.speak(lessonText, TextToSpeech.QUEUE_FLUSH, null, "LESSON_AUDIO");
            }
        });

        btnBack.setOnClickListener(v -> finish());
    }

    @Override
    protected void onDestroy() {
        if (textToSpeech != null) {
            textToSpeech.stop();
            textToSpeech.shutdown();
        }
        super.onDestroy();
    }
}