package GlobalTools.DataBean.Action;

/**
 * 描述动作。描述动作，从何来，什么方式，
 */
public  class Action {
    public final static int ACTIONTYPE_MEAN_JUMP=171;
    public final static int ACTIONTYPE_MEAN_ACTIVE=172;
    public final static int ACTIONTYPE_MEAN_CHANGE=173;

    private int actionId=-1;
    private int ScreenId;
    private int ComponentId;
    protected int classType=0;

    public int getRelationType() {
        return classType;
    }

    public void setRelationType(int classType) {
        this.classType = classType;
    }

    private EventType action;/*动作的记录*/
    private ActionMean actionMean;/*动作的含义*/

    public int getScreenId() {
        return ScreenId;
    }

    public void setScreenId(int screenId) {
        ScreenId = screenId;
    }

    public int getComponentId() {
        return ComponentId;
    }

    public void setComponentId(int componentId) {
        ComponentId = componentId;
    }

    public EventType getAction() {
        return action;
    }

    public void setAction(EventType action) {
        this.action = action;
    }

    public ActionMean getActionMean() {
        return actionMean;
    }

    public void setActionMean(ActionMean actionMean) {
        this.actionMean = actionMean;
    }

    public Action(int screenId, int componentId, EventType action, ActionMean actionMean) {
        ScreenId = screenId;
        ComponentId = componentId;
        this.action = action;
        this.actionMean = actionMean;
    }

    /**
     * 拷贝构造方法
     * @param action
     */
    public Action(Action action){
        this.ScreenId=action.getScreenId();
        this.ComponentId=action.getComponentId();
        this.action=action.getAction();
        this.actionMean=action.getActionMean();
    }

    public int getActionId() {
        return actionId;
    }

    public void setActionId(int actionId) {
        this.actionId = actionId;
    }



}
