package hi.hbv601g.QuizGo.Services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

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