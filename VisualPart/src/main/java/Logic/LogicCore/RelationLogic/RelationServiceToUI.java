package Logic.LogicCore.RelationLogic;

import java.util.LinkedList;

import DataPersistence.DataBean.RelationShip.Relation;
import GlobalTools.DataBean.Action.Action;
import UI.Translate.RelationOp;

public class RelationServiceToUI implements RelationOp {

    RelationCore relationCore=new RelationCore();
    @Override
    public LinkedList<Action> getAllAction() {
        return relationCore.getAllAction();
    }

    @Override
    public Action getActionById(int id) {
        return relationCore.getActionById(id);
    }

    @Override
    public boolean saveAction(Action action) {
        return relationCore.saveAction(action);
    }

    @Override
    public boolean deleteAction(int actionId) {
        return relationCore.deleteAction(actionId);
    }
}
