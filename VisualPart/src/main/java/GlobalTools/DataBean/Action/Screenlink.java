package GlobalTools.DataBean.Action;

import GlobalTools.DataBean.Action.Action;
import GlobalTools.DataBean.Screen;

/**
 * 链接的全局的数据接口，链接两个动作，并记录链接的方式以及其他的数据
 */
public class Screenlink extends Action {
    public int getPreScreenId() {
        return preScreenId;
    }

    public void setPreScreenId(int preScreenId) {
        this.preScreenId = preScreenId;
    }

    public int getNextScreenId() {
        return nextScreenId;
    }

    public void setNextScreenId(int nextScreenId) {
        this.nextScreenId = nextScreenId;
    }

    int preScreenId;
    int nextScreenId;



    /**
     * 构造器
     * @param pre
     * @param next
     * @param action
     */
    public Screenlink(int pre, int next, Action action) {
        super(action.getScreenId(),action.getComponentId(),action.getAction(),action.getActionMean());
        super.classType=ACTIONTYPE_MEAN_JUMP;
        this.preScreenId = pre;
        this.nextScreenId = next;
    }
}
