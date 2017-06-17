package com.nipunbirla.boxloader;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;

import com.nipunbirla.boxloaderlib.R;

/**
 * Created by Nipun on 6/14/2017.
 */

public class BoxLoaderView extends View {

    private static final int FRAME_RATE = 10;
    private static final int DEFAULT_SPEED = 20;
    private static final int STROKE_WIDTH = 20;
    private static final int INNER_MARGIN = 20;

    private boolean dirChange = false;
    private Paint paint;
    private Box box, outBox;
    private Handler handler;


    public BoxLoaderView(Context context) {
        super(context);
        init(context);
    }

    public BoxLoaderView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public BoxLoaderView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context){
        handler = new Handler();
        paint = new Paint();
        paint.setColor(Color.BLUE);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if(outBox == null){
            outBox = new Box(0,0,canvas.getWidth(), canvas.getHeight(), Color.WHITE, 10);
            outBox.getPaint().setStrokeWidth(STROKE_WIDTH);
        }
        canvas.drawRect(outBox.getLeft(), outBox.getTop(), outBox.getRight(), outBox.getBottom(), outBox.getPaint());
        if(box == null){
            box = new Box(STROKE_WIDTH,STROKE_WIDTH, canvas.getWidth()/2 - STROKE_WIDTH, canvas.getHeight()/2 - STROKE_WIDTH, Color.BLUE, 10);
            box.setDx(DEFAULT_SPEED);
            box.setDy(DEFAULT_SPEED);
        }
        dirChange = box.bounce(canvas);
        canvas.drawRect(box.getLeft(), box.getTop(), box.getRight(), box.getBottom(), box.getPaint());
        handler.postDelayed(r, dirChange ? FRAME_RATE * 20 : FRAME_RATE);
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

        //Bounce of edge
        public boolean bounce(Canvas canvas){
            switch (dir){
                case 0:
                    if(rect.right <= canvas.getWidth() - STROKE_WIDTH){
                        increaseRight();
                    } else {
                        increaseLeft();
                        if(rect.left >= canvas.getWidth()/2 - STROKE_WIDTH){
                            dir++;
                            return true;
                        }
                    }
                    break;
                case 1:
                    if(rect.bottom <= canvas.getHeight() - STROKE_WIDTH){
                        increaseBottom();
                    } else {
                        increaseTop();
                        if(rect.top >= canvas.getHeight()/2 - STROKE_WIDTH){
                            dir++;
                            return true;
                        }
                    }
                    break;
                case 2:
                    if(rect.left >= STROKE_WIDTH){
                        decreaseLeft();
                    } else {
                        decreaseRight();
                        if(rect.right <= canvas.getWidth()/2 - STROKE_WIDTH){
                            dir++;
                            return true;
                        }
                    }
                    break;
                case 3:
                    if(rect.top >= STROKE_WIDTH){
                        decreaseTop();
                    } else {
                        decreaseBottom();
                        if(rect.bottom <= canvas.getHeight()/2 - STROKE_WIDTH){
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
