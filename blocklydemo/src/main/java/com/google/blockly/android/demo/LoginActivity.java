package com.google.blockly.android.demo;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import com.google.blockly.service.NotificationService;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    private static Context mContext;
    private Handler mHandler;
    private Button btn_login;
    private Button btn_notification;
    private ListView listview;

    private NotificationManager mNM;
    public void bindView()
    {
        btn_login=findViewById(R.id.btn_login);
        btn_notification=findViewById(R.id.btn_notification);
        btn_login.setOnClickListener(this);
        btn_notification.setOnClickListener(this);

        listview = findViewById(R.id.my_list);
        listview.setAdapter(new myAdapter());
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Log.i("lyt", "click:" + i);
                Intent intent = new Intent(mContext, AndroidActivity.class);
                intent.putExtra("screen_id", i);
                mContext.startActivity(intent);
            }
        });
    }

    private class myAdapter extends android.widget.BaseAdapter{

        @Override
        public int getCount() {
            return 4;
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            View v;
            switch (i){
                case 1:
                    v = View.inflate(LoginActivity.this,R.layout.my_item_2,null);
                    break;
                case 2:
                    v = View.inflate(LoginActivity.this,R.layout.my_item_3,null);
                    break;
                case 3:
                    v = View.inflate(LoginActivity.this,R.layout.my_item_4,null);
                    break;
                default:
                    v = View.inflate(LoginActivity.this,R.layout.my_item,null);
                    break;

            }
            return v;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mHandler = new Handler();
        mContext=this;

        bindView();



    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.btn_login) {
            mContext.startActivity(new Intent(mContext,AndroidActivity.class));
        }else if(id == R.id.btn_notification){
            Intent intent = new Intent(mContext, NotificationService.class);
            startService(intent);
            Log.i("lyt", "service start");
        }
    }


}
