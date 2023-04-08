package hi.hbv601g.QuizGo.Activities;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.view.View;
import android.graphics.Paint;

import java.util.Arrays;

import hi.hbv601g.QuizGo.R;

public class GameBoard extends View {
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
    private Circle[] circles;
    private Bitmap backgroundBitmap;

    public void addCircle(Paint color, int location) {
        float x = mCircleCoordinates[location][0];
        float y = mCircleCoordinates[location][1];
        circles[location] = new Circle(x, y, mRadius, color);
    }

    public void clear() {
        circles = new Circle[16];
        Arrays.fill(circles,null);
    }

    public GameBoard(Context context) {
        super(context);
        backgroundBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.hrings);
        circles = new Circle[16];
        clear();
    }

    public class Circle {
        float x;
        float y;
        float radius;
        Paint color;

        public Circle(float x, float y, float radius, Paint color) {
            this.x = x;
            this.y = y;
            this.radius = radius;
            this.color = color;
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // Draw the background image on the canvas
        canvas.drawBitmap(backgroundBitmap, 0, 0, null);

        for (Circle circle : circles) {
            if (circle != null) {
                canvas.drawCircle(circle.x, circle.y, circle.radius, circle.color);
            }
        }
    }
}