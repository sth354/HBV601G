package hi.hbv601g.QuizGo.Entities;

public class Question {

    //TODO: Put @SerializedName("")
    private int mId;
    //TODO: Put @SerializedName("")
    private int mTextResId;
    //TODO: Put @SerializedName("")
    private boolean mAnswerTrue;

    public Question(int textResId, boolean answerTrue) {
        mTextResId = textResId;
        mAnswerTrue = answerTrue;
    }

    public int getTextResId() {
        return mTextResId;
    }

    public void setTextResId(int textResId) {
        mTextResId = textResId;
    }

    public boolean isAnswerTrue() {
        return mAnswerTrue;
    }

    public void setAnswerTrue(boolean answerTrue) {
        mAnswerTrue = answerTrue;
    }
}
