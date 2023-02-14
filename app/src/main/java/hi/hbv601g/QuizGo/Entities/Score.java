package hi.hbv601g.QuizGo.Entities;

public class Score {
    private User mUser;
    private int mDifficulty;
    private int mScore;

    public Score(User user, int difficulty, int score) {
        mUser = user;
        mDifficulty = difficulty;
        mScore = score;
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
}
