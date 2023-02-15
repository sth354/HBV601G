package hi.hbv601g.QuizGo.Entities;

import androidx.annotation.NonNull;

public class GameBoard {
    private int mGbId;
    private String mGbName;
    private Score[] mScores;
    private int mDifficulty;
    private int mCurrentQuestion;
    private int[] mPlayerLocations;
    private Question[] mQuestions;

    public GameBoard(int gbId, String gbName, Score[] scores, int difficulty, int currentQuestion, int[] playerLocations, Question[] questions) {
        mGbId = gbId;
        mGbName = gbName;
        mScores = scores;
        mDifficulty = difficulty;
        mCurrentQuestion = currentQuestion;
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

    public int getCurrentQuestion() {
        return mCurrentQuestion;
    }

    public void setCurrentQuestion(int currentQuestion) {
        mCurrentQuestion = currentQuestion;
    }
    public Question[] getQuestions() {
        return mQuestions;
    }

    public void setQuestions(Question[] questions) {
        mQuestions = questions;
    }

    @NonNull
    public String toString() {
        //TODO implement
        return null;
    }
}
