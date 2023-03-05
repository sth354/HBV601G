package hi.hbv601g.QuizGo.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import java.util.List;

import hi.hbv601g.QuizGo.Entities.User;
import hi.hbv601g.QuizGo.R;
import hi.hbv601g.QuizGo.Services.UserService;

public class MenuActivity extends AppCompatActivity {

    private static UserService mUserService;

    private List<User> mUsers;

    private Button playButton;
    private Button loadButton;
    private Button loginButton;
    private Button scoreButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        mUserService = new UserService();
        updatePlayers();

        playButton = findViewById(R.id.playButton);
        playButton.setOnClickListener(view -> {
            playGame();
        });

        loadButton = findViewById(R.id.loadButton);

        loginButton = findViewById(R.id.userButton);
        loginButton.setOnClickListener(view -> {
            choosePlayers();
        });

        scoreButton = findViewById(R.id.scoreButton);
    }

    public static UserService getUserService() {
        return mUserService;
    }

    public void playGame() {
        if (mUsers != null) {
            Intent intent = new Intent(this, GameActivity.class);
            startActivity(intent);
        }
    }

    public void loadGame() {
        //TODO implement
    }

    public void choosePlayers() {
        //temporary
        mUserService.register(new User("player1","password1"));

        Intent intent = new Intent(this, UserActivity.class);
        startActivity(intent);

        //TODO implement
    }

    public void viewScores() {
        //TODO implement
    }

    private void updatePlayers() {
        mUsers = mUserService.getUsers();
    }

    //TODO interface stuff
}