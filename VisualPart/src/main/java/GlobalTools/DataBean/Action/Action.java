package GlobalTools.DataBean.Action;

/**
 * 描述动作。描述动作，从何来，什么方式，
 */
public  class Action {
    public final static int ACTIONTYPE_MEAN_JUMP=171;
    public final static int ACTIONTYPE_MEAN_ACTIVE=172;
    public final static int ACTIONTYPE_MEAN_CHANGE=173;

    private int actionId=-1;
    private String ScreenName;
    private String  ComponentId;
    protected int classType=0;

    public int getRelationType() {
        return classType;
    }

    public void setRelationType(int classType) {
        this.classType = classType;
    }

    private EventType action;/*动作的记录*/
    private ActionMean actionMean;/*动作的含义*/

    public String getScreenId() {
        return ScreenName;
    }

    public void setScreenId(String screenId) {
        ScreenName = screenId;
    }

    public String getComponentId() {
        return ComponentId;
    }

    public void setComponentId(String componentId) {
        ComponentId = componentId;
    }

    public EventType getEvent() {
        return action;
    }

    public void setEvent(EventType action) {
        this.action = action;
    }

    public ActionMean getActionMean() {
        return actionMean;
    }

    public void setActionMean(ActionMean actionMean) {
        this.actionMean = actionMean;
    }

    public Action(String screenId, String componentId, EventType action, ActionMean actionMean) {
        ScreenName= screenId;
        ComponentId = componentId;
        this.action = action;
        this.actionMean = actionMean;
    }

    /**
     * 拷贝构造方法
     * @param action
     */
    public Action(Action action){
        this.ScreenName=action.getScreenId();
        this.ComponentId=action.getComponentId();
        this.action=action.getEvent();
        this.actionMean=action.getActionMean();
    }

    public int getActionId() {
        return actionId;
    }

    public void setActionId(int actionId) {
        this.actionId = actionId;
    }



}
