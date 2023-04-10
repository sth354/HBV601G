package hi.hbv601g.QuizGo.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import java.net.MalformedURLException;
import java.util.List;

import hi.hbv601g.QuizGo.Entities.User;
import hi.hbv601g.QuizGo.R;
import hi.hbv601g.QuizGo.Services.UserService;

public class MenuActivity extends AppCompatActivity {
    //public UserService
    private static UserService mUserService;
    public static UserService getUserService() {
        return mUserService;
    }

    //variables
    private List<User> mUsers;

    //interface variables
    private Button mPlayButton;
    private Button mLoginButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        //force Portrait layout
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        try {
            mUserService = new UserService();
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }

        mPlayButton = findViewById(R.id.playButton);
        mPlayButton.setOnClickListener(view -> playGame());

        mLoginButton = findViewById(R.id.userButton);
        mLoginButton.setOnClickListener(view -> choosePlayers());
    }

    /**
     * Starts GameActivity if there are enough players logged in.
     */
    private void playGame() {
        if (mUserService.gameReady()) {
            Intent intent = new Intent(this, GameActivity.class);
            startActivity(intent);
        }
        else {
            displayToast(R.string.minPlayersToast);
        }
    }

    /**
     * Starts UserActivity.
     */
    private void choosePlayers() {
        Intent intent = new Intent(this, UserActivity.class);
        startActivity(intent);
    }

    /**
     * Displays a toast with a given string.
     * @param toast integer code for the given string
     */
    private void displayToast(int toast) {
        Toast.makeText(MenuActivity.this, toast, Toast.LENGTH_SHORT).show();
    }
}