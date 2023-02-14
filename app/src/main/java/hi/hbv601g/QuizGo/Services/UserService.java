package hi.hbv601g.QuizGo.Services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import hi.hbv601g.QuizGo.Entities.Score;
import hi.hbv601g.QuizGo.Entities.User;

public class UserService extends Service {
    private ScoreService mScoreService;

    public UserService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public User[] register() {
        //TODO implement
        return null;
    }

    public User[] login() {
        //TODO implement
        return null;
    }

    public Score[] getScores(User[] users) {
        //TODO implement
        return null;
    }

    private boolean checkUser(User user) {
        //TODO implement
        return true;
    }

    private String passwordHash(String password) {
        //TODO implement
        return null;
    }
}