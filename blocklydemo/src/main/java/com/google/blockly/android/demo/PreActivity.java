package com.google.blockly.android.demo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

public class PreActivity extends AppCompatActivity {

    private static Context mContext;
    private Handler mHandler;

        @Override
        protected void onCreate(Bundle savedInstanceState){
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_pre);
            mHandler = new Handler();
            mContext=this;


        }
    }
