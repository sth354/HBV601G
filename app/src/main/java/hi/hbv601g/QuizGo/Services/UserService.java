package hi.hbv601g.QuizGo.Services;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import hi.hbv601g.QuizGo.Entities.User;

public class UserService extends Service {
    public Semaphore mLoginSem;
    public Semaphore mRegisterSem;
    private final int mMinPlayers = 2;
    private final int mMaxPlayers = 4;
    private URL url;
    private HttpURLConnection con;
    private String jsonInputString;

    private String username;

    private String password;

    public List<User> mUsersPlaying;

    public UserService() throws MalformedURLException {
        mUsersPlaying = new ArrayList<>();

        mLoginSem = new Semaphore(0);
        mRegisterSem = new Semaphore(0);
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public User register(User user) {
        if (mUsersPlaying.contains(user)) {
            mRegisterSem.release();
            return new User("","");
        }

        if (maxPlayers()) {
            // try: create new user in the cloud using JSON POST request
            try {
                url = new URL("https://quizgo-api-server.onrender.com/users");
                con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("POST");
                con.setRequestProperty("Content-Type", "application/json");
                con.setRequestProperty("Accept", "application/json");
                con.setDoOutput(true);
                jsonInputString = "{\"username\": \"" +
                        user.getUsername() +
                        "\", \"password\": \""
                        + user.getPassword()
                        + "\"}";

                OutputStream os = con.getOutputStream();
                byte[] input = jsonInputString.getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
                os.close();

                BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), StandardCharsets.UTF_8));
                StringBuilder response = new StringBuilder();
                String responseLine;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }

            } catch (IOException e) {
                mRegisterSem.release();
                return null;
            }
            mUsersPlaying.add(user);
            mRegisterSem.release();
            return user;
        }
        mRegisterSem.release();
        return null;
    }

    /**
     * Function that checks whether login attempt matches our user database
     * @param user The login attempt
     * @return user if username and password match, otherwise null
     */
    public User login(User user) {
        if (mUsersPlaying.contains(user)) {
            mLoginSem.release();
            return new User("","");
        }

        if (maxPlayers()) {
            username = user.getUsername();
            password = user.getPassword();
            try {
                URL url = new URL("https://quizgo-api-server.onrender.com/users/" + username);
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("GET");
                con.setRequestProperty("Content-Type", "application/json");
                con.setRequestProperty("Accept", "application/json");

                BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), StandardCharsets.UTF_8));
                StringBuilder response = new StringBuilder();
                String responseLine;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }

                JSONArray jsonArray = new JSONArray(response.toString());
                JSONObject userJson = jsonArray.getJSONObject(0);
                String jsonUsername = userJson.getString("username");
                String jsonPassword = userJson.getString("password");
                if (jsonUsername.equals(username) && jsonPassword.equals(password)) {
                    mUsersPlaying.add(user);
                    mLoginSem.release();
                    return user;
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                mLoginSem.release();
                return null;
            }
        }
        mLoginSem.release();
        return null;
    }

    public void logout() {
        if (mUsersPlaying.size() != 0) {
            mUsersPlaying.remove(mUsersPlaying.size() - 1);
        }
    }

    public List<User> getUsers() {
        return mUsersPlaying;
    }

    public boolean gameReady() {
        return mUsersPlaying.size() >= mMinPlayers;
    }

    private String passwordHash(String passwordToHash) {
        String generatedPassword = null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            md.update("salt".getBytes(StandardCharsets.UTF_8));
            byte[] bytes = md.digest(passwordToHash.getBytes(StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder();
            for (byte aByte : bytes) {
                sb.append(Integer.toString((aByte & 0xff) + 0x100, 16).substring(1));
            }
            generatedPassword = sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return generatedPassword;
    }

    public boolean maxPlayers() {
        return mUsersPlaying.size() != mMaxPlayers;
    }
}