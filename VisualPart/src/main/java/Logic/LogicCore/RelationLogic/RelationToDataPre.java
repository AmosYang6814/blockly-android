package Logic.LogicCore.RelationLogic;

import java.util.LinkedList;

import GlobalTools.DataBean.Action.Action;

/*对于数据持久层的接口 */
public interface RelationToDataPre {
    /**
     * 存储链接
     * @param action
     * @return
     */
    public boolean saveAction(Action action);

    /**
     * 删除链接
     * @param actionId
     * @return
     */
    public boolean deleteAction(int actionId);

    /**
     * 查询全部的链接
     * @return
     */
    public LinkedList<Action> getAllAction();

    /**
     * 根据io查询动作
     * @param actionId
     * @return
     */
    public Action getActionById(int actionId);
}
