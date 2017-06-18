package com.nipunbirla.boxloader;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;

import com.nipunbirla.boxloaderlib.R;

/**
 * Created by Nipun on 6/14/2017.
 */

public class BoxLoaderView extends View {

    private static final int FRAME_RATE = 2;
    private static final int DEFAULT_SPEED = 10;
    private static final int DEFAULT_STROKE_WIDTH = 20;
    private static final int DEFAULT_STROKE_COLOR = Color.WHITE;
    private static final int DEFAULT_LOADER_COLOR = Color.BLUE;

    private int speed;
    private int strokeWidth;
    private int strokeColor, loaderColor;
    private boolean dirChange = false;
    private Box box, outBox;
    private Handler handler;


    public BoxLoaderView(Context context) {
        super(context);
        init(context, null);
    }

    public BoxLoaderView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs){
        handler = new Handler();

        if(attrs != null) {
            TypedArray a = context.obtainStyledAttributes(attrs,
                    R.styleable.Options, 0, 0);
            strokeColor = a.getColor(R.styleable.Options_strokeColor, DEFAULT_STROKE_COLOR);
            loaderColor = a.getColor(R.styleable.Options_loaderColor, DEFAULT_LOADER_COLOR);
            strokeWidth = a.getInt(R.styleable.Options_strokeWidth, DEFAULT_STROKE_WIDTH);
            speed = a.getInt(R.styleable.Options_speed, DEFAULT_SPEED);
            a.recycle();
        } else {
            strokeColor = DEFAULT_STROKE_COLOR;
            loaderColor = DEFAULT_LOADER_COLOR;
            strokeWidth = DEFAULT_STROKE_WIDTH;
            speed = DEFAULT_SPEED;
        }
    }

    public void setSpeed(int speed){
        this.speed = speed;
    }

    public void setStrokeWidth(int strokeWidth){
        this.strokeWidth = strokeWidth;
    }

    public void setStrokeColor(int color){
        strokeColor = color;
    }

    public void setLoaderColor(int color){
        loaderColor = color;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if(outBox == null){
            outBox = new Box(0,0,canvas.getWidth(), canvas.getHeight(), strokeColor, 10);
            outBox.getPaint().setStrokeWidth(strokeWidth);
        }
        canvas.drawRect(outBox.getLeft(), outBox.getTop(), outBox.getRight(), outBox.getBottom(), outBox.getPaint());
        if(box == null){
            box = new Box(strokeWidth, strokeWidth, canvas.getWidth()/2 - strokeWidth, canvas.getHeight()/2 - strokeWidth, loaderColor, 10);
            box.setDx(speed);
            box.setDy(speed);
        }
        dirChange = box.bounce(canvas, strokeWidth);
        rectifyBoundaries(canvas, box);
        canvas.drawRect(box.getLeft(), box.getTop(), box.getRight(), box.getBottom(), box.getPaint());
        handler.postDelayed(r, dirChange ? FRAME_RATE * 20 : FRAME_RATE);
    }

    private void rectifyBoundaries(Canvas canvas, Box box) {
        if(box.getLeft() < strokeWidth){
            box.getrect().left = strokeWidth;
        }
        if(box.getTop() < strokeWidth){
            box.getrect().top = strokeWidth;
        }
        if(box.getRight() > canvas.getWidth() - strokeWidth){
            box.getrect().right = canvas.getWidth() - strokeWidth;
        }
        if(box.getBottom() > canvas.getHeight() - strokeWidth){
            box.getrect().bottom = canvas.getHeight() - strokeWidth;
        }
    }

    private Runnable r = new Runnable() {
        @Override
        public void run() {
            invalidate();
        }
    };

    private static class Box{
        private int c,r,dx,dy, dir;
        private Rect rect;
        private Paint paint;

        //dir : 0 : right, 1 : down, 2 : left, 3 : up

        public Box(int left, int top, int right, int bottom, int color, int radius){
            rect = new Rect(left, top, right, bottom);
            c = color;
            r = radius;
            paint = new Paint();
            paint.setColor(c);
            dx = 0;
            dy = 0;
            dir = 0;
        }

        public void setColor(int col){
            c = col;
        }

        public void goTo(int l, int t, int r, int b){
            rect.left = l;
            rect.top = t;
            rect.right = r;
            rect.bottom = b;
        }

        public void setDx(int speed){
            dx = speed;
        }

        public void setDy(int speed){
            dy = speed;
        }

        public int getLeft(){
            return rect.left;
        }

        public int getTop(){
            return rect.top;
        }

        public int getRight(){
            return rect.right;
        }

        public int getBottom(){
            return rect.bottom;
        }

        public int getRadius(){
            return r;
        }

        public Paint getPaint(){
            return paint;
        }

        public void increaseRight(){
            rect.right += dx;
        }

        public void decreaseRight(){
            rect.right -= dx;
        }

        public void increaseLeft(){
            rect.left += dx;
        }

        public void decreaseLeft(){
            rect.left -= dx;
        }

        public void increaseTop(){
            rect.top += dy;
        }

        public void decreaseTop(){
            rect.top -= dy;
        }

        public void increaseBottom(){
            rect.bottom += dy;
        }

        public void decreaseBottom(){
            rect.bottom -= dy;
        }

        public Rect getrect(){
            return rect;
        }

        //Bounce of edge
        public boolean bounce(Canvas canvas, int strokeWidth){
            switch (dir){
                case 0:
                    if(rect.right < canvas.getWidth() - strokeWidth){
                        increaseRight();
                    } else {
                        increaseLeft();
                        if(rect.left > canvas.getWidth()/2){
                            dir++;
                            return true;
                        }
                    }
                    break;
                case 1:
                    if(rect.bottom < canvas.getHeight() - strokeWidth){
                        increaseBottom();
                    } else {
                        increaseTop();
                        if(rect.top > canvas.getHeight()/2){
                            dir++;
                            return true;
                        }
                    }
                    break;
                case 2:
                    if(rect.left > strokeWidth){
                        decreaseLeft();
                    } else {
                        decreaseRight();
                        if(rect.right < canvas.getWidth()/2){
                            dir++;
                            return true;
                        }
                    }
                    break;
                case 3:
                    if(rect.top > strokeWidth){
                        decreaseTop();
                    } else {
                        decreaseBottom();
                        if(rect.bottom < canvas.getHeight()/2){
                            dir = 0;
                            return true;
                        }
                    }
                    break;
            }
            return false;
        }
    }

}
