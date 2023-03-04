package hi.hbv601g.QuizGo.Services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import hi.hbv601g.QuizGo.Entities.Score;
import hi.hbv601g.QuizGo.Entities.User;

public class UserService extends Service {
    //TODO get database to replace dummy users
    private final String userFile = "users.txt";

    public List<User> mUsersPlaying;

    private ScoreService mScoreService;

    public UserService() {
        mUsersPlaying = new ArrayList<>();
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public User register(User user) {
        //TODO replace with database call
        User doesExist = findUser(user);
        if (doesExist != null) return null;
        User newUser = new User(user.getUsername(),passwordHash(user.getPassword()));
        try {
            BufferedWriter out = new BufferedWriter(new FileWriter(userFile));
            out.write(newUser.getUsername() + " " + newUser.getPassword());
            out.newLine();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        mUsersPlaying.add(newUser);
        return newUser;
    }

    /**
     * Function that checks whether login attempt matches our user database
     * @param user The login attempt
     * @return user if username and password match, otherwise null
     */
    public User login(User user) {
        User foundUser = findUser(user);
        if (foundUser != null) {
            mUsersPlaying.add(foundUser);
        }
        return foundUser;

        //TODO replace with database call

    }

    public Score[] getScores(User[] users) {
        //TODO implement
        return null;
    }

    public List<User> getUsers() {
        return mUsersPlaying;
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
        try {
            Scanner scanner = new Scanner(new File(userFile));

            while (scanner.hasNextLine()) {
                String str = scanner.nextLine();
                String[] userStr = str.split("\\s+");
                if (user.getUsername().equals(userStr[1])) {
                    return new User(userStr[0],userStr[1]);
                }
            }

            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}