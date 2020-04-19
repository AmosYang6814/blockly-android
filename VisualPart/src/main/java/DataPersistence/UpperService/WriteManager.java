package DataPersistence.UpperService;

/**
 * Created by Administrator on 2020/2/2.
 */

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import DataPersistence.CenterControl.CacheManager;
import DataPersistence.DataBean.Component.Component;
import DataPersistence.DataBean.RelationShip.Relation;
import DataPersistence.DataBean.Status.Status;

/**
 * 为同一个组件提供同步加锁的功能，防止异步写
 */
public class WriteManager {
    Lock componentLock=new ReentrantLock();
    Lock statusLock=new ReentrantLock();
    Lock relationLock=new ReentrantLock();

    /**
     * 添加组件
     * @param component
     * @return
     */
    public boolean submitComponent(Component component){
        boolean result=false;
        try {
            componentLock.lock();
            if(component.getId()==-1) if(CacheManager.getCacheManager().addComponent(component))result=true;
            else if(CacheManager.getCacheManager().modifyComponent(component))result=true;
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            componentLock.unlock();
            return result;
        }
    }



    /**
     * 添加状态
     * @param status
     * @return
     */
    public boolean submitStatus(Status status){
        boolean result=false;
        try {
            componentLock.lock();
            statusLock.lock();

            if(status.getId()==-1) if(CacheManager.getCacheManager().addStatus(status))result=true;
            else if(CacheManager.getCacheManager().modifyStatus(status))result=true;
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            componentLock.unlock();
            statusLock.unlock();
            return result;
        }
    }

    /**
     * 添加状态
     * @param relation
     * @return
     */
    public boolean submitRelation(Relation relation){
        boolean result=false;
        try {
            statusLock.lock();
            relationLock.lock();

            if(relation.getId()==-1) if(CacheManager.getCacheManager().addRelation(relation))result=true;
            else if(CacheManager.getCacheManager().modifyRelation(relation))result=true;
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            statusLock.unlock();
            relationLock.unlock();
            return result;
        }
    }

    /**
     * 根据Id删除
     * @return
     */
    public boolean deleteinfo(int type,int id){
       return CacheManager.getCacheManager().deleteinfo(id,type);
    }

    public static WriteManager getWritManager(){
        return new WriteManager();
    }



}
