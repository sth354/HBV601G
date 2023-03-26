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

    private List<Circle> circles = new ArrayList<>();
    private Bitmap backgroundBitmap;

    private Paint paint;

    private void addCircle(float x, float y, float radius) {
        circles.add(new Circle(x, y, radius));
    }

    private void createCircles() {
        addCircle(450, 150, 25); // Numer 1
        addCircle(540, 185, 25); // Numer 2
        addCircle(605, 260, 25); // Numer 3
        addCircle(650, 350, 25); // Numer 4
        addCircle(650, 450, 25); // Numer 5
        addCircle(605, 540, 25); // Numer 6
        addCircle(540, 605, 25); // Numer 7
        addCircle(450, 650, 25); // Numer 8

        addCircle(350, 650, 25); // Numer 9
        addCircle(260, 605, 25); // Numer 10
        addCircle(185, 540, 25); // Numer 11
        addCircle(150, 450, 25); // Numer 12
        addCircle(150, 350, 25); // Numer 13
        addCircle(185, 260, 25); // Numer 14
        addCircle(260, 185, 25); // Numer 15
        addCircle(350, 150, 25); // Numer 16
    }

    public MyCanvas(Context context) {
        super(context);
        init(null);
        initializePaint();
        createCircles();
    }

    public MyCanvas(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
        initializePaint();
        createCircles();
    }

    public MyCanvas(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
        initializePaint();
        createCircles();
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