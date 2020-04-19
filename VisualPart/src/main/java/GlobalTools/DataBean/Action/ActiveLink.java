package GlobalTools.DataBean.Action;

public class ActiveLink extends Action {
    int activeScreenId;
    int activeComponentId;
    String parameter;  /*如果参数是对象类型，那么参数为对象的Gson字符串*/

    /**
     * 构造器
     * @param activeScreenId
     * @param activeComponentId
     * @param parameter
     * @param action
     */
    ActiveLink(int activeScreenId,int activeComponentId,String parameter,Action action){
        super(action);
        super.classType=ACTIONTYPE_MEAN_ACTIVE;
        this.activeScreenId=activeScreenId;
        this.activeComponentId=activeComponentId;
        this.parameter=parameter;
    }

}
