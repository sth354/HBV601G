package hi.hbv601g.QuizGo.Services;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
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
import java.util.Scanner;
import java.util.concurrent.Semaphore;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import hi.hbv601g.QuizGo.Entities.User;

public class UserService extends Service {
    public Semaphore mLoginSem;
    public Semaphore mRegisterSem;
    private final String userFile = "/data/user/0/hi.hbv601g.QuizGo/files/users.txt";
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
        //TODO replace with database call
        if (usernameTaken(user.getUsername())) {
            mRegisterSem.release();
            return null;
        }
        User newUser = new User(user.getUsername(),passwordHash(user.getPassword()));

        // try: create new user in the cloud using JSON POST request
        try {
            url = new URL ("https://quizgo-api-server.onrender.com/users");
            con = (HttpURLConnection)url.openConnection();
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
            byte[] input = jsonInputString.getBytes("utf-8");
            os.write(input, 0, input.length);
            os.close();

            BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "utf-8"));
            StringBuilder response = new StringBuilder();
            String responseLine = null;
            while ((responseLine = br.readLine()) != null) {
                response.append(responseLine.trim());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            BufferedWriter out = new BufferedWriter(new FileWriter(userFile,true));
            out.write( newUser.getUsername() + " " + newUser.getPassword());
            out.newLine();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (!maxPlayers()) {
            mUsersPlaying.add(newUser);
            mRegisterSem.release();
            return newUser;
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
        if (!maxPlayers()) {
            username = user.getUsername();
            password = user.getPassword();
            try {
                URL url = new URL("https://quizgo-api-server.onrender.com/users/" + username);
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("GET");
                con.setRequestProperty("Content-Type", "application/json");
                con.setRequestProperty("Accept", "application/json");

                BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "utf-8"));
                StringBuilder response = new StringBuilder();
                String responseLine = null;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }

                JSONArray jsonArray = new JSONArray(response.toString());
                JSONObject userJson = jsonArray.getJSONObject(0);
                String jsonUsername = userJson.getString("username");
                String jsonPassword = userJson.getString("password");
                if (jsonUsername.equals(username) && jsonPassword.equals(password)) {
                    if (mUsersPlaying.contains(user)) {
                        mLoginSem.release();
                        return new User("",password);
                    }
                    mUsersPlaying.add(user);
                    mLoginSem.release();
                    return user;
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }
        mLoginSem.release();
        return null;
    }

    public void logout(int n) {
        mUsersPlaying.remove(n);
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
            for(int i=0; i< bytes.length ;i++){
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            generatedPassword = sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return generatedPassword;
    }

    private User findUser(User user) {
        System.out.println(mUsersPlaying.size());
        try {
            Scanner scanner = new Scanner(new File(userFile));

            while (scanner.hasNextLine()) {
                String str = scanner.nextLine();
                String[] userStr = str.split("\\s+");
                if (user.getUsername().equals(userStr[0]) && passwordHash(user.getPassword()).equals(userStr[1])) {
                    for (User playing: mUsersPlaying) {
                         if (playing.getUsername().equals(user.getUsername())) {
                             System.out.println("already logged in");
                             return new User("","");
                         }
                    }
                    return new User(userStr[0],userStr[1]);
                }
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    private boolean usernameTaken(String username) {
        try {
            Scanner scanner = new Scanner(new File(userFile));

            while (scanner.hasNextLine()) {
                String str = scanner.nextLine();
                String[] userStr = str.split("\\s+");
                if (username.equals(userStr[0])) {
                    return true;
                }
            }

            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }

    private boolean maxPlayers() {
        return mUsersPlaying.size() == mMaxPlayers;
    }
}