package hi.hbv601g.QuizGo.Services;

import android.app.Service;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.IBinder;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.util.List;
import java.util.concurrent.Semaphore;

import hi.hbv601g.QuizGo.Activities.MenuActivity;
import hi.hbv601g.QuizGo.Entities.Question;
import hi.hbv601g.QuizGo.Entities.User;

public class GameService extends Service {
    //variables
    public Semaphore sem;
    private UserService mUserService;
    private List<User> mUsers;
    private int mCurrentPlayer;

    public GameService() {
        mCurrentPlayer = 0;
        mUserService = MenuActivity.getUserService();
        mUsers = mUserService.getUsers();

        sem = new Semaphore(0);
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public List<User> getUsers() {
        return mUsers;
    }

    public User currentPlayer() {
        return mUsers.get(mCurrentPlayer);
    }

    public Question[] getQuestions() throws IOException {
        // Make the API call and retrieve the JSON data
        String apiUrl = "https://the-trivia-api.com/api/questions?limit=10";
        URL url = new URL(apiUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.connect();
        JsonElement jsonElement = JsonParser.parseReader(new InputStreamReader((InputStream) connection.getContent()));

        // Use GSON to parse the JSON data and loop through the questions
        Question[] mQuestions = new Question[10];
        JsonArray questionsArray = jsonElement.getAsJsonArray();
        for (int i = 0; i < 10; i++) {
            JsonObject questionObject = questionsArray.get(i).getAsJsonObject();
            String question = questionObject.get("question").getAsString();
            String answer = questionObject.get("correctAnswer").getAsString();
            Question mTempQuestion = new Question(i,0,question,answer,false);
            mQuestions[i] = mTempQuestion;
        }
        sem.release();
        return mQuestions;
    }

    public void exit() {
        mCurrentPlayer = 0;
        for (User player: mUsers) {
            player.setScore(0);
        }
    }

    public void nextPlayer() {
        if (mCurrentPlayer < mUsers.size() - 1) {
            mCurrentPlayer++;
        }
        else {
            mCurrentPlayer = 0;
        }
    }

    public int correctAnswer() {
        int score = mUsers.get(mCurrentPlayer).getScore()+1;
        mUsers.get(mCurrentPlayer).setScore(score);
        nextPlayer();
        return score;
    }

    public int currentScore() {
        int score = mUsers.get(mCurrentPlayer).getScore();
        nextPlayer();
        return score;
    }

    //Color methods:

    public void initColors() {
        int max = mUsers.size();
        for (int i = 0; i < max; i++) {
            mUsers.get(i).setColor(getPlayerColor(i));
        }
    }

    public Paint getPlayerColor(int currentPlayer) {
        Paint color = new Paint();
        color.setStyle(Paint.Style.FILL);
        switch (currentPlayer) {
            case 0:
                color.setColor(Color.BLUE);
                break;
            case 1:
                color.setColor(Color.RED);
                break;
            case 2:
                color.setColor(Color.GREEN);
                break;
            case 3:
                color.setColor(Color.YELLOW);
                break;
        }
        return color;
    }
}