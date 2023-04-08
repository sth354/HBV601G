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

    //variables
    private GameService mGameService;
    private Question[] mQuestions;
    private int mCurrentQuestion;

    private static final String DEFAULT_SCORE = "0";

    private final Thread mQuestionApi = new Thread(() -> {
        try {
            mQuestions = mGameService.getQuestions();
        } catch (Exception e) {
            e.printStackTrace();
        }
    });

    //layout objects
    private GameFragment mGameFragment;
    private TextView mQuestion;
    private Button mRight;
    private Button mWrong;
    private Button mSeeAnswer;
    private TextView mUser1;
    private TextView mUser2;
    private TextView mUser3;
    private TextView mUser4;
    private TextView mUser1Score;
    private TextView mUser2Score;
    private TextView mUser3Score;
    private TextView mUser4Score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mGameFragment = new GameFragment();
        mGameService = new GameService();

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .setReorderingAllowed(true)
                    .add(R.id.fragment_container, mGameFragment.getClass(), null)
                    .commit();
        }

        setContentView(R.layout.activity_game);

        //initializing view objects
        mQuestion = findViewById(R.id.question);
        mRight = findViewById(R.id.rightButton);
        mWrong = findViewById(R.id.wrongButton);
        mSeeAnswer = findViewById(R.id.seeAnswerButton);
        mUser1 = findViewById(R.id.user1);
        mUser2 = findViewById(R.id.user2);
        mUser3 = findViewById(R.id.user3);
        mUser4 = findViewById(R.id.user4);
        mUser1Score = findViewById(R.id.user1Score);
        mUser2Score = findViewById(R.id.user2Score);
        mUser3Score = findViewById(R.id.user3Score);
        mUser4Score = findViewById(R.id.user4Score);

        //listeners
        mSeeAnswer.setOnClickListener(view -> {
            mSeeAnswer.setText(mQuestions[mCurrentQuestion-1].getAnswer());
        });
        mRight.setOnClickListener(view -> {
            win();
            updateQuestion();
            updateUsers(mGameService.correctAnswer());
            prevLocations();
            playingLocation();
        });
        mWrong.setOnClickListener(view -> {
            updateQuestion();
            updateUsers(mGameService.currentScore());
        });

        //initialize users
        updateUsers(-1);

        //initialize gameboard
        mGameService.initColors();
        playingLocation();

        //initialize questions
        mCurrentQuestion = 10;
        mQuestions = new Question[mCurrentQuestion];
        updateQuestion();
    }

    //interface methods/handlers:

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Exit")
                .setMessage("Are you sure you want to go back to the menu? (data will be lost)")
                .setPositiveButton("Yes", (dialog, which) -> exit())
                .setNegativeButton("No", null)
                .show();
    }

    private void winDialog(String name) {
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.btn_star_big_on)
                .setTitle("Winner!")
                .setMessage("Congratulations " + name + "!" + " You won!")
                .setPositiveButton("Exit", (dialog, which) -> exit())
                .show();
    }

    private void exit() {
        mGameService.exit();
        finish();
    }

    //drawing methods:

    private void playingLocation() {
        int currentPlayer = mGameService.currentPlayer();
        User player = mGameService.getUsers().get(currentPlayer);
        mGameFragment.setPlayer(player.getColor(), player.getScore());
    }

    private void prevLocations() {
        mGameFragment.resetBoard();
        int currentPlayer = mGameService.currentPlayer();
        int max = mGameService.getUsers().size();
        for (int i = 0; i < max; i++) {
            if (i != currentPlayer) {
                User player = mGameService.getUsers().get(i);
                mGameFragment.setPlayer(player.getColor(), player.getScore());
            }
        }
    }

    //question methods:

    private void updateQuestion() {
        if (mCurrentQuestion < 10) {
            try {
                setQuestion();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            mCurrentQuestion = 0; // Set index back to 0
            mQuestionApi.start(); // Generate 10 new questions
            try {
                mGameService.sem.acquire();
            } catch (InterruptedException ie) {
                ie.printStackTrace();
            }
            try {
                setQuestion();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        mCurrentQuestion++;
    }

    private void setQuestion() {
        Question question = mQuestions[mCurrentQuestion];
        if (!question.getQuestion().contains("Which") && !question.getQuestion().contains("these")) {
            mQuestion.setText(question.getQuestion());
            mSeeAnswer.setText(R.string.seeAnswer_button);
        }
        else {
            mCurrentQuestion++;
            updateQuestion();
        }
    }

    //user methods:

    private void win() {
        User user = mGameService.getUsers().get(mGameService.currentPlayer());
        if (user.getScore() == 14) {
            winDialog(user.getUsername());
        }
    }

    private void updateUsers(int i) {
        List<User> players = mGameService.getUsers();
        if (i == -1) {
            mUser1.setText(players.get(0).toString());
            mUser1Score.setText(DEFAULT_SCORE);
            mUser2.setText(players.get(1).toString());
            mUser2Score.setText(DEFAULT_SCORE);
            if (players.size() > 2) {
                mUser3.setText(players.get(2).toString());
                mUser3Score.setText(DEFAULT_SCORE);
            }
            if (players.size() > 3) {
                mUser4.setText(players.get(3).toString());
                mUser4Score.setText(DEFAULT_SCORE);
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
}