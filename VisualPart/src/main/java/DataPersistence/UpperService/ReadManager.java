package DataPersistence.UpperService;

/**
 * Created by Administrator on 2020/2/15.
 */

import java.util.LinkedList;
import java.util.List;

import DataPersistence.CenterControl.CacheManager;
import DataPersistence.DataBean.Component.Component;
import DataPersistence.DataBean.RelationShip.Relation;
import DataPersistence.DataBean.Status.Status;

/**
 * 允许异步读
 */
public class ReadManager {

    /**
     * 获取所有的组件
     * @return
     */
    public static List<Component> getAllComponent(){
       return CacheManager.getCacheManager().getAllComponent();
    }

    /**
     * 通过ID获取组件
     * @return
     */
    public static Component getComponentById(int id){
        return CacheManager.getCacheManager().getComponentById(id);
    }


    /**
     * 获取所有的状态
     * @return
     */
    public static List<Status> getAllStatus(){
        return CacheManager.getCacheManager().getAllStatus();
    }

    /**
     * 通过ID获取状态
     * @return
     */
    public static Status getStatusById(int id){
        return CacheManager.getCacheManager().getStatusById(id);
    }

    /**
     * 获取所有的链接
     * @return
     */
    public static List<Relation> getAllRelation(){
        return CacheManager.getCacheManager().getAllRelation();
    }

    /**
     * 根据Id获取所有的链接
     * @param id
     * @return
     */
    public static Relation getRelationById(int id){
        return CacheManager.getCacheManager().getRelationById(id);
    }


    public static ReadManager GetReadManagerInstance(){
        return new ReadManager();
    }
}
