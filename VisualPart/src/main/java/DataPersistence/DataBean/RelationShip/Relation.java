package DataPersistence.DataBean.RelationShip;

/**
 * Created by Administrator on 2020/2/1.
 */

public class Relation {
    public static final int RELATION=10002;
    /**
     * 关系连接的id的值，自增的属性，无需设定
     */
    int id;



    /**
     * 前一个状态的id
     */
    int per_status_id;

    /**
     * 后一个状态的id
     */
    int next_status_id;

    int logic_id;

    public int getRelationType() {
        return RelationType;
    }

    public void setRelationType(int relationType) {
        RelationType = relationType;
    }

    int RelationType;

    /**
     * 为动作类的StringGson描述，
     */
    String ActionDefine;

    public String getActionDefine() {
        return ActionDefine;
    }

    public void setActionDefine(String actionDefine) {
        ActionDefine = actionDefine;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public int getPer_status_id() {
        return per_status_id;
    }

    public void setPer_status_id(int per_status_id) {
        this.per_status_id = per_status_id;
    }

    public int getNext_status_id() {
        return next_status_id;
    }

    public void setNext_status_id(int next_status_id) {
        this.next_status_id = next_status_id;
    }

    public int getLogic_id() {
        return logic_id;
    }

    public void setLogic_id(int logic_id) {
        this.logic_id = logic_id;
    }
}
