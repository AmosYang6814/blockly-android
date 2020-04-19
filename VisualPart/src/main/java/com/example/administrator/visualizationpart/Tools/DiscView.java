package com.example.administrator.visualizationpart.Tools;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v4.content.LocalBroadcastManager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

public class DiscView extends View {

    private Paint paint;
    private int btnX;
    private int btnY;
    //内外圆半径
    private int radiusBack;
    private int radiusPre;
    //外圆圆心
    private float circleX;
    private float circleY;

    //广播action
    public static final String DISC_BROADCAST = "com.example.adminstrator.visualizationpart.DISCVIEW";
    private LocalBroadcastManager manager;


    public DiscView(Context context) {
        this(context,null);
    }

    public DiscView(Context context,AttributeSet attrs) {
        this(context, attrs, 0 );
    }

    public DiscView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        manager = LocalBroadcastManager.getInstance(context);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.TRANSPARENT);

        //设置背景图的半径
        radiusBack = getWidth() / 3;
        //设置小圆点的半径
        radiusPre = getWidth() / 8;
        //圆心
        circleX = getWidth() / 2;
        circleY = getHeight() / 2;

        if (paint == null) {
            paint = new Paint();
        }
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(0x7f111111);
        canvas.drawCircle(circleX,circleY,(float) radiusBack,paint);
        if (btnX <= 0 && btnY <= 0){
            paint.setColor(0xFFF10159);
            canvas.drawCircle(circleX,circleY,(float) radiusPre,paint);
            canvas.save();
            return;
        }
        paint.setColor(0xFF744041);
        canvas.drawCircle(btnX,btnY,(float) radiusPre,paint);
        canvas.save();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_MOVE:
                System.out.println(event.getX() + " " + event.getY());
                btnX = (int) event.getX();
                btnY = (int) event.getY();
                if ((btnY - circleY)*(btnY - circleY) + (btnX - circleX)*(btnX - circleX) >= radiusBack * radiusBack){
                    changeBtnLocation();
                }
                this.invalidate();
                break;
            case MotionEvent.ACTION_UP:
                System.out.println(event.getX() + " " + event.getY() + "TAG");
                //发广播
                sendAction(btnX,btnY);
                btnX = 0;
                btnY = 0;
                this.invalidate();
                break;
        }

        return true;
    }



    private void sendAction(int x,int y) {
        Intent intent = new Intent(DISC_BROADCAST);
        intent.putExtra("move",(x - circleX) + " " + (y - circleY));
        manager.sendBroadcast(intent);
    }

    /**
     * 这里会用到数学知识
     */
    private void changeBtnLocation() {
        //相似比
        double similarity = Math.sqrt((radiusBack * radiusBack) / ((btnY - circleY)*(btnY - circleY) + (btnX - circleX)*(btnX - circleX)));
        btnX = (int) ((btnX - circleX)*similarity + circleX);
        btnY = (int) ((btnY - circleY)*similarity + circleY);
    }

}
