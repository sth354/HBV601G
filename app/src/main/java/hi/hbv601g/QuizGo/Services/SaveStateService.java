package hi.hbv601g.QuizGo.Services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import hi.hbv601g.QuizGo.Entities.GameBoard;
import hi.hbv601g.QuizGo.Entities.Score;

public class SaveStateService extends Service {
    public SaveStateService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public void saveState(GameBoard gameBoard) {
        //TODO implement
    }

    public GameBoard loadState(int gbId) {
        //TODO implement
        return null;
    }
}