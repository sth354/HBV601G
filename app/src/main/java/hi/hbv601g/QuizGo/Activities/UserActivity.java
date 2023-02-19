package hi.hbv601g.QuizGo.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;

import hi.hbv601g.QuizGo.Entities.Score;
import hi.hbv601g.QuizGo.Entities.User;
import hi.hbv601g.QuizGo.Services.UserService;
import hi.hbv601g.QuizGo.R;

public class UserActivity extends AppCompatActivity {
    private UserService mUserService;
    private final int mMaxPlayers = 4;
    private final int mMinPlayers = 2;
    private User[] mUsers;

    private Button mLoginButton;
    private Button mRegisterButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        mUserService = MenuActivity.getUserService();

        mLoginButton = findViewById(R.id.loginButton);
        mLoginButton.setOnClickListener(view -> {
            loginUser();
        });

        mRegisterButton = findViewById(R.id.registerButton);
        mRegisterButton.setOnClickListener(view -> {
            registerUser();
        });
    }

    public void registerUser() {

    }

    public void loginUser() {
        
    }

    //TODO interface stuff
}