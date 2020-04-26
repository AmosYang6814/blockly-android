package com.example.administrator.visualizationpart.Logic;

import android.app.Fragment;
import android.util.Log;

import com.example.administrator.visualizationpart.Global.GlobalApplication;

import java.util.List;

import GlobalTools.DataBean.Action.Action;
import GlobalTools.DataBean.Action.ActionMean;
import GlobalTools.DataBean.Action.ChangeLink;
import GlobalTools.DataBean.Action.EventType;
import GlobalTools.DataBean.Action.Screenlink;
import GlobalTools.DataBean.UiComponent;
import UI.EvenHanding.BuilderAction;
import UI.UICenterCtrol.UIGlobalManager;

public class ActionLogic implements BuilderAction<Fragment> {
    @Override
    public void builderAttributeChage(String screenName, UiComponent uiComponent, UiComponent uiComponentChange, EventType eventType) {

        if(GlobalApplication.Debug){
            Log.i("Action日志","添加属性改变");
        }

        Action action=new Action(screenName,uiComponent.getUUID(),eventType, ActionMean.ACTION_MEAN_CHANGE_ATTRIBUTE);
        ChangeLink changeLink=new ChangeLink(action);
        changeLink.addStatusNote(uiComponentChange);
        UIGlobalManager.getEvenManager().addAction(screenName,changeLink);
    }

    @Override
    public void builderJumpEvent(String current, String next, UiComponent uiComponent, EventType eventType) {

        if(GlobalApplication.Debug){
            Log.i("Action日志","添加界面跳转操作");
        }

        Action action=new Action(current,uiComponent.getUUID(),eventType,ActionMean.ACTION_MEAN_JUMP_TO_ANOTHER);
        Screenlink screenlink=new Screenlink(current,next,action);
        UIGlobalManager.getEvenManager().addAction(current,screenlink);
    }

    @Override
    public void builderwakeModule(String screenName, UiComponent uiComponent, EventType eventType) {

    }
}
