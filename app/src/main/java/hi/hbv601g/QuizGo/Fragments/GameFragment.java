package hi.hbv601g.QuizGo.Fragments;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
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

    public void setPlayer(Paint color, int location) {
        if (location != 0) {
            mCanvas.removePrevCircles(color);
        }
        mCanvas.addCircle(color, location);
        mCanvas.invalidate();
    }
}
