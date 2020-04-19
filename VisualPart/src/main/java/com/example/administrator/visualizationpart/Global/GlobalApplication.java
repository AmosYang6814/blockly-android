package com.example.administrator.visualizationpart.Global;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;


import com.example.administrator.visualizationpart.R;
import com.example.administrator.visualizationpart.Tools.ActivityTools;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.impl.AttachListPopupView;
import com.lxj.xpopup.interfaces.OnSelectListener;
import com.yhao.floatwindow.FloatWindow;
import com.yhao.floatwindow.MoveType;
import com.yhao.floatwindow.PermissionListener;

import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;
import javax.xml.parsers.ParserConfigurationException;

import GlobalTools.DataBean.Screen;
import GlobalTools.FunctionClassXMLParaser;
import GlobalTools.GlobalManager;
import GlobalTools.Perproties;
import UI.ScreenStatus.ScreenOprate;

public class GlobalApplication extends Application implements Perproties {

    public static  final  boolean Debug=true;
    static View view = null;
    public static AttachListPopupView attachListPopupView;

    public Handler handler=new Handler(){

    };
    @Override
    public void onCreate() {
        super.onCreate();

        InitFunctionClass();
        registerToGlobalManager();
        //申请全局的悬浮窗权限

        menu();
    }

    /**
     * 将自己注册到GlobalManager中
     */
    @Override
    public void registerToGlobalManager() {
        GlobalManager.registerPerproties(this);
    }

    /**
     * 获取简单模块定义的文件流
     * @return
     */
    @Override
    public InputStream getSimpleComponentDefineFile() {
        try {
            return getResources().getAssets().open("SimpleProperties.xml");
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 初始化功能配置类
     */
    public void InitFunctionClass(){
        try {
            InputStream inputStream= getResources().getAssets().open("functionClassProperties.xml");
            FunctionClassXMLParaser.parser(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
    }

    private void menu(){
        view=LayoutInflater.from(this).inflate(R.layout.menu,null);

        FloatWindow
                .with(getApplicationContext())
                .setView(view)
                .setWidth(100)                               //设置控件宽高
                .setHeight(100)
                .setX(100)                                   //设置控件初始位置
                .setY(100)
                .setDesktopShow(false)                        //桌面显示
                .setMoveType(MoveType.inactive)
                .setPermissionListener(new PermissionListener() {
                    @Override
                    public void onSuccess() {

                    }
                    @Override
                    public void onFail() {
                        Toast.makeText(GlobalApplication.this,"未开启悬浮窗权限",Toast.LENGTH_LONG).show();
                    }
                })  //监听权限申请结果
                .build();
    }


    static Context lastContext=null;
    public static void DisplayMainMenu(final Context context){
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(context,"点击菜单",Toast.LENGTH_LONG).show();
                XPopup.Builder builder=new XPopup.Builder(context).atView(view);

               if(attachListPopupView!=null && attachListPopupView.isShow())return;

                 attachListPopupView=builder.asAttachList(new String[]{"新建项目","新建页面","新建逻辑代码"}, null,-100,0, new OnSelectListener() {
                    @Override
                    public void onSelect(int position, String text) {
                        if(position==1){
                            ActivityTools.getInstance().AddNewContent(null,null,(AppCompatActivity) context);
                        }

                        if(position==2){
                            Intent intent=new Intent("com.example.blockly.AndroidActivity");
                            context.startActivity(intent);
                        }
                    }
                });
                attachListPopupView.show();
                lastContext=context;
            }
        });
        FloatWindow.get().show();
    }


}
