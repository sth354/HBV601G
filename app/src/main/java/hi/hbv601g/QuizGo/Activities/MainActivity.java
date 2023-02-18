package hi.hbv601g.QuizGo.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import hi.hbv601g.QuizGo.Networking.NetworkCallback;
import hi.hbv601g.QuizGo.Networking.NetworkManager;
import hi.hbv601g.quizgo.R;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private static final String KEY_INDEX = "index";
    private static final int REQUEST_CODE_CHEAT = 0;

    private Button mButtonTrue;
    private Button mButtonFalse;
    private Button mButtonCheat;
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
    private boolean mUserCheated = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: ");
        setContentView(R.layout.activity_main);

        if (savedInstanceState != null) {
            mCurrentIndex = savedInstanceState.getInt(KEY_INDEX, 0);
        }

        NetworkManager networkManager = NetworkManager.getInstance(this);
        networkManager.getQuestions(new NetworkCallback<List<Question>>() {
            @Override
            public void onSuccess(List<Question> result) {
                //TODO: Put result into questionBank
                Log.d(TAG, "onSuccess: ");
            }

            @Override
            public void onFailure(String errorString) {
                Log.e(TAG, "onFailure: " + errorString);
            }
        });

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

        mButtonCheat = findViewById(R.id.cheat_button);
        mButtonCheat.setOnClickListener(view -> {
            Intent intent = CheatActivity.newIntent(MainActivity.this,mQuestionBank[mCurrentIndex].isAnswerTrue());
            startActivityForResult(intent, REQUEST_CODE_CHEAT);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) {
            return ;
        }

        if (requestCode == REQUEST_CODE_CHEAT) {
            mUserCheated = CheatActivity.wasAnswerShown(data);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart: ");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume: ");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: ");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop: ");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause: ");
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        Log.d(TAG, "onSaveInstanceState: ");
        savedInstanceState.putInt(KEY_INDEX,mCurrentIndex);
    }

    private void updateQuestion() {
        mQuestionText = findViewById(R.id.question_text);
        mQuestionText.setText(mQuestionBank[mCurrentIndex].getTextResId());
        mUserCheated = false;
    }

    private void checkAnswer(boolean userPressedTrue) {
        boolean answerIsTrue = mQuestionBank[mCurrentIndex].isAnswerTrue();

        if (mUserCheated) {
            Toast.makeText(MainActivity.this,R.string.judgement_toast,Toast.LENGTH_SHORT).show();
        }
        else if (userPressedTrue == answerIsTrue) {
            Toast.makeText(MainActivity.this,R.string.correct_toast,Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(MainActivity.this,R.string.incorrect_toast,Toast.LENGTH_SHORT).show();
        }
    }
}