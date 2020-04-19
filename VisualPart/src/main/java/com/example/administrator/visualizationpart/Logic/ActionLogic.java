package com.example.administrator.visualizationpart.Logic;

import android.app.Fragment;

import java.util.List;

import GlobalTools.DataBean.Action.EventType;
import GlobalTools.DataBean.UiComponent;
import UI.EvenHanding.BuilderAction;

public class ActionLogic implements BuilderAction<Fragment> {
    @Override
    public void builderAttributeChage(String screenName, UiComponent uiComponent, UiComponent uiComponentChange, EventType eventType) {

    }

    @Override
    public void builderJumpEvent(String current, String next, UiComponent uiComponent, EventType eventType) {

    }

    @Override
    public void builderwakeModule(String screenName, UiComponent uiComponent, EventType eventType) {

    }
}
