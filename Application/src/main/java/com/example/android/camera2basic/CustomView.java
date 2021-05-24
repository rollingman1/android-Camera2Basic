package com.example.android.camera2basic;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.service.quickaccesswallet.SelectWalletCardRequest;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

public class CustomView extends View implements View.OnTouchListener {

    Point point1, point3;
    Point point2, point4;

    /**
     * point1 and point 3 are of same group and same as point 2 and point4
     */
    int groupId = -1;
    ArrayList<ColorBall> colorballs = new ArrayList<ColorBall>();
    // array that holds the balls
    int balID = 0; // variable to know what ball is being dragged
    int diffX = -1, diffY = -1; // 터치지점 관련.
    boolean isInsideTouch = false; // 드래그 관련 변수.

    Paint paint;
    Canvas canvas;
    String strokeColorString = getResources().getString(R.string.yellow);
    String fillColorString = getResources().getString(R.string.yellow_overlay);

    int screenHeight, screenWidth;

    public CustomView(Context context, Point p1, Point p2, Point p3, Point p4, int screenWidth, int screenHeight) {
        super(context);
        paint = new Paint();
        setFocusable(true); // necessary for getting the touch events
        canvas = new Canvas();
        // setting the start point for the balls
        point1 = new Point();
        point1.x = p1.x;
        point1.y = p1.y;

        point2 = new Point();
        point2.x = p2.x;
        point2.y = p2.y;

        point3 = new Point();
        point3.x = p3.x;
        point3.y = p3.y;

        point4 = new Point();
        point4.x = p4.x;
        point4.y = p4.y;

        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;

        // declare each ball with the ColorBall class
        colorballs.add(new ColorBall(context, R.drawable.yellow_circle, point1, screenWidth, screenHeight));
        colorballs.add(new ColorBall(context, R.drawable.yellow_circle, point2, screenWidth, screenHeight));
        colorballs.add(new ColorBall(context, R.drawable.yellow_circle, point3, screenWidth, screenHeight));
        colorballs.add(new ColorBall(context, R.drawable.yellow_circle, point4, screenWidth, screenHeight));

        //Listener
        this.setOnTouchListener(this);

    }


    public CustomView(Context context, AttributeSet attrs, Point p1, Point p2, Point p3, Point p4, int screenWidth, int screenHeight) {
        super(context, attrs);
        paint = new Paint();
        setFocusable(true); // necessary for getting the touch events
        canvas = new Canvas();
        // setting the start point for the balls
        point1 = new Point();
        point1.x = p1.x;
        point1.y = p1.y;

        point2 = new Point();
        point2.x = p2.x;
        point2.y = p2.y;

        point3 = new Point();
        point3.x = p3.x;
        point3.y = p3.y;

        point4 = new Point();
        point4.x = p4.x;
        point4.y = p4.y;

        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;

        // declare each ball with the ColorBall class
        colorballs.add(new ColorBall(context, R.drawable.yellow_circle, point1, screenWidth, screenHeight));
        colorballs.add(new ColorBall(context, R.drawable.yellow_circle, point2, screenWidth, screenHeight));
        colorballs.add(new ColorBall(context, R.drawable.yellow_circle, point3, screenWidth, screenHeight));
        colorballs.add(new ColorBall(context, R.drawable.yellow_circle, point4, screenWidth, screenHeight));

        //Listener
        this.setOnTouchListener(this);

    }

    // the method that draws the balls
    @Override
    protected void onDraw(Canvas canvas) {
        // canvas.drawColor(0xFFCCCCCC); //if you want another background color

        paint.setAntiAlias(true);
        paint.setDither(true);
        paint.setColor(Color.parseColor("#55000000"));
        paint.setStyle(Paint.Style.FILL);
        paint.setStrokeJoin(Paint.Join.ROUND);
        // mPaint.setStrokeCap(Paint.Cap.ROUND);

        canvas.drawPaint(paint);

        if (groupId == 1) {
            drawStrokedRect(canvas, point1.x + colorballs.get(0).getWidthOfBall() / 2,
                    point3.y + colorballs.get(2).getWidthOfBall() / 2, point3.x
                            + colorballs.get(2).getWidthOfBall() / 2, point1.y
                            + colorballs.get(0).getWidthOfBall() / 2, strokeColorString, fillColorString);

        } else {
            drawStrokedRect(canvas, point2.x + colorballs.get(1).getWidthOfBall() / 2,
                    point4.y + colorballs.get(3).getWidthOfBall() / 2, point4.x
                            + colorballs.get(3).getWidthOfBall() / 2, point2.y
                            + colorballs.get(1).getWidthOfBall() / 2, strokeColorString, fillColorString);
        }
        BitmapDrawable mBitmap;
        mBitmap = new BitmapDrawable();

        // draw the balls on the canvas
        for (ColorBall ball : colorballs) {
            canvas.drawBitmap(ball.getBitmap(), ball.getX(), ball.getY(),
                    new Paint());
        }
    }

    public void shade_region_between_points() {
        canvas.drawRect(point1.x, point3.y, point3.x, point1.y, paint);
    }

    public void drawStrokedRect(Canvas c, float left, float top, float right, float bottom, String string_color_stroke, String string_color_fill) {
        // stroke 그리기
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(8);
        paint.setColor(Color.parseColor(string_color_stroke));

        c.drawRect(left, top, right, bottom, paint);

        // Fill 채우기
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.parseColor(string_color_fill));
        c.drawRect(left, top, right, bottom, paint);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        int eventaction = event.getAction();
        int minX = -1, maxX = -1, minY = -1, maxY = -1;
        int cnt = 0;

        int X = (int) event.getX();
        int Y = (int) event.getY();

        switch (eventaction) {
            case MotionEvent.ACTION_DOWN: // touch down so check if the finger is on
                // a ball
                balID = -1;
                groupId = -1;
                cnt = 0;
                diffX = -1;
                diffY = -1;
                for (ColorBall ball : colorballs) {
                    // check if inside the bounds of the ball (circle)
                    // get the center for the ball
                    System.out.println("Id : " + ball.getID());
                    System.out.println("getX : " + ball.getX() + " getY() : " + ball.getY());
                    int centerX = ball.getX() + ball.getWidthOfBall();
                    int centerY = ball.getY() + ball.getHeightOfBall();
                    paint.setColor(Color.CYAN);
                    // calculate the radius from the touch to the center of the ball
                    double radCircle = Math
                            .sqrt((double) (((centerX - X) * (centerX - X)) + (centerY - Y)
                                    * (centerY - Y)));

                    System.out.println("X : " + X + " Y : " + Y + " centerX : " + centerX
                            + " CenterY : " + centerY + " radCircle : " + radCircle);

                    if (radCircle < ball.getWidthOfBall()) {

                        balID = ball.getID();
                        System.out.println("Selected ball : " + balID);
                        if (balID == 1 || balID == 3) {
                            groupId = 2;
                            drawStrokedRect(canvas, point1.x, point3.y, point3.x, point1.y,
                                    strokeColorString, fillColorString);
                        } else {
                            groupId = 1;
                            drawStrokedRect(canvas, point2.x, point4.y, point4.x, point2.y,
                                    strokeColorString, fillColorString);
                        }
                        invalidate();
                        break;
                    } else {
                        cnt++;
                    }
                    invalidate();
                }
                if (cnt == 4) {
                    //터치를 했지만 4개 점 말고 다른 영역을 터치한 경우
                    //박스 내부를 클릭한 경우
                    System.out.println("Pressed");
                    minX = Math.min(point1.x, point3.x);
                    maxX = Math.max(point1.x, point3.x);
                    minY = Math.min(point1.y, point3.y);
                    maxY = Math.max(point1.y, point3.y);
                    if ((minX < X && X < maxX) && (minY < Y && Y < maxY)) {
                        /*diffX = X - (minX + (maxX - minX) / 2);
                        diffY = Y - (minY + (maxY - minY) / 2);
                        System.out.println("Pressed Inside: " + X + ", " + Y + ", " + diffX + ", " + diffY + ", " + (point1.x + diffX) + ", " + (point1.y + diffY));*/
                        diffX = 0;
                        diffY = 0;
                        isInsideTouch = true;
                        /*drawStrokedRect(canvas, point1.x+diffX, point3.y+diffY, point3.x+diffX, point1.y+diffY,
                                strokeColorString, fillColorString);
                        invalidate();*/
                        break;
                    }
                }
                invalidate();
                break;

            case MotionEvent.ACTION_MOVE: // touch drag with the ball
                // move the balls the same as the finger
                if (balID > -1) {
                    System.out.println("Moving Ball : " + balID);

                    colorballs.get(balID).setX(X);
                    colorballs.get(balID).setY(Y);

                    paint.setColor(Color.CYAN);

                    if (groupId == 1) {
                        if (colorballs.get(2).getX() < colorballs.get(0).getX() && colorballs.get(2).getY() < colorballs.get(0).getY()) {
                            colorballs.get(1).setX(colorballs.get(2).getX());
                            colorballs.get(1).setY(colorballs.get(0).getY());
                            colorballs.get(3).setX(colorballs.get(0).getX());
                            colorballs.get(3).setY(colorballs.get(2).getY());
                        } else {
                            colorballs.get(1).setX(colorballs.get(0).getX());
                            colorballs.get(1).setY(colorballs.get(2).getY());
                            colorballs.get(3).setX(colorballs.get(2).getX());
                            colorballs.get(3).setY(colorballs.get(0).getY());
                        }
                        drawStrokedRect(canvas, point1.x, point3.y, point3.x, point1.y,
                                strokeColorString, fillColorString);
                    } else {
                        if (colorballs.get(1).getX() < colorballs.get(3).getX() && colorballs.get(1).getY() > colorballs.get(3).getY()) {
                            colorballs.get(0).setX(colorballs.get(3).getX());
                            colorballs.get(0).setY(colorballs.get(1).getY());
                            colorballs.get(2).setX(colorballs.get(1).getX());
                            colorballs.get(2).setY(colorballs.get(3).getY());
                        } else {
                            colorballs.get(0).setX(colorballs.get(1).getX());
                            colorballs.get(0).setY(colorballs.get(3).getY());
                            colorballs.get(2).setX(colorballs.get(3).getX());
                            colorballs.get(2).setY(colorballs.get(1).getY());
                        }
                        drawStrokedRect(canvas, point2.x, point4.y, point4.x, point2.y,
                                strokeColorString, fillColorString);
                    }
                    invalidate();
                }
                if (isInsideTouch) {
                    minX = Math.min(point1.x, point3.x);
                    maxX = Math.max(point1.x, point3.x);
                    minY = Math.min(point1.y, point3.y);
                    maxY = Math.max(point1.y, point3.y);

                    diffX = (int) event.getX() - (minX + (maxX - minX) / 2);
                    diffY = (int) event.getY() - (minY + (maxY - minY) / 2);

                    System.out.println("box move (" + diffX + ", " + diffY + ")");

                    colorballs.get(0).setX((int) colorballs.get(0).getX() + diffX);
                    colorballs.get(0).setY((int) colorballs.get(0).getY() + diffY);
                    colorballs.get(1).setX((int) colorballs.get(1).getX() + diffX);
                    colorballs.get(1).setY((int) colorballs.get(1).getY() + diffY);
                    colorballs.get(2).setX((int) colorballs.get(2).getX() + diffX);
                    colorballs.get(2).setY((int) colorballs.get(2).getY() + diffY);
                    colorballs.get(3).setX((int) colorballs.get(3).getX() + diffX);
                    colorballs.get(3).setY((int) colorballs.get(3).getY() + diffY);
                    drawStrokedRect(canvas, point1.x, point3.y, point3.x, point1.y,
                            strokeColorString, fillColorString);
                    invalidate();
                }

                break;

            case MotionEvent.ACTION_UP:
                // touch drop - just do things here after dropping
                isInsideTouch = false;
                break;
        }
        // redraw the canvas
        invalidate();
        return true;
    }
}