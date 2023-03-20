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

        // Replace your previous Canvas implementation with the custom MyCanvas class
        MyCanvas canvas = new MyCanvas(mcontext);

        // You can remove this line because the background color will be replaced by the background image
        // canvas.setBackgroundColor(Color.RED);

        return canvas;
    }
}
