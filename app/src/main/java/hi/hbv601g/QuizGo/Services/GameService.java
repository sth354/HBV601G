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
    private int mCurrentPlayer;

    //constants
    private final List<User> mUsers;

    public GameService() {
        mCurrentPlayer = 0;
        mUsers = MenuActivity.getUserService().getUsers();

        //semaphore for question api-call
        sem = new Semaphore(0);
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    /**
     * Returns all current players.
     * @return List of users currently playing.
     */
    public List<User> getUsers() {
        return mUsers;
    }

    /**
     * Returns the current player.
     * @return Current player.
     */
    public User currentPlayer() {
        return mUsers.get(mCurrentPlayer);
    }

    /**
     * Gets question from a trivia-api.
     * @return Array of 10 questions.
     */
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

    /**
     * Resets all values.
     */
    public void exit() {
        mCurrentPlayer = 0;
        for (User player: mUsers) {
            player.setScore(0);
        }
    }

    /**
     * Increments the player counter; lets the next player have their turn.
     */
    public void nextPlayer() {
        if (mCurrentPlayer < mUsers.size() - 1) {
            mCurrentPlayer++;
        }
        else {
            mCurrentPlayer = 0;
        }
    }

    /**
     * Increments the current players score.
     * @return The new score.
     */
    public int correctAnswer() {
        int score = mUsers.get(mCurrentPlayer).getScore()+1;
        mUsers.get(mCurrentPlayer).setScore(score);
        nextPlayer();
        return score;
    }

    /**
     * Returns the current players score, and gives the turn to the next player.
     * @return The current score.
     */
    public int currentScore() {
        int score = mUsers.get(mCurrentPlayer).getScore();
        nextPlayer();
        return score;
    }

    //Color methods:

    /**
     * Initializes colors for all players.
     */
    public void initColors() {
        int max = mUsers.size();
        for (int i = 0; i < max; i++) {
            mUsers.get(i).setColor(getPlayerColor(i));
        }
    }

    /**
     * Designates the specific color for each user.
     * @param currentPlayer integer that identifies their role
     * @return The color of the current player.
     */
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