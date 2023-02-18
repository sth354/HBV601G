package hi.hbv601g.QuizGo.Services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import hi.hbv601g.QuizGo.Entities.Score;
import hi.hbv601g.QuizGo.Entities.User;

public class UserService extends Service {
    //TODO get database to replace dummy users
    private List<User> mDummyUsers = new ArrayList<User>(Arrays.asList(
            new User(0, "user1", "password1"),
            new User(1, "user2", "password2"),
            new User(2, "user3", "password3"),
            new User(3, "user4", "password4"))
    );

    private ScoreService mScoreService;

    public UserService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public User register(User user) {
        //TODO replace for loop with database call
        int n = mDummyUsers.size();
        for (int i = 0; i < n; i++) {
            if (user.getUsername().equals(mDummyUsers.get(i).getUsername())) {
                return null;
            }
        }
        user.setPassword(passwordHash(user.getPassword(),"salt"));
        mDummyUsers.add(user);
        return user;
    }

    /**
     * Function that checks whether login attempt matches our user database
     * @param user The login attempt
     * @return user if username and password match, otherwise null
     */
    public User login(User user) {
        //User doesExist = findByUsername(user.getUsername());

        //TODO replace for loop with database call
        User doesExist = null;
        int n = mDummyUsers.size();
        for (int i = 0; i < n; i++) {
            if (user.getUsername().equals(mDummyUsers.get(i).getUsername())) {
                doesExist = mDummyUsers.get(i);
            }
        }

        if (doesExist != null) {
            if (doesExist.getPassword().equals(passwordHash(user.getPassword(),"salt"))) {
                return doesExist;
            }
        }
        return null;
    }

    public Score[] getScores(User[] users) {
        //TODO implement
        return null;
    }

    private String passwordHash(String passwordToHash,String salt) {
        String generatedPassword = null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            md.update(salt.getBytes(StandardCharsets.UTF_8));
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
}