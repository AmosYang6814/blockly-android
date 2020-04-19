package com.example.administrator.visualizationpart.Activity;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ExpandableListView;

import com.example.administrator.visualizationpart.Adapter.MyAdapter;
import com.example.administrator.visualizationpart.Global.GlobalApplication;
import com.example.administrator.visualizationpart.R;
import com.example.administrator.visualizationpart.Tools.ActivityTools;
import com.tbruyelle.rxpermissions.RxPermissions;
import com.yhao.floatwindow.FloatWindow;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import GlobalTools.DataBean.UiComponent;
import UI.ComponentIndex.AbstractDataManager;
import UI.UICenterCtrol.UIGlobalManager;
import rx.functions.Action1;


public class FrameActivity extends AppCompatActivity {
    public static String ADD_PAGE="com.example.VisualLizationPart_AddPage";
    public static String ADD_LOGIC="com.example.VisualLizationPart_AddLogic";
    public  final static int CREATE_NEW_FRAGMENT=12;
    public  final static int CANCEL_CREATE_FRAGMENT=13;

    private DrawerLayout drawerLayout;
    private ConstraintLayout left;
    private ExpandableListView fileList;
    private MyAdapter adapter;

    final public String[] fileClass={"界面","逻辑代码","变量"};
    public static ArrayList<ArrayList<String>> arrayLists=new ArrayList<>();

    public static Context context=null;

    public Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    };


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        context=this;

        setContentView(R.layout.frame_layout);
        getPermisstion();
        /**
         * 设置数据
         */


        /**
         * 初始化组件
         */

        initFileList();
        initView();
        initReceiver();
        RegitsterClickToFragment();
        if(FloatWindow.get()!=null)GlobalApplication.DisplayMainMenu(this);



    }

    /**
     * 初始化文件结构
     */
    private void initFileList(){
        ArrayList<String> apage=new ArrayList<>();
        ArrayList<String> logic=new ArrayList<>();
        ArrayList<String> parameter=new ArrayList<>();
        arrayLists.add(apage);
        arrayLists.add(logic);
        arrayLists.add(parameter);
    }


    /**
     * 初始化控件
     */
    private void initView(){

        if(GlobalApplication.Debug){
            Log.i("MainActivity","初始化控件");
        }
        drawerLayout=findViewById(R.id.Frame_drawerLayout);
        left=findViewById(R.id.FilePanel);

        fileList=findViewById(R.id.FileList);

        adapter=new MyAdapter(handler,this,fileClass,arrayLists);
        fileList.setAdapter(adapter);

    }


    /**
     * 申请权限
     */
    private void  getPermisstion(){

        if(RxPermissions.getInstance(this).isGranted(Manifest.permission.WRITE_EXTERNAL_STORAGE))return;

        System.out.println("未打开写权限");

        RxPermissions.getInstance(FrameActivity.this)
                .request(Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE)//这里填写所需要的权限多个的话可以逗号隔开
                .subscribe(new Action1<Boolean>() {
                    @Override
                    public void call(Boolean aBoolean) {
                        if (aBoolean) {//true表示获取权限成功（注意这里在android6.0以下默认为true）
                            Log.i("permissions",  "权限：获取成功"  );
                        } else {//表示权限被拒绝
                            Log.i("permissions",  "权限：获取失败"  );
                        }
                    }
                });
    }


    /**
     * 通过本地广播添加子项目
     */

    private void initReceiver(){
        //获取实例
        LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance(this);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ADD_PAGE);
        intentFilter.addAction(ADD_LOGIC);

        FileReceiver fileReceiver = new FileReceiver();
        //绑定
        localBroadcastManager.registerReceiver(fileReceiver,intentFilter);
    }


    /**
     * 文件广播处理内部类
     */
    class FileReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {

            Log.i("文件系统日志","收到："+intent.getAction());
            if(intent.getAction().equals(ADD_PAGE)){
                adapter.getmItemList().get(0).add(intent.getStringExtra("Data"));
                adapter.notifyDataSetChanged();

                if(fileList.isGroupExpanded(0))fileList.collapseGroup(0);
                fileList.expandGroup(0);
                fileList.collapseGroup(0);

            }else if(intent.getAction().equals(ADD_LOGIC)){
                adapter.getmItemList().get(1).add(intent.getStringExtra("Data"));
                adapter.notifyDataSetChanged();
            }
        }
    }


    /**
     * 注册文件列表的点击跳转情况
     */
    private void RegitsterClickToFragment(){
        fileList.setOnChildClickListener(new ExpandableListView.OnChildClickListener(){

            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {

                if (groupPosition == 0) {
                    String name = adapter.getmItemList().get(groupPosition).get(childPosition);
                    FragmentManager fm = getSupportFragmentManager();
                    FragmentTransaction tx = fm.beginTransaction();
                    List<Fragment> fragments = fm.getFragments();
                    for (Fragment fragment : fragments) {
                        if (fragment.isAdded() && fragment.isVisible()) tx.hide(fragment);
                    }
                    tx.show(fm.findFragmentByTag(name));
                }
                else if(groupPosition==1){
                    Intent intent=new Intent("com.example.blockly.AndroidActivity");
                    intent.putExtra("screen_id",adapter.getmItemList().get(groupPosition).get(childPosition));
                    FrameActivity.this.startActivity(intent);
                }
                return true;
            }

        });
    }


    /**
     * 获取当前有多少逻辑片段
     * @return
     */
    public static int getLogicSize(){
        return arrayLists.get(1).size();
    }


}
