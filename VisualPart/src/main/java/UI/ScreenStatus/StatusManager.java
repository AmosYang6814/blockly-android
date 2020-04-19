package UI.ScreenStatus;

import java.util.HashMap;

import GlobalTools.DataBean.Action.Action;

//状态管理器
public class StatusManager {

    /**
     * 按照屏幕的id存储，该屏幕上所有组件的状态
     */
    HashMap<Integer, Action> actionMap=new HashMap<>();

}
