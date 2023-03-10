package hi.hbv601g.QuizGo.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import hi.hbv601g.QuizGo.Entities.Question;
import hi.hbv601g.QuizGo.Entities.User;
import hi.hbv601g.QuizGo.Fragments.GameFragment;
import hi.hbv601g.QuizGo.Services.GameService;
import hi.hbv601g.QuizGo.R;

public class GameActivity extends AppCompatActivity {
    private static final String USER_CODE = "hi.hbv601g.QuizGo.Users";

    private GameService mGameService;
    private Question[] mQuestions;
    private int mCurrentQuestion;
    private User mCurrentUser;
    private int[] mPlayerLocations;

    private TextView mQuestion;
    private TextView mUser1;
    private TextView mUser1Score;
    private Button mTrue;
    private Button mFalse;
    private Button mSeeAnswer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .setReorderingAllowed(true)
                    .add(R.id.fragmentContainerView, GameFragment.class, null)
                    .commit();
        }

        setContentView(R.layout.activity_game);

        mQuestion = findViewById(R.id.question);
        mTrue = findViewById(R.id.trueButton);
        mFalse = findViewById(R.id.falseButton);
        mSeeAnswer = findViewById(R.id.seeAnswerButton);
        mUser1 = findViewById(R.id.user1);
        mUser1Score = findViewById(R.id.user1Score);

        mGameService = new GameService();
        updateUsers();

        //NEW THREAD TO MAKE THE API CALL ONLINE AND GENERATE QUESTIONS
        Thread questionApi = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    mQuestions = mGameService.getQuestions();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        questionApi.start();
        try {
            mGameService.sem.acquire();
        } catch (InterruptedException ie) {
            ie.printStackTrace();
        }
        updateQuestion();

        mSeeAnswer.setOnClickListener(view -> {
            mSeeAnswer.setText(mQuestions[mCurrentQuestion-1].getAnswer());
        });
        mTrue.setOnClickListener(view -> {
            mGameService.correctAnswer();
            updateQuestion();
            updateUsers();
        });
        mFalse.setOnClickListener(view -> {
            updateQuestion();
            updateUsers();
        });
    }


    //A thread that runs when we need 10 new questions
    private void refreshQuestions() {
        Thread questionApi = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    mQuestions = mGameService.getQuestions();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        questionApi.start();
    }
    
    public void updateUsers() {
        mCurrentUser = mGameService.currentPlayer();
        mUser1.setText(mCurrentUser.toString());
        mUser1Score.setText(mGameService.getScore().toString());
    }

    public void updateQuestion() {
        if (mCurrentQuestion < 10) {
            try {
                mQuestion.setText(mQuestions[mCurrentQuestion].getQuestion());
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            mCurrentQuestion = 0; // Set index back to 0
            refreshQuestions(); // Generate 10 new questions
            try {
                mQuestion.setText(mQuestions[mCurrentQuestion].getQuestion());
                System.out.println(mQuestions[mCurrentQuestion].getQuestion());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        mSeeAnswer.setText(R.string.seeAnswer);
        mCurrentQuestion++;
    }

    public void saveGame() {
        //TODO implement
    }

    public void nextPlayer() {
        //TODO implement
    }

    public void play() {
        //TODO implement
    }


    //TODO interface stuff
}