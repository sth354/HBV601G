package hi.hbv601g.QuizGo.Activities;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;

import hi.hbv601g.QuizGo.R;

public class MyCanvas extends View {

    private Bitmap backgroundBitmap;

    public MyCanvas(Context context) {
        super(context);
        init(null);
    }

    public MyCanvas(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public MyCanvas(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(@Nullable AttributeSet set) {
        // Load the background image from resources
        backgroundBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.hringur);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // Draw the background image on the canvas
        canvas.drawBitmap(backgroundBitmap, 0, 0, null);
    }
}