package com.example.administrator.visualizationpart.FunctionCLass;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.example.administrator.visualizationpart.Activity.FrameActivity;
import com.example.administrator.visualizationpart.Global.GlobalApplication;

import FileManager.logicStatesOpatrate;

public class androidlogicStatesOprate implements logicStatesOpatrate  {
    @Override
    public void NotifiedLogicStatesChaged(String addName) {
        if(GlobalApplication.Debug){
            Log.i("屏幕状态日志","屏幕数量变化");
        }

        Context context= FrameActivity.context;
        if(context==null)return;

        LocalBroadcastManager localBroadcastManager=LocalBroadcastManager.getInstance(context);

        Intent intent=new Intent();
        intent.setAction(FrameActivity.ADD_LOGIC);
        intent.putExtra("Data",addName);
        localBroadcastManager.sendBroadcast(intent);
    }
}
