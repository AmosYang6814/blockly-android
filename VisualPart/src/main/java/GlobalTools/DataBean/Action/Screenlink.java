package GlobalTools.DataBean.Action;

import GlobalTools.DataBean.Action.Action;
import GlobalTools.DataBean.Screen;

/**
 * 链接的全局的数据接口，链接两个动作，并记录链接的方式以及其他的数据
 */
public class Screenlink extends Action {
    public String getPreScreenId() {
        return preScreenId;
    }

    public void setPreScreenId(String preScreenId) {
        this.preScreenId = preScreenId;
    }

    public String getNextScreenId() {
        return nextScreenId;
    }

    public void setNextScreenId(String nextScreenId) {
        this.nextScreenId = nextScreenId;
    }

    String preScreenId;
    String nextScreenId;



    /**
     * 构造器
     * @param pre
     * @param next
     * @param action
     */
    @SuppressWarnings("unchecked")
    public Screenlink(String pre, String next, Action action) {
        super(action.getScreenId(),action.getComponentId(),action.getEvent(),action.getActionMean());
        super.classType=ACTIONTYPE_MEAN_JUMP;
        this.preScreenId = pre;
        this.nextScreenId = next;
    }
}
