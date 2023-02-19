package hi.hbv601g.QuizGo.Services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import java.util.List;

import hi.hbv601g.QuizGo.Activities.MenuActivity;
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
    private List<User> mUsers;
    private Score[] mScores;
    private int mDifficulty;
    private int currentPlayer;

    public GameService() {
        currentPlayer = 0;
        mUserService = MenuActivity.getUserService();
        mUsers = mUserService.getUsers();
        System.out.println(mUsers.get(0).toString());
        setDifficulty(0);

        int n = mUsers.size();
        mScores = new Score[n];
        for (int i = 0; i < n; i++) {
            mScores[i] = new Score(0,mUsers.get(i),0,0);
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public List<User> getUsers() {
        return mUsers;
    }

    public Score getScore() {
        System.out.println(currentPlayer);
        return mScores[currentPlayer];
    }

    public User currentPlayer() {
        return mUsers.get(currentPlayer);
    }

    public Question[] getQuestions() {
        //TODO implement (gets 10 questions at a time, doesnt get same questions)
        return mQuestions;
    }

    public void saveGame() {
        //TODO implement
    }

    public void nextPlayer() {
        if (currentPlayer < mUsers.size() - 1) {
            currentPlayer++;
        }
        else {
            currentPlayer = 0;
        }
    }

    public int correctAnswer() {
        int score = mScores[currentPlayer].getScore()+1;
        mScores[currentPlayer].setScore(mScores[currentPlayer].getScore()+1);
        nextPlayer();
        return score;
    }

    public void setDifficulty(int difficulty) {
        mDifficulty = difficulty;
    }
}