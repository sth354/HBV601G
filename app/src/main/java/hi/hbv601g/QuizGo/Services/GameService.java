package hi.hbv601g.QuizGo.Services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import hi.hbv601g.QuizGo.Entities.GameBoard;
import hi.hbv601g.QuizGo.Entities.Question;
import hi.hbv601g.QuizGo.Entities.Score;
import hi.hbv601g.QuizGo.Entities.User;

public class GameService extends Service {
    private ScoreService mScoreService;
    private SaveStateService mSaveStateService;
    private User[] mUsers;
    private Score[] mScores;
    private int mDifficulty;

    public GameService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public Question[] getQuestions() {
        //TODO implement
        return null;
    }

    public void saveGame() {
        //TODO implement
    }

    public void nextPlayer() {
        //TODO implement
    }

    public Score play() {
        //TODO implement
        return null;
    }
}