package hi.hbv601g.QuizGo.Activities;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;
import android.graphics.Color;
import android.graphics.Paint;

import java.util.ArrayList;
import java.util.List;

import hi.hbv601g.QuizGo.R;

public class MyCanvas extends View {

    private final float[][] mCircleCoordinates = new float[][] {
            {450, 150}, // Numer 0
            {540, 185}, // Numer 1
            {605, 260}, // Numer 2
            {650, 350}, // Numer 3
            {650, 450}, // Numer 4
            {605, 540}, // Numer 5
            {540, 605}, // Numer 6
            {450, 650}, // Numer 7
            {350, 650}, // Numer 8
            {260, 605}, // Numer 9
            {185, 540}, // Numer 10
            {150, 450}, // Numer 11
            {150, 350}, // Numer 12
            {185, 260}, // Numer 13
            {260, 185}, // Numer 14
            {350, 150}  // Numer 15
    };

    private final float mRadius = 25;

    private List<Circle> circles = new ArrayList<>();
    private Bitmap backgroundBitmap;

    private Paint paint;

    public void addCircle(int n) {
        float x = mCircleCoordinates[n][0];
        float y = mCircleCoordinates[n][1];
        circles.add(new Circle(x,y,mRadius));
    }

    public void removeCircle(int n) {
        circles.remove(n);
    }

    public MyCanvas(Context context) {
        super(context);
        init(null);
        initializePaint();
    }

    private void initializePaint() {
        paint = new Paint();
        paint.setColor(Color.parseColor("#FFC0CB")); //Pink color
        paint.setStyle(Paint.Style.FILL);
        paint.setAntiAlias(true);
    }

    private void init(@Nullable AttributeSet set) {
        // Load the background image from resources
        backgroundBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.hrings);
    }

    public class Circle {
        float x;
        float y;
        float radius;

        public Circle(float x, float y, float radius) {
            this.x = x;
            this.y = y;
            this.radius = radius;
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // Draw the background image on the canvas
        canvas.drawBitmap(backgroundBitmap, 0, 0, null);
        // Draw circles

        Paint paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.BLUE);

        for (Circle circle : circles) {
            canvas.drawCircle(circle.x, circle.y, circle.radius, paint);
        }
    }
}