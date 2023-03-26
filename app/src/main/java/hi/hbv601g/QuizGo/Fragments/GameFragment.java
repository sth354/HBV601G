package hi.hbv601g.QuizGo.Fragments;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import hi.hbv601g.QuizGo.Activities.MyCanvas;
import hi.hbv601g.QuizGo.R;

public class GameFragment extends Fragment {

    private static MyCanvas mCanvas;

    public GameFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCanvas = new MyCanvas(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return mCanvas;
    }

    public void setPlayer(int n) {
        if (n != 0) {
            mCanvas.removeCircle(n-1);
        }
        mCanvas.addCircle(n);
    }
}
