package hi.hbv601g.QuizGo.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import hi.hbv601g.QuizGo.Entities.User;
import hi.hbv601g.quizgo.R;

public class MenuActivity extends AppCompatActivity {
    private User[] users;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
    }

    public void playGame() {
        //TODO implement
    }

    public void loadGame() {
        //TODO implement
    }

    public void choosePlayers() {
        //TODO implement
    }

    public void viewScores() {
        //TODO implement
    }

    //TODO interface stuff
}