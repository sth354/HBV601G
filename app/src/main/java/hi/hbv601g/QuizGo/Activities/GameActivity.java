package hi.hbv601g.QuizGo.Activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
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

    //constants
    private static final String DEFAULT_SCORE = "0";

    private final Thread mQuestionApi = new Thread(() -> {
        try {
            mQuestions = mGameService.getQuestions();
        } catch (Exception e) {
            e.printStackTrace();
        }
    });

    //interface variables
    private GameFragment mGameFragment;
    private TextView mQuestion;
    private Button mSkip;
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

        //force Portrait layout
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        //initializing view objects
        mQuestion = findViewById(R.id.question);
        //mSkip = findViewById(R.id.skipButton);
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
        mSkip.setOnClickListener(view -> skipQuestion());
        mSeeAnswer.setOnClickListener(view -> setSeeAnswer());
        mRight.setOnClickListener(view -> correct());
        mWrong.setOnClickListener(view -> incorrect());

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

    /**
     * Creates and displays the win screen dialog.
     */
    private void winDialog(String name) {
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.btn_star_big_on)
                .setTitle("Winner!")
                .setMessage("Congratulations " + name + "!" + " You won!")
                .setPositiveButton("Exit", (dialog, which) -> exit())
                .show();
    }

    /**
     * Exits the activity.
     */
    private void exit() {
        mGameService.exit();
        finish();
    }

    //drawing methods:

    /**
     * Makes the fragment draw the current player.
     */
    private void playingLocation() {
        User player = mGameService.currentPlayer();
        mGameFragment.setPlayer(player.getColor(), player.getScore());
    }

    /**
     * Makes the fragment draw the other players locations.
     */
    private void prevLocations() {
        mGameFragment.resetBoard();
        List<User> players = mGameService.getUsers();
        User currentPlayer = mGameService.currentPlayer();
        for (User player: players) {
            if (!player.equals(currentPlayer)) {
                mGameFragment.setPlayer(player.getColor(), player.getScore());
            }
        }
    }

    //question methods:

    /**
     * Checks if 10 questions have been seen, gets more if needed.
     */
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
    }

    /**
     * Displays the current question.
     * (also checks for multiple choice quesions)
     */
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

    /**
     * Skips the current question.
     */
    private void skipQuestion() {
        mCurrentQuestion++;
        updateQuestion();
    }

    //Button handlers:

    /**
     * Handler for the See Answer button.
     */
    private void setSeeAnswer() {
        mSeeAnswer.setText(mQuestions[mCurrentQuestion-1].getAnswer());
    }

    /**
     * Handler for the Right button.
     */
    private void correct() {
        win();
        mCurrentQuestion++;
        updateQuestion();
        updateUsers(mGameService.correctAnswer());
        prevLocations();
        playingLocation();
    }

    /**
     * Handler for the Wrong button.
     */
    private void incorrect() {
        mCurrentQuestion++;
        updateQuestion();
        updateUsers(mGameService.currentScore());
    }

    //user methods:

    /**
     * Checks if the current player has won.
     */
    private void win() {
        User player = mGameService.currentPlayer();
        if (player.getScore() == 14) {
            winDialog(player.getUsername());
        }
    }

    /**
     * Updates the current playing users score, and makes the next players name bold.
     */
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
            User player = mGameService.currentPlayer();
            int currentPlayer = players.indexOf(player);
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