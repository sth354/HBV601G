package hi.hbv601g.QuizGo.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import hi.hbv601g.QuizGo.Entities.User;
import hi.hbv601g.QuizGo.R;

public class MenuActivity extends AppCompatActivity {
    private static final String USER_CODE = "hi.hbv601g.QuizGo.Users";

    private User[] mUsers;
    private int[] mUserIds;

    private Button playButton;
    private Button loadButton;
    private Button loginButton;
    private Button scoreButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

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

    public void playGame() {
        if (mUsers != null) {
            Intent intent = new Intent(this, GameActivity.class);
            intent.putExtra(USER_CODE, mUserIds);
            startActivity(intent);
        }
    }

    public void loadGame() {
        //TODO implement
    }

    public void choosePlayers() {
        //temporary
        mUsers = new User[] {
                new User(0, "user1", "password1"),
                new User(1, "user2", "password2"),
                new User(2, "user3", "password3"),
                new User(3, "user4", "password4")
        };
        mUserIds = new int[mUsers.length];
        int i = 0;
        for (User user:mUsers) {
            mUserIds[i] = user.getId();
            i++;
        }

        Intent intent = new Intent(this, UserActivity.class);
        startActivity(intent);

        //TODO implement
    }

    public void viewScores() {
        //TODO implement
    }

    //TODO interface stuff
}