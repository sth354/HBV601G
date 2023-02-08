package hi.hbv601g.QuizGo.networking;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.net.Proxy;
import java.util.List;

import hi.hbv601g.QuizGo.Question;

public class NetworkManager {

    private static final String BASE_URL = "http://10.0.2.2:8080/";

    private static NetworkManager mInstance;
    private static RequestQueue mQueue;
    private Context mContext;

    public static synchronized NetworkManager getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new NetworkManager(context);
        }
        return mInstance;
    }

    private NetworkManager(Context context) {
        mContext = context;
        mQueue = getRequestQueue();
    }

    public RequestQueue getRequestQueue() {
        if (mQueue == null) {
            mQueue = Volley.newRequestQueue(mContext.getApplicationContext());
        }
        return mQueue;
    }

    public void getQuestions(final NetworkCallback<List<Question>> callback) {
        StringRequest request = new StringRequest(
                Request.Method.GET, BASE_URL + "questions", response -> {
                    Gson gson = new Gson();
                    Type listType = new TypeToken<List<Question>>(){}.getType();
                    List<Question> questionBank = gson.fromJson(response,listType);
                    callback.onSuccess(questionBank);
                }, error -> callback.onFailure(error.toString())
        );
        mQueue.add(request);
    }
}
