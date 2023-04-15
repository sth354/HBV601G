package hi.hbv601g.QuizGo.Activities;

import androidx.appcompat.app.AppCompatActivity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import java.io.IOException;
import hi.hbv601g.QuizGo.Entities.User;
import hi.hbv601g.QuizGo.Services.UserService;
import hi.hbv601g.QuizGo.R;
import android.content.Intent;
import java.net.MalformedURLException;
import java.util.concurrent.atomic.AtomicReference;

public class UserActivity extends AppCompatActivity {
    private static UserService mUserService;
    public static UserService getUserService() {
        return mUserService;
    }

    private User mUser;

    private Button mLoginButton;
    private Button mLogoutButton;
    private Button mRegisterButton;
    private Button mPlayButton;

    //TODO delete this
    private Button mTestButton;

    private EditText mUsername;
    private EditText mPassword;
    private TextView mUser1;
    private TextView mUser2;
    private TextView mUser3;
    private TextView mUser4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        if (mUserService == null) {
            try {
                mUserService = new UserService();
            } catch (MalformedURLException e) {
                throw new RuntimeException(e);
            }
        }

        //force Portrait layout
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        mUsername = findViewById(R.id.username);
        mPassword = findViewById(R.id.password);
        mUser1 = findViewById(R.id.user1);
        mUser2 = findViewById(R.id.user2);
        mUser3 = findViewById(R.id.user3);
        mUser4 = findViewById(R.id.user4);
        mLoginButton = findViewById(R.id.loginButton);
        mRegisterButton = findViewById(R.id.registerButton);
        mPlayButton = findViewById(R.id.playButton);

        mLoginButton.setOnClickListener(view -> loginUser());
        mRegisterButton.setOnClickListener(view -> registerUser());
        mPlayButton.setOnClickListener(view -> playGame());

        //TODO delete this (for testing only)
        mTestButton = findViewById(R.id.testButton);
        mTestButton.setOnClickListener(view -> {
            mUsername.setText("Play1");
            mPassword.setText("password");
            registerUser();
            loginUser();
            mUsername.setText("Play2");
            mPassword.setText("password");
            registerUser();
            loginUser();
        });
    }

    public void registerUser() {
        String name = mUsername.getText().toString();
        String pw = mPassword.getText().toString();
        if (!name.equals("") && !pw.equals("")) {

            // New thread to make Api POST call
            Thread registerApi = new Thread(() -> {
                mUser = mUserService.register(new User(name, pw));
            });

            registerApi.start();

            try {
                mUserService.mRegisterSem.acquire();
            } catch (InterruptedException ie) {
                ie.printStackTrace();
            }

            if (mUser != null) {
                displayUser(mUser);
                resetInfo();
            } else {
                displayToast(R.string.takenToast);
            }
        }
    }

    public void loginUser() {
        String name = mUsername.getText().toString();
        String pw = mPassword.getText().toString();
        if (!name.equals("") && !pw.equals("")) {

            // New thread to make Api GET call
            Thread loginApi = new Thread(() -> {
                mUser = mUserService.login(new User(name, pw));
            });

            loginApi.start();

            try {
                mUserService.mLoginSem.acquire();
            } catch (InterruptedException ie) {
                ie.printStackTrace();
            }

            if (mUser == null) {
                displayToast(R.string.loginFailedToast);
            } else if (mUser.getUsername().equals("")) {
                displayToast(R.string.alreadyLoggedInToast);
            } else {
                displayUser(mUser);
                resetInfo();
            }
        }
    }

    private void playGame() {
        if (mUserService.gameReady()) {
            Intent intent = new Intent(this, GameActivity.class);
            startActivity(intent);
        } else {
            displayToast(R.string.minPlayersToast);
        }
    }

    private void displayToast(int toast) {
        Toast.makeText(UserActivity.this, toast, Toast.LENGTH_SHORT).show();
    }

    private void displayUser(User user) {
        switch (currentUser()) {
            case 1:
                mUser1.setText(user.toString());
                break;
            case 2:
                mUser2.setText(user.toString());
                break;
            case 3:
                mUser3.setText(user.toString());
                break;
            case 4:
                mUser4.setText(user.toString());
                break;
        }
    }

    private int currentUser() {
        if (mUser1.getText().equals("")) {
            return 1;
        }
        else if (mUser2.getText().equals("")) {
            return 2;
        }
        else if (mUser3.getText().equals("")) {
            return 3;
        }
        else {
            return 4;
        }
    }

    private void resetInfo() {
        mUsername.setText("");
        mPassword.setText("");
    }
}