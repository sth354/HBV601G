package com.example.quizgo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Button mButtonTrue;
    private Button mButtonFalse;
    private Button mButtonNext;
    private TextView mQuestionText;

    private Question[] mQuestionBank = new Question[] {
            new Question(R.string.question_text1,true),
            new Question(R.string.question_text2,false),
            new Question(R.string.question_text3,true),
            new Question(R.string.question_text4,false),
            new Question(R.string.question_text5,true)
    };

    private int mCurrentIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        updateQuestion();

        mButtonTrue = findViewById(R.id.true_button);
        mButtonTrue.setOnClickListener(view -> checkAnswer(true));

        mButtonFalse = findViewById(R.id.false_button);
        mButtonFalse.setOnClickListener(view -> checkAnswer(false));

        mButtonNext = findViewById(R.id.next_button);
        mButtonNext.setOnClickListener(view -> {
            mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.length;
            updateQuestion();
        });
    }

    private void updateQuestion() {
        mQuestionText = findViewById(R.id.question_text);
        mQuestionText.setText(mQuestionBank[mCurrentIndex].getTextResId());
    }

    private void checkAnswer(boolean userPressedTrue) {
        boolean answerIsTrue = mQuestionBank[mCurrentIndex].isAnswerTrue();

        if (userPressedTrue == answerIsTrue) {
            Toast.makeText(MainActivity.this,R.string.correct_toast,Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(MainActivity.this,R.string.incorrect_toast,Toast.LENGTH_SHORT).show();
        }
    }
}