package hi.hbv601g.QuizGo.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

import hi.hbv601g.QuizGo.Entities.User;
import hi.hbv601g.QuizGo.Services.UserService;
import hi.hbv601g.QuizGo.R;

public class UserActivity extends AppCompatActivity {
    private UserService mUserService;
    private Button mLoginButton;
    private Button mRegisterButton;

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

        //force Portrait layout
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        mUserService = MenuActivity.getUserService();

        mLoginButton = findViewById(R.id.loginButton);
        mLoginButton.setOnClickListener(view -> {
            loginUser();
        });

        mRegisterButton = findViewById(R.id.registerButton);
        mRegisterButton.setOnClickListener(view -> {
            try {
                registerUser();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        //TODO delete this
        mTestButton = findViewById(R.id.testButton);
        mTestButton.setOnClickListener(view -> {
            mUsername.setText("Play1");
            mPassword.setText("password");
            try {
                registerUser();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            loginUser();
            mUsername.setText("Play2");
            mPassword.setText("password");
            try {
                registerUser();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            loginUser();
        });

        mUsername = findViewById(R.id.username);
        mPassword = findViewById(R.id.password);
        mUser1 = findViewById(R.id.user1);
        mUser2 = findViewById(R.id.user2);
        mUser3 = findViewById(R.id.user3);
        mUser4 = findViewById(R.id.user4);

        updateView();
    }

    public void registerUser() throws IOException {
        String name = mUsername.getText().toString();
        String pw = mPassword.getText().toString();
        if (!name.equals("") && !pw.equals("")) {
            // New thread to make Api POST call
            Thread registerApi = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        User user = mUserService.register(new User(name,pw));
                        if (user != null) {
                            displayUser(user);
                            resetInfo();
                        }
                        else {
                            displayToast(R.string.takenToast);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            registerApi.start();
        }
    }

    public void loginUser() {
        String name = mUsername.getText().toString();
        String pw = mPassword.getText().toString();
        if (!name.equals("") && !pw.equals("")) {
            // New thread to make Api GET call
            Thread loginApi = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        User user = mUserService.login(new User(name,pw));
                        if (user == null) {
                            displayToast(R.string.loginFailedToast);
                        }
                        else if (user.getUsername().equals("")) {
                            displayToast(R.string.alreadyLoggedInToast);
                        }
                        else {
                            displayUser(user);
                            resetInfo();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            loginApi.start();
        }
    }

    private void logout(int n) {
        mUserService.logout(n);
        switch (n) {
            case 0:
                mUser1.setText(mUser2.getText());
                mUser2.setText(mUser3.getText());
                mUser3.setText(mUser4.getText());
                break;
            case 1:
                mUser2.setText(mUser3.getText());
                mUser3.setText(mUser4.getText());
                break;
            case 2:
                mUser3.setText(mUser4.getText());
                break;
            case 3:
                //nothing
                break;
            default:
                System.out.println("No one is logged in (this should never print)");
        }
        mUser4.setText("");
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
            default:
                System.out.println("Max players reached");
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

    public void updateView() {
        for (User user: mUserService.getUsers()) {
            displayUser(user);
        }
    }
    //TODO interface stuff
}