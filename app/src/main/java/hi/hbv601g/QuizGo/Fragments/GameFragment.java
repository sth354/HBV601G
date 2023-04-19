package hi.hbv601g.QuizGo.Fragments;

import android.graphics.Paint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.concurrent.Semaphore;

import hi.hbv601g.QuizGo.View.GameBoard;

public class GameFragment extends Fragment {

    public Semaphore sem;

    private static GameBoard mGameBoard;

    public GameFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sem = new Semaphore(0);
        mGameBoard = new GameBoard(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return mGameBoard;
    }

    public void setPlayer(Paint color, int location) {
        mGameBoard.addCircle(color, location);
        mGameBoard.invalidate();
    }

    public void resetBoard() {
        mGameBoard.clear();
    }
}
