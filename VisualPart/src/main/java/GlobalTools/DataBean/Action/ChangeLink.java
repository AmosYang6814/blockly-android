package GlobalTools.DataBean.Action;


import java.util.LinkedList;

import GlobalTools.DataBean.UiComponent;

public class ChangeLink extends Action {
    LinkedList<UiComponent> changes;
    /**
     * 改变的构造器
     * @param action
     */
    ChangeLink(Action action){
        super(action);
        changes=new LinkedList<>();
        super.classType=ACTIONTYPE_MEAN_CHANGE;
    }

    public void addStatusNote(UiComponent statusNode){
        changes.add(statusNode);
    }

    public void deleteComponent(int componentId){
        UiComponent statusNode=null;
        for(UiComponent s:changes){
            if(s.getId()==componentId)statusNode=s;
        }
        this.changes.remove(statusNode);
    }
}
