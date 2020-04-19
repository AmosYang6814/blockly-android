package com.example.administrator.visualizationpart.Tools;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.administrator.visualizationpart.Activity.ContentFragment;
import com.example.administrator.visualizationpart.Activity.FrameActivity;
import com.example.administrator.visualizationpart.R;
import com.hb.dialog.myDialog.MyAlertInputDialog;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import GlobalTools.DataBean.Action.EventType;
import GlobalTools.DataBean.Screen;
import UI.UICenterCtrol.UIGlobalManager;

public class ActivityTools {

    public static ActivityTools getInstance(){
        return new ActivityTools();
    }

    public static View CreateView(Context context, Class currentClass){
        try {
            Constructor constructor=currentClass.getConstructor(Context.class);
            View view=(View) constructor.newInstance(context);
            return view;
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        return null;
    }


    /**
     * 添加新的页面
     * @param activity
     */
    public  void AddNewContent(final Handler handler, final Fragment lastfragment, final AppCompatActivity activity){



        final Screen<Fragment>[] result = new Screen[]{null};

        final MyAlertInputDialog myAlertInputDialog = new MyAlertInputDialog(activity).builder()
                .setTitle("请输入")
                .setEditText("");
        myAlertInputDialog.setPositiveButton("确认", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myAlertInputDialog.dismiss();
                ContentFragment temp1=new ContentFragment();
                FragmentManager fm=activity.getSupportFragmentManager();
                FragmentTransaction tx=fm.beginTransaction();
                tx.add(R.id.FramMain,temp1,myAlertInputDialog.getResult());
                tx.commit();
                temp1.setName(myAlertInputDialog.getResult());
                if(lastfragment!=null) tx.hide(lastfragment);
                tx.show(temp1);
                result[0] =new Screen<Fragment>(myAlertInputDialog.getResult(),temp1);
                UIGlobalManager.getScreenNumberManager().addScreen(result[0]);

                if(handler==null)return;
                Message message=new Message();
                message.what= FrameActivity.CREATE_NEW_FRAGMENT;
                Bundle b=new Bundle();
                b.putString("Name",myAlertInputDialog.getResult());
                message.setData(b);
                handler.sendMessage(message);
            }
        }).setNegativeButton("取消", new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                myAlertInputDialog.dismiss();

                if(handler==null)return;

                Message message=new Message();
                message.what= FrameActivity.CANCEL_CREATE_FRAGMENT;
                handler.sendMessage(message);
            }
        });

        myAlertInputDialog.show();
        return ;
    }

}
