package hi.hbv601g.QuizGo.networking;

public interface NetworkCallback<T> {

    void onSuccess(T result);

    void onFailure(String errorString);
}
