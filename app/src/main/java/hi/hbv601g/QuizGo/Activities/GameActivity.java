package hi.hbv601g.QuizGo.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import hi.hbv601g.QuizGo.Entities.Question;
import hi.hbv601g.QuizGo.Services.GameService;
import hi.hbv601g.quizgo.R;

public class GameActivity extends AppCompatActivity {
    private GameService mGameService;
    private Question[] mQuestions;
    private int mCurrentQuestion;
    private int[] mPlayerLocations;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
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