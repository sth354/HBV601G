package hi.hbv601g.QuizGo.Entities;

public class Question {
    private final String mQuestion;
    private final String mAnswer;

    public Question(String question, String answer) {
        mQuestion = question;
        mAnswer = answer;
    }

    public String getQuestion() {
        return mQuestion;
    }

    public String getAnswer() {
        return mAnswer;
    }

}
