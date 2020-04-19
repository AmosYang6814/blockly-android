package com.google.blockly.service;

import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;


public class NotificationService extends Service {
    private final static String TAG = "NotificationService";

    public final static String ACTION_SEND_PROGRESS_NOTIFICATION = "com.android.peter.notificationdemo.ACTION_SEND_PROGRESS_NOTIFICATION";

    private Context mContext;
    private NotificationManager mNM;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
        mNM = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        Log.i(TAG,"onCreate() exec.");

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(TAG,"out : onStartCommand action = " + intent.getAction());
        dealWithActionSendProgressNotification();
        return START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.i(TAG,"onBind() exec.");
        return null;
    }



    private void dealWithActionSendProgressNotification() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                for(int i=0 ; i<=100 ; i++) {
                    //Notificaitons.getInstance().sendProgressViewNotification(mContext,mNM,i);
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

}