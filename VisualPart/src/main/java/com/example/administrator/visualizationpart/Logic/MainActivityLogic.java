package com.example.administrator.visualizationpart.Logic;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.view.View;

import com.example.administrator.visualizationpart.Activity.ContentFragment;
import com.example.administrator.visualizationpart.R;
import com.example.administrator.visualizationpart.Tools.ActivityTools;
import com.hb.dialog.myDialog.MyAlertInputDialog;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.logging.Handler;

import DataPersistence.DataBean.Component.SimpleComponent;
import GlobalTools.DataBean.Action.Action;
import GlobalTools.DataBean.Action.EventType;
import GlobalTools.DataBean.Action.Screenlink;
import GlobalTools.DataBean.Attribute;
import GlobalTools.DataBean.Screen;
import GlobalTools.DataBean.UiComponent;
import UI.EvenHanding.BuilderAction;
import UI.EvenHanding.EvenManager;
import UI.UICenterCtrol.UIGlobalManager;


/**
 * 负责逻辑代码的生成
 */
public class MainActivityLogic {

    private UiComponent lastCreateAction=null;
    private int eventAction=0;

    public UiComponent InitAndSetAttribute(Context context, UiComponent simpleComponent){

        View newView= ActivityTools.CreateView(context,simpleComponent.getComponentClass());
        simpleComponent.setComponentObj(newView);

        //设置属性的值
        try {
            for(int i=0;i<simpleComponent.getAllDefineAttribute().size();i++){
                Attribute attribute=simpleComponent.getAllDefineAttribute().get(i);
                Method method=null;
                Class temp1,temp2,temp3;
                switch (attribute.getNumber()){
                    case 1:
                         temp1=Class.forName(attribute.getMethodParameterClass()[0]);
                        method=simpleComponent.getComponentClass().getMethod(attribute.getReflectMethod(),temp1);
                        method.invoke(newView,attribute.getValues()[0]);
                        break;
                    case 2:
                        temp1=Class.forName(attribute.getMethodParameterClass()[0]);
                        temp2=Class.forName(attribute.getMethodParameterClass()[1]);
                        method=simpleComponent.getComponentClass().getMethod(attribute.getReflectMethod(),attribute.getValues()[0].getClass(),temp1,temp2);
                        method.invoke(newView,attribute.getValues()[0],attribute.getValues()[1]);
                        break;
                    case 3:
                        temp1=Class.forName(attribute.getMethodParameterClass()[0]);
                        temp2=Class.forName(attribute.getMethodParameterClass()[1]);
                        temp3=Class.forName(attribute.getMethodParameterClass()[2]);
                        method=simpleComponent.getComponentClass().getMethod(attribute.getReflectMethod(),temp1,temp2,temp3);
                        method.invoke(newView,attribute.getValues()[0],attribute.getValues()[1],attribute.getValues()[2]);

                }

            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        return simpleComponent;
    }


    /**
     * 添加动作
     * @param preName
     * @param nextName
     */
    public void AddAction(String preName,String nextName){
        UIGlobalManager.getEvenManager().getBuilderAction().builderJumpEvent(preName,
                nextName,lastCreateAction,EventType.getActionTypeByIndex(eventAction));
        this.ClearAttachAction();
    }


    /**
     * 关联点击事件
     * @param uiComponent
     * @param eventAction
     */
    public void attachAddAction(UiComponent uiComponent,int eventAction){
        this.lastCreateAction=uiComponent;
        this.eventAction=eventAction;
    }

    public void ClearAttachAction(){
        this.lastCreateAction=null;
    }

}
