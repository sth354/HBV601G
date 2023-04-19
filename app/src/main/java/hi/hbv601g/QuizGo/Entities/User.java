package hi.hbv601g.QuizGo.Entities;

import android.graphics.Paint;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class User {
    private String mUsername;
    private String mPassword;
    private int mScore;
    private Paint mColor;

    public User(String username, String password) {
        //mId = id;
        mUsername = username;
        mPassword = password;
        mScore = 0;
        mColor = null;
    }

    public String getUsername() {
        return mUsername;
    }
    public String getPassword() {
        return mPassword;
    }

    public int getScore() {
        return mScore;
    }

    public void setScore(int n) {
        mScore = n;
    }

    public Paint getColor() {
        return mColor;
    }

    public void setColor(Paint n) {
        mColor = n;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        User user = (User) obj;
        assert user != null;
        return user.getUsername().equals(this.getUsername());
    }

    @NonNull
    @Override
    public String toString() {
        return mUsername;
    }
}
