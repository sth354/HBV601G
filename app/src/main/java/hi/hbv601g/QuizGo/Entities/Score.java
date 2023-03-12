package hi.hbv601g.QuizGo.Entities;

import androidx.annotation.NonNull;

public class Score {
    private int mId;
    private User mUser;
    private int mDifficulty;
    private int mScore;

    public Score(int id, User user, int difficulty, int score) {
        mId = id;
        mUser = user;
        mDifficulty = difficulty;
        mScore = score;
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }
    public User getUser() {
        return mUser;
    }

    public void setUser(User user) {
        mUser = user;
    }

    public int getDifficulty() {
        return mDifficulty;
    }

    public void setDifficulty(int difficulty) {
        mDifficulty = difficulty;
    }

    public int getScore() {
        return mScore;
    }

    public void setScore(int score) {
        mScore = score;
    }

    @NonNull
    public String toString() {
        return mScore + "";
    }
}
