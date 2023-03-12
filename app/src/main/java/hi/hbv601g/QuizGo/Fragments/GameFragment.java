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

    public GameFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Context mcontext = getActivity();
        MyCanvas canvas = new MyCanvas(mcontext);
        canvas.setBackgroundColor(Color.RED);
        return canvas;
    }

}