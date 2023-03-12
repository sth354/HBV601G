package hi.hbv601g.QuizGo.Services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import java.io.BufferedReader;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.util.List;
import java.util.concurrent.Semaphore;

import hi.hbv601g.QuizGo.Activities.MenuActivity;
import hi.hbv601g.QuizGo.Entities.Question;
import hi.hbv601g.QuizGo.Entities.Score;
import hi.hbv601g.QuizGo.Entities.User;

public class GameService extends Service {
    public Semaphore sem;

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
        setDifficulty(0);

        int n = mUsers.size();
        mScores = new Score[n];
        for (int i = 0; i < n; i++) {
            mScores[i] = new Score(0,mUsers.get(i),0,0);
        }

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

    public Score getScore() {
        System.out.println(currentPlayer);
        return mScores[currentPlayer];
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

    public User currentPlayer() {
        return mUsers.get(currentPlayer);
    }


    //TODO get API to replace dummy questions

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