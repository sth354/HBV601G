package hi.hbv601g.QuizGo.Fragments;

import android.graphics.Paint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import hi.hbv601g.QuizGo.Activities.GameBoard;

public class GameFragment extends Fragment {

    private static GameBoard mGameBoard;

    public GameFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mGameBoard = new GameBoard(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return mGameBoard;
    }

    public void setPlayer(Paint color, int location) {
        System.out.println(location);
        mGameBoard.addCircle(color, location);
        mGameBoard.invalidate();
    }

    public void resetBoard() {
        mGameBoard.clear();
    }
}
