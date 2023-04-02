package hi.hbv601g.QuizGo.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import java.net.MalformedURLException;
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

        try {
            mUserService = new UserService();
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
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
        if (mUserService.gameReady()) {
            Intent intent = new Intent(this, GameActivity.class);
            startActivity(intent);
        }
        else {
            displayToast(R.string.minPlayersToast);
        }
    }

    public void loadGame() {
        //TODO implement
    }

    public void choosePlayers() {
        //temporary
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

    private void displayToast(int toast) {
        Toast.makeText(MenuActivity.this, toast, Toast.LENGTH_SHORT).show();
    }

    //TODO interface stuff
}