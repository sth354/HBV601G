package hi.hbv601g.QuizGo.Services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import java.util.ArrayList;
import java.util.List;

import hi.hbv601g.QuizGo.Entities.User;

public class ScoreService extends Service {
    //TODO get database to replace dummy users
    private List<Score> mDummyScores = new ArrayList<>();
    public ScoreService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public void saveScore(Score score) {
        //TODO implement
        mDummyScores.add(score);
    }

    public Score[] getScoresByUser(User user) {
        //TODO implement
        return null;
    }

    public Score getScoreById(int id) {
        //TODO implement
        return null;
    }
}