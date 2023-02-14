package hi.hbv601g.QuizGo.Entities;

public class User {
    private int mId;
    private String mUsername;
    private String mPassword;


    public User(int id, String username, String password) {
        mId = id;
        mUsername = username;
        mPassword = password;
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

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
}
