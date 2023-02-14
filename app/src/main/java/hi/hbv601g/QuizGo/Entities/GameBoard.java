package hi.hbv601g.QuizGo.Entities;

public class GameBoard {
    private int mGbId;
    private String mGbName;
    private Score[] mScores;
    private int mDifficulty;
    private int[] mPlayerLocations;
    private Question[] mQuestions;

    public GameBoard(int gbId, String gbName, Score[] scores, int difficulty, int[] playerLocations, Question[] questions) {
        mGbId = gbId;
        mGbName = gbName;
        mScores = scores;
        mDifficulty = difficulty;
        mPlayerLocations = playerLocations;
        mQuestions = questions;
    }

    public int getGbId() {
        return mGbId;
    }

    public void setGbId(int gbId) {
        mGbId = gbId;
    }

    public String getGbName() {
        return mGbName;
    }

    public void setGbName(String gbName) {
        mGbName = gbName;
    }

    public Score[] getScores() {
        return mScores;
    }

    public void setScores(Score[] scores) {
        mScores = scores;
    }

    public int getDifficulty() {
        return mDifficulty;
    }

    public void setDifficulty(int difficulty) {
        mDifficulty = difficulty;
    }

    public int[] getPlayerLocations() {
        return mPlayerLocations;
    }

    public void setPlayerLocations(int[] playerLocations) {
        mPlayerLocations = playerLocations;
    }

    public Question[] getQuestions() {
        return mQuestions;
    }

    public void setQuestions(Question[] questions) {
        mQuestions = questions;
    }
}
