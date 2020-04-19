package DataPersistence.CenterControl;

/**
 * Created by Administrator on 2020/2/1.
 */

import java.util.HashSet;
import java.util.Set;

import DataPersistence.AbstractStorage.PreserveId;
import DataPersistence.DataBean.Component.Component;
import DataPersistence.DataBean.RelationShip.Relation;
import DataPersistence.DataBean.Status.Status;

/**
 * 管理全部组件的id的值
 */
public class IdManager {
    /**
     * ID管理器的唯一的实例
     */
    static IdManager idManager;

    /**
     * 组件的id
     */
    Set<Integer> componentId;

    /**
     * 状态的id
     */
    Set<Integer> statusId;

    /**
     * 关系的id
     */
    Set<Integer> relationId;


    /**
     * 初始化，并获取记录的全部组件
     */
    private IdManager(){
        componentId=DAOmanager.getDaOmanager().getPreserveIdDAO().getALLId(PreserveId.ID_TYPE_COMPONENT);
        if(componentId==null)componentId=new HashSet<>();

        statusId=DAOmanager.getDaOmanager().getPreserveIdDAO().getALLId(PreserveId.ID_TYPE_STATUS);
        if(statusId==null)statusId=new HashSet<>();

        relationId=DAOmanager.getDaOmanager().getPreserveIdDAO().getALLId(PreserveId.ID_TYPE_RELATION);
        if(relationId==null)relationId=new HashSet<>();
    }

    /**
     * 注册组件
     * @param component
     * @return
     */
    public int registerComponent(Component component){
        for(int i=100;i<10000;i++)if(!componentId.contains(i))return i;
        return -1;
    }

    /**
     * 注册状态组件
     * @param status
     * @return
     */
    public int registerStatus(Status status){
        for(int i=10000;i<20000;i++)if(!statusId.contains(i))return i;
        return -1;
    }

    /**
     * 注册关系组件
     * @return
     */
    public int registerRelation(Relation relation){
        for(int i=20000;i<30000;i++)if(!relationId.contains(i))return i;
        return -1;
    }

    /**
     * 将所有id缓存做持久化存储
     * @return
     */
    public boolean storeAllIdCache(){
        boolean componentResult=DAOmanager.getDaOmanager().getPreserveIdDAO().refreshAllId(PreserveId.ID_TYPE_COMPONENT,componentId);
        boolean statusResult=DAOmanager.getDaOmanager().getPreserveIdDAO().refreshAllId(PreserveId.ID_TYPE_STATUS,componentId);
        boolean relationResult=DAOmanager.getDaOmanager().getPreserveIdDAO().refreshAllId(PreserveId.ID_TYPE_RELATION,componentId);

        if(componentResult && statusResult && relationResult)return true;
        else return false;
    }




    public static IdManager getIdManager() {
        if(idManager==null)idManager=new IdManager();
        return idManager;
    }

    /**
     * 在其他层调用注册状态
     * @return
     */
    public int registerStatusInOtherLayer(){
        for(int i=10000;i<20000;i++)if(!statusId.contains(i))return i;
        return -1;
    }

    /**
     * 在其它层注册链接
     * @return
     */
    public int  registerRelationInOtherLayer(){
        for(int i=20000;i<30000;i++)if(!relationId.contains(i))return i;
        return -1;
    }

    /**
     * 反注册，卸载
     * @param id
     * @return
     */
    public boolean Unregister(int id){
        try {
            if(componentId.contains(id))componentId.remove(id);
            else if(statusId.contains(id))statusId.remove(id);
            else if(relationId.contains(id))relationId.remove(id);

            storeAllIdCache();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
