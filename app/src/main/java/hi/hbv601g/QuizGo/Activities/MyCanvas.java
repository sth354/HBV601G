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
        addCircle(320, 100, 25);
        addCircle(520, 100, 25);
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
        float centerX = getWidth() / 2f;
        float centerY = getHeight() / 2f;
        float radius = 30; // You can change this value to adjust the size of the circle
        canvas.drawCircle(centerX, centerY, radius, paint);
        canvas.drawCircle(centerX, centerY, radius, paint);

        Paint paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.BLUE);

        for (Circle circle : circles) {
            canvas.drawCircle(circle.x, circle.y, circle.radius, paint);
        }
    }
}