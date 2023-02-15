package hi.hbv601g.QuizGo.Services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import hi.hbv601g.QuizGo.Entities.Score;
import hi.hbv601g.QuizGo.Entities.User;

public class ScoreService extends Service {
    public ScoreService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public void saveScore(Score score) {
        //TODO implement
    }

    public Score[] getScores(User user) {
        //TODO implement
        return null;
    }
}