package hi.hbv601g.QuizGo.Entities;

public class mQuestion {
    private int mId;
    private int mDifficulty;
    private String mQuestion;
    private String mAnswer;
    private boolean mIsAnswered;

    public mQuestion(int id, int difficulty, String question, String answer, boolean isAnswered) {
        mId = id;
        mDifficulty = difficulty;
        mQuestion = question;
        mAnswer = answer;
        mIsAnswered = isAnswered;
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public int getDifficulty() {
        return mDifficulty;
    }

    public void setDifficulty(int difficulty) {
        mDifficulty = difficulty;
    }

    public String getQuestion() {
        return mQuestion;
    }

    public void setQuestion(String question) {
        mQuestion = question;
    }

    public String getAnswer() {
        return mAnswer;
    }

    public void setAnswer(String answer) {
        mAnswer = answer;
    }

    public boolean isAnswered() {
        return mIsAnswered;
    }

    public void setAnswered(boolean answered) {
        mIsAnswered = answered;
    }
}
