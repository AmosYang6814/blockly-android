package Logic.LogicCore.RelationLogic;

import com.google.gson.Gson;

import java.util.LinkedList;
import java.util.List;

import DataPersistence.DataBean.RelationShip.Relation;
import DataPersistence.UpperService.ReadManager;
import DataPersistence.UpperService.WriteManager;
import GlobalTools.DataBean.Action.Action;
import GlobalTools.DataBean.Action.ActiveLink;
import GlobalTools.DataBean.Action.ChangeLink;
import GlobalTools.DataBean.Action.Screenlink;
import GlobalTools.GlobalManager;

public class RelationCore implements RelationToDataPre {

    /**
     * 存储动作
     * @param action
     * @return
     */
    @Override
    public boolean saveAction(Action action) {
        Relation relation=new Relation();
        if(action.getActionId()==-1) action.setActionId(GlobalManager.registerAction());
        relation.setId(action.getActionId());
        relation.setRelationType(action.getRelationType());
        relation.setPer_status_id(action.getScreenId());

        if(action.getRelationType()==Action.ACTIONTYPE_MEAN_JUMP){
            relation.setNext_status_id(((Screenlink)action).getNextScreenId());
        }else relation.setNext_status_id(action.getComponentId());
        relation.setActionDefine(new Gson().toJson(action));
        return WriteManager.getWritManager().submitRelation(relation);
    }

    /**
     * 删除动作
     * @param actionId
     * @return
     */
    @Override
    public boolean deleteAction(int actionId) {
       return WriteManager.getWritManager().deleteinfo(Relation.RELATION,actionId);
    }


    /**
     * 获取全部的动作
     * @return
     */
    @Override
    public LinkedList<Action> getAllAction() {
        LinkedList<Action> actions=new LinkedList<>();
        List<Relation>relations= ReadManager.getAllRelation();
        Action actionTemp=null;
        Gson gson=new Gson();
        for(Relation relation:relations){
            if(relation.getRelationType()==Action.ACTIONTYPE_MEAN_JUMP) actionTemp=gson.fromJson(relation.getActionDefine(),Screenlink.class);
            else if(relation.getRelationType()==Action.ACTIONTYPE_MEAN_ACTIVE)actionTemp=gson.fromJson(relation.getActionDefine(), ActiveLink.class);
            else if(relation.getRelationType()==Action.ACTIONTYPE_MEAN_CHANGE)actionTemp=gson.fromJson(relation.getActionDefine(), ChangeLink.class);

            actions.add(actionTemp);
        }
        return actions;
    }


    /**
     * 根据id获取动作
     * @param actionId
     * @return
     */
    @Override
    public Action getActionById(int actionId) {
        Relation relation=ReadManager.getRelationById(actionId);
        Action actionTemp=null;
        Gson gson=new Gson();
        if(relation.getRelationType()==Action.ACTIONTYPE_MEAN_JUMP) actionTemp=gson.fromJson(relation.getActionDefine(),Screenlink.class);
        else if(relation.getRelationType()==Action.ACTIONTYPE_MEAN_ACTIVE)actionTemp=gson.fromJson(relation.getActionDefine(), ActiveLink.class);
        else if(relation.getRelationType()==Action.ACTIONTYPE_MEAN_CHANGE)actionTemp=gson.fromJson(relation.getActionDefine(), ChangeLink.class);
        return actionTemp;
    }
}
