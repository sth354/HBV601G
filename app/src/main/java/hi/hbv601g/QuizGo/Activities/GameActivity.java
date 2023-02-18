package hi.hbv601g.QuizGo.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import java.util.ArrayList;

import hi.hbv601g.QuizGo.Entities.Question;
import hi.hbv601g.QuizGo.Entities.User;
import hi.hbv601g.QuizGo.Services.GameService;
import hi.hbv601g.QuizGo.R;

public class GameActivity extends AppCompatActivity {
    private static final String USER_CODE = "hi.hbv601g.QuizGo.Users";

    private GameService mGameService;
    private Question[] mQuestions;
    private int mCurrentQuestion;
    private int[] mPlayerLocations;

    private int[] mUserIds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        //sending ids for now
        Intent intent = getIntent();
        mUserIds = intent.getIntArrayExtra(USER_CODE);
    }

    public Question[] getQuestions() {
        return mGameService.getQuestions();
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