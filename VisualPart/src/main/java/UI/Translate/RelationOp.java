package UI.Translate;

/**
 * Created by Administrator on 2020/2/4.
 */

import java.util.LinkedList;

import DataPersistence.DataBean.RelationShip.Relation;
import GlobalTools.DataBean.Action.Action;

/**
 * 获取所有的连接信息
 */
public interface RelationOp {

    /**
     * 获取所有的连接信息
     * @return
     */
    public LinkedList<Action> getAllAction();

    public Action getActionById(int id);

    public boolean saveAction(Action action);

    public boolean deleteAction(int actionId);
}
