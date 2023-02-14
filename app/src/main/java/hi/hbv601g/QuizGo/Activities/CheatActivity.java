package hi.hbv601g.QuizGo.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import hi.hbv601g.quizgo.R;

public class CheatActivity extends AppCompatActivity {

    private static final String TAG = "CheatActivity";
    private static final String EXTRA_ANSWER_IS_TRUE = "com.example.quizgo.isAnswerTrue";
    private static final String EXTRA_ANSWER_IS_SHOWN = "com.example.quizgo.answerIsShown";
    private static final String TRUE = "True";
    private static final String FALSE = "False";

    private Button mButtonShowAnswer;
    private TextView mAnswerText;

    private boolean mIsAnswerTrue;

    public static Intent newIntent(Context packageContext, boolean isAnswerTrue) {
        Intent intent = new Intent(packageContext, CheatActivity.class);
        intent.putExtra(EXTRA_ANSWER_IS_TRUE, isAnswerTrue);
        return intent;
    }

    public static boolean wasAnswerShown(Intent data) {
        return data.getBooleanExtra(EXTRA_ANSWER_IS_SHOWN,false);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cheat);

        mIsAnswerTrue = getIntent().getBooleanExtra(EXTRA_ANSWER_IS_TRUE, false);
        Log.d(TAG, "onCreate: " + mIsAnswerTrue);

        mAnswerText = findViewById(R.id.answer_text);

        mButtonShowAnswer = findViewById(R.id.showAnswer_button);
        mButtonShowAnswer.setOnClickListener(view -> {
            if (mIsAnswerTrue) {
                mAnswerText.setText(TRUE);
            }
            else {
                mAnswerText.setText(FALSE);
            }
            setAnswerShowResult(true);
        });
    }

    private void setAnswerShowResult(boolean isAnswerShown) {
        Intent data = new Intent();
        data.putExtra(EXTRA_ANSWER_IS_SHOWN,isAnswerShown);
        setResult(RESULT_OK,data);
    }
}