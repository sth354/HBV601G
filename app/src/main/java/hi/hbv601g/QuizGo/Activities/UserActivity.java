package hi.hbv601g.QuizGo.Activities;

import androidx.appcompat.app.AppCompatActivity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import hi.hbv601g.QuizGo.Entities.User;
import hi.hbv601g.QuizGo.Services.UserService;
import hi.hbv601g.QuizGo.R;
import android.content.Intent;
import java.net.MalformedURLException;

public class UserActivity extends AppCompatActivity {
    //variables
    private User mUser;
    private static UserService mUserService;
    public static UserService getUserService() {
        return mUserService;
    }

    //interface variables
    private Button mLoginButton;
    private Button mLogoutButton;
    private Button mRegisterButton;
    private Button mPlayButton;
    private Button mTestButton;  //TODO delete this
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

        //creating UserService
        if (mUserService == null) {
            try {
                mUserService = new UserService();
            } catch (MalformedURLException e) {
                throw new RuntimeException(e);
            }
        }

        //force Portrait layout
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        //initializing view objects
        mUsername = findViewById(R.id.username);
        mPassword = findViewById(R.id.password);
        mUser1 = findViewById(R.id.user1);
        mUser2 = findViewById(R.id.user2);
        mUser3 = findViewById(R.id.user3);
        mUser4 = findViewById(R.id.user4);
        mLoginButton = findViewById(R.id.loginButton);
        mRegisterButton = findViewById(R.id.registerButton);
        mLogoutButton = findViewById(R.id.logoutButton);
        mPlayButton = findViewById(R.id.playButton);

        //listeners
        mLoginButton.setOnClickListener(view -> loginUser());
        mRegisterButton.setOnClickListener(view -> registerUser());
        mLogoutButton.setOnClickListener(view -> logout());
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

    //interface methods/handlers

    /**
     * Starts the game.
     */
    private void playGame() {
        if (mUserService.gameReady()) {
            Intent intent = new Intent(this, GameActivity.class);
            startActivity(intent);
        } else {
            displayToast(R.string.minPlayersToast);
        }
    }

    /**
     * Creates and displays a toast with a certain string.
     * @param toast integer id for the string to be shown
     */
    private void displayToast(int toast) {
        Toast.makeText(UserActivity.this, toast, Toast.LENGTH_SHORT).show();
    }

    /**
     * Adds the logged in player to the interface.
     * @param user the user to be added to the interface
     */
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

    /**
     * Returns the user that most recently logged in.
     */
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
        else if (mUser4.getText().equals("")) {
            return 4;
        }
        else {
            return -1;
        }
    }

    /**
     * Resets the user input.
     */
    private void resetInfo() {
        mUsername.setText("");
        mPassword.setText("");
    }

    //user methods

    /**
     * Reads the user inputs and tries to register with those credentials.
     */
    private void registerUser() {
        if (canLogin()) {
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
        else {
            displayToast(R.string.maxPlayerToast);
        }
    }

    /**
     * Reads the user inputs and tries to login with those credentials.
     */
    private void loginUser() {
        if (canLogin()) {
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
        else {
            displayToast(R.string.maxPlayerToast);
        }
    }

    /**
     * Removes the user who most recently logged in.
     */
    private void logout() {
        switch (currentUser()) {
            case 2:
                mUser1.setText("");
                break;
            case 3:
                mUser2.setText("");
                break;
            case 4:
                mUser3.setText("");
                break;
            case -1:
                mUser4.setText("");
                break;
        }
        mUserService.logout();
    }

    /**
     * Checks if the maximum player count has been reached and returns that value.
     */
    private boolean canLogin() {
        return mUserService.maxPlayers();
    }
}