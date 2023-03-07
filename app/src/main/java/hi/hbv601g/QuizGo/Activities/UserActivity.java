package hi.hbv601g.QuizGo.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import hi.hbv601g.QuizGo.Entities.Score;
import hi.hbv601g.QuizGo.Entities.User;
import hi.hbv601g.QuizGo.Services.UserService;
import hi.hbv601g.QuizGo.R;

public class UserActivity extends AppCompatActivity {
    private UserService mUserService;
    private Button mLoginButton;
    private Button mRegisterButton;
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

        mUserService = MenuActivity.getUserService();

        mLoginButton = findViewById(R.id.loginButton);
        mLoginButton.setOnClickListener(view -> {
            loginUser();
        });

        mRegisterButton = findViewById(R.id.registerButton);
        mRegisterButton.setOnClickListener(view -> {
            registerUser();
        });

        mUsername = findViewById(R.id.username);
        mPassword = findViewById(R.id.password);
        mUser1 = findViewById(R.id.user1);
        mUser2 = findViewById(R.id.user2);
        mUser3 = findViewById(R.id.user3);
        mUser4 = findViewById(R.id.user4);
    }

    public void registerUser() {
        String name = mUsername.getText().toString();
        String pw = mPassword.getText().toString();

        if (!name.equals("") && !pw.equals("")) {
            User user = mUserService.register(new User(name,pw));
            if (user != null) {
                displayUser(user);
            }
        }
    }

    public void loginUser() {
        logout(2);
        String name = mUsername.getText().toString();
        String pw = mPassword.getText().toString();

        if (!name.equals("") && !pw.equals("")) {
            User user = mUserService.login(new User(name,pw));
            if (user != null) {
                displayUser(user);
            }
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

    //TODO interface stuff
}