package hi.hbv601g.QuizGo.Entities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class User {
    //private int mId;
    private String mUsername;
    private String mPassword;

    public User(String username, String password) {
        //mId = id;
        mUsername = username;
        mPassword = password;
    }

    //public int getId() {
    //    return mId;
    //}

    //public void setId(int id) {
    //    mId = id;
    //}

    public String getUsername() {
        return mUsername;
    }

    public void setUsername(String username) {
        mUsername = username;
    }

    public String getPassword() {
        return mPassword;
    }

    public void setPassword(String password) {
        mPassword = password;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        User user = (User) obj;
        assert user != null;
        return user.getUsername().equals(this.getUsername()) && user.getPassword().equals(this.getPassword());
    }

    @NonNull
    public String toString() {
        return mUsername;
    }
}
