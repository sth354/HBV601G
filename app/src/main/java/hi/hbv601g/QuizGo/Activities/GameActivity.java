package hi.hbv601g.QuizGo.Activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.fonts.Font;
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

    private int[] mPlayerLocations;

    private GameFragment mGameFragment;

    private TextView mQuestion;
    private TextView mUser1;
    private TextView mUser2;
    private TextView mUser3;
    private TextView mUser4;
    private TextView mUser1Score;
    private TextView mUser2Score;
    private TextView mUser3Score;
    private TextView mUser4Score;
    private Button mTrue;
    private Button mFalse;
    private Button mSeeAnswer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mGameFragment = new GameFragment();

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .setReorderingAllowed(true)
                    .add(R.id.fragment_container, mGameFragment.getClass(), null)
                    .commit();
        }

        setContentView(R.layout.activity_game);

        mQuestion = findViewById(R.id.question);
        mTrue = findViewById(R.id.trueButton);
        mFalse = findViewById(R.id.falseButton);
        mSeeAnswer = findViewById(R.id.seeAnswerButton);

        mUser1 = findViewById(R.id.user1);
        mUser2 = findViewById(R.id.user2);
        mUser3 = findViewById(R.id.user3);
        mUser4 = findViewById(R.id.user4);
        mUser1Score = findViewById(R.id.user1Score);
        mUser2Score = findViewById(R.id.user2Score);
        mUser3Score = findViewById(R.id.user3Score);
        mUser4Score = findViewById(R.id.user4Score);

        mGameService = new GameService();
        updateUsers(-1);

        //NEW THREAD TO MAKE THE API CALL ONLINE AND GENERATE QUESTIONS
        Thread questionApi = new Thread(() -> {
            try {
                mQuestions = mGameService.getQuestions();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        questionApi.start();
        try {
            mGameService.sem.acquire();
        } catch (InterruptedException ie) {
            ie.printStackTrace();
        }
        updateQuestion();

        //listeners
        mSeeAnswer.setOnClickListener(view -> {
            mSeeAnswer.setText(mQuestions[mCurrentQuestion-1].getAnswer());
        });
        mTrue.setOnClickListener(view -> {
            updateQuestion();
            updateUsers(mGameService.correctAnswer());
            updatePlayerLocations();
        });
        mFalse.setOnClickListener(view -> {
            updateQuestion();
            updateUsers(mGameService.currentScore());
        });
        initLocations();
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
    
    public void updateUsers(int i) {
        List<User> players = mGameService.getUsers();
        if (i == -1) {
            mUser1.setText(players.get(0).toString());
            mUser1Score.setText("0");
            mUser2.setText(players.get(1).toString());
            mUser2Score.setText("0");
            if (players.size() > 2) {
                mUser3.setText(players.get(2).toString());
                mUser3Score.setText("0");
            }
            if (players.size() > 3) {
                mUser4.setText(players.get(3).toString());
                mUser4Score.setText("0");
            }
            mUser1.setTypeface(null, Typeface.BOLD);
        }
        else {
            int currentPlayer = mGameService.currentPlayer();
            int lastPlayer = players.size()-1;
            switch (currentPlayer) {
               case 0:
                   switch (lastPlayer) {
                       case 1:
                           mUser2Score.setText(Integer.toString(i));
                           mUser2.setTypeface(null, Typeface.NORMAL);
                           break;
                       case 2:
                           mUser3Score.setText(Integer.toString(i));
                           mUser3.setTypeface(null, Typeface.NORMAL);
                           break;
                       case 3:
                           mUser4Score.setText(Integer.toString(i));
                           mUser4.setTypeface(null, Typeface.NORMAL);
                           break;
                       default:
                           System.out.println("errrorrrrr");
                  }
                  mUser1.setTypeface(null, Typeface.BOLD);
                  break;
               case 1:
                   mUser1Score.setText(Integer.toString(i));
                   mUser1.setTypeface(null, Typeface.NORMAL);
                   mUser2.setTypeface(null, Typeface.BOLD);
                   break;
               case 2:
                   mUser2Score.setText(Integer.toString(i));
                   mUser2.setTypeface(null, Typeface.NORMAL);
                   mUser3.setTypeface(null, Typeface.BOLD);
                   break;
               case 3:
                   mUser3Score.setText(Integer.toString(i));
                   mUser3.setTypeface(null, Typeface.NORMAL);
                   mUser4.setTypeface(null, Typeface.BOLD);
                   break;
               default:
                   System.out.println("errrorrrrr");
            }
        }
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

    public void initLocations() {
        Paint color = new Paint();
        color.setStyle(Paint.Style.FILL);
        color.setColor(Color.BLUE);
        mGameFragment.setPlayer(color,0, mGameService.getUsers().size());
    }

    public void updatePlayerLocations() {




        int max = mGameService.getUsers().size();
        for (int i = 0; i < max; i++) {
            System.out.println(mGameService.getUsers().get(i).getScore());
            //mGameFragment.setPlayer(i,mGameService.getUsers().get(i).getScore(),max);
        }
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Exit")
                .setMessage("Are you sure you want to go back to the menu? (data will be lost)")
                .setPositiveButton("Yes", (dialog, which) -> finish())
                .setNegativeButton("No", null)
                .show();
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