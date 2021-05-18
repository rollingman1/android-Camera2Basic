package com.example.android.camera2basic;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;

public class ColorBall {

    Bitmap bitmap;
    Context mContext;
    Point point;
    int id;
    static int count = 0;
    int screenWidth, screenHeight;

    public ColorBall(Context context, int resourceId, Point point, int screenWidth, int screenHeight) {
        this.id = count++;
        System.out.println("Created ColorBall : id " + id + " x " + point.x + ", y " + point.y);
        bitmap = BitmapFactory.decodeResource(context.getResources(),
                resourceId);
        mContext = context;
        this.screenHeight = screenHeight;
        this.screenWidth = screenWidth;
        this.point = point;
    }

    public int getWidthOfBall() {
        return bitmap.getWidth();
    }

    public int getHeightOfBall() {
        return bitmap.getHeight();
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public int getX() {
        return point.x;
    }

    public int getY() {
        return point.y;
    }

    public int getID() {
        return id;
    }

    public void setX(int x) {
        if (x < 0) {
            point.x = 0;
        } else if (x >= screenWidth - getWidthOfBall()) {
            point.x = screenWidth - getWidthOfBall();
        } else point.x = x;
    }

    public void setY(int y) {
        if (y < 0) {
            point.y = 0;
        } else if (y >= screenHeight - getHeightOfBall()) {
            point.y = screenHeight - getHeightOfBall();
        } else point.y = y;
    }
}

