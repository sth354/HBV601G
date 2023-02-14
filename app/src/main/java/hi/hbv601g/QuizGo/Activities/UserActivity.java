package hi.hbv601g.QuizGo.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import hi.hbv601g.QuizGo.Entities.Score;
import hi.hbv601g.QuizGo.Entities.User;
import hi.hbv601g.quizgo.R;

public class UserActivity extends AppCompatActivity {
    private User[] mUsers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
    }

    public User[] registerUser() {
        //TODO implement
        return null;
    }

    public User[] loginUser() {
        //TODO implement
        return null;
    }

    //TODO interface stuff
}