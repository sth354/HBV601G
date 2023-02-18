package hi.hbv601g.QuizGo.Services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import hi.hbv601g.QuizGo.Entities.Question;
import hi.hbv601g.QuizGo.Entities.Score;
import hi.hbv601g.QuizGo.Entities.User;

public class GameService extends Service {
    //TODO get API to replace dummy questions
    private Question[] mQuestions = new Question[] {
            new Question(0, 0,"Question0","Answer0",false),
            new Question(1, 0,"Question2","Answer1",false),
            new Question(2, 0,"Question3","Answer2",false),
            new Question(3, 0,"Question4","Answer3",false)
    };


    private ScoreService mScoreService;
    private SaveStateService mSaveStateService;
    private UserService mUserService;
    private User[] mUsers;
    private Score[] mScores;
    private int mDifficulty;
    private int currentPlayer;

    public GameService(User[] users,int difficulty) {
        mUsers = users;
        mDifficulty = difficulty;
        currentPlayer = 0;

        int n = mUsers.length;
        mScores = new Score[n];
        for (int i = 0; i < n; i++) {
            mScores[i].setUser(mUsers[i]);
            mScores[i].setDifficulty(mDifficulty);
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public Question[] getQuestions() {
        //TODO implement (gets 10 questions at a time, doesnt get same questions)
        return mQuestions;
    }

    public void saveGame() {
        //TODO implement
    }

    public void nextPlayer() {
        currentPlayer++;
    }

    public int correctAnswer() {
        int score = mScores[currentPlayer].getScore()+1;
        mScores[currentPlayer].setScore(mScores[currentPlayer].getScore()+1);
        nextPlayer();
        return score;
    }
}