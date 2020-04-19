package DataPersistence.DataBean.Component;

/**
 * Created by Administrator on 2020/2/1.
 */

/**
 * describle the component Action
 */
public class Action {
    final static int ACTION_CLICK=12;
    final static int ACTION_FOCUS_GET=13;
    final static int ACTION_FOCUS_LOSE=14;
    final static int ACTION_LONG_CLICK=15;
    final static int ACTION_INPUT_BEFORE=16;
    final static int ACTION_INPUT_AFTER=17;

    int id;
    boolean isSetting=false;
    int actionType;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isSetting() {
        return isSetting;
    }

    public void setSetting(boolean setting) {
        isSetting = setting;
    }

    public int getActionType() {
        return actionType;
    }

    public void setActionType(int actionType) {
        this.actionType = actionType;
    }
}