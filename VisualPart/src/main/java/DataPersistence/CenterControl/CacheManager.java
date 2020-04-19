package DataPersistence.CenterControl;

/**
 * Created by Administrator on 2020/2/2.
 */

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import DataPersistence.DataBean.Component.Component;
import DataPersistence.DataBean.RelationShip.Relation;
import DataPersistence.DataBean.Status.Status;
import GlobalTools.DataBean.Prioritypack;

/**
 * 缓存，用于加快操作速度
 */
public class CacheManager {

    static CacheManager cacheManager=new CacheManager();

    /**
     * 获取Cahche管理器
     * @return
     */
    public static CacheManager getCacheManager() {
        return cacheManager;
    }

    //组件缓存容量初始值设置为100
    volatile ConcurrentHashMap<Integer,Prioritypack<Component>> componentCache=new ConcurrentHashMap();
    volatile ConcurrentHashMap<Integer,Prioritypack<Status>> statusCache=new ConcurrentHashMap<>();
    volatile ConcurrentHashMap<Integer,Prioritypack<Relation>> relationCache=new ConcurrentHashMap<>();


    /**
     * 返回所有的Component组件
     * @return
     */
    public LinkedList<Component> getAllComponent(){
        LinkedList<Component> components=new LinkedList<>();
        for(int id:IdManager.getIdManager().componentId){
            components.add(DAOmanager.getDaOmanager().getComponentDAO().getComponentById(id));
        }
        return components;
    }

    /**
     * 根据ID获取组件
     * @param id
     * @return
     */
    public Component getComponentById(int id){
        if(!componentCache.containsKey(id)){
            Component component=DAOmanager.getDaOmanager().getComponentDAO().getComponentById(id);
            Prioritypack<Component> componentProipery=new Prioritypack<>(1,component);
            componentCache.put(component.getId(),componentProipery);
            return component;
        }
        else {
            componentCache.get(id).increaseCount(); //添加优先级
            return componentCache.get(id).getObject();
        }
    }

    /**
     * 返回所有的状态
     * @return
     */
    public LinkedList<Status> getAllStatus(){
        LinkedList<Status> status=new LinkedList<>();
        for(int id:IdManager.getIdManager().statusId){
            status.add(DAOmanager.getDaOmanager().getStatusDAO().getStatusById(id));
        }
        return status;
    }


    /**
     * 根据ID获取状态
     * @param id
     * @return
     */
    public Status getStatusById(int id){
        if(!statusCache.containsKey(id)){
            Status status=DAOmanager.getDaOmanager().getStatusDAO().getStatusById(id);
            Prioritypack<Status> componentProipery=new Prioritypack<>(1,status);
            statusCache.put(status.getId(),componentProipery);
            return status;
        }
        else {
           statusCache.get(id).increaseCount(); //添加优先级
            return statusCache.get(id).getObject();
        }
    }

    /**
     * 返回所有的链接
     * @return
     */
    public LinkedList<Relation> getAllRelation (){
        LinkedList<Relation> relations=new LinkedList<>();
        for(int id:IdManager.getIdManager().relationId){
            relations.add(DAOmanager.getDaOmanager().getRelationDAO().getLinkById(id));
        }
        return relations;
    }

    /**
     * 根据ID获取链接
     * @param id
     * @return
     */
    public Relation getRelationById(int id){
        if(!relationCache.containsKey(id)){
            Relation relation=DAOmanager.getDaOmanager().getRelationDAO().getLinkById(id);
            Prioritypack<Relation> componentProipery=new Prioritypack<>(1,relation);
            relationCache.put(relation.getId(),componentProipery);
            return relation;
        }
        else {
            relationCache.get(id).increaseCount(); //添加优先级
            return relationCache.get(id).getObject();
        }
    }


    /**
     * 添加组件
     */
    public boolean addComponent(Component component){
        try {
            if(component.getId()!=-1)return false;  //检查
            component.setId(IdManager.getIdManager().registerComponent(component)); //注册ID
            componentCache.put(component.getId(),new Prioritypack<Component>(1,component));
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 修改组件
     */
    public boolean modifyComponent(Component component){
        try {
            if(component.getId()==-1)return false;  //检查
            componentCache.get(component.getId()).setObject(component);
            componentCache.get(component.getId()).increaseCount();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 添加状态
     * @param status
     * @return
     */
    public boolean addStatus(Status status){
        try {
            if(status.getId()!=-1)return false;  //检查
            status.setId(IdManager.getIdManager().registerStatus(status)); //注册ID
           statusCache.put(status.getId(),new Prioritypack<Status>(1,status));
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 修改状态
     * @param status
     * @return
     */
    public boolean modifyStatus(Status status){
        try {
            if(status.getId()==-1)return false;  //检查
           statusCache.get(status.getId()).setObject(status);
           statusCache.get(status.getId()).increaseCount();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 添加链接
     * @param relation
     * @return
     */
    public boolean addRelation(Relation relation){
        try {
            if(relation.getId()!=-1)return false;  //检查
            relation.setId(IdManager.getIdManager().registerRelation(relation)); //注册ID
           relationCache.put(relation.getId(),new Prioritypack<Relation>(1,relation));
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 修改链接
     * @param relation
     * @return
     */
    public boolean modifyRelation(Relation relation){
        try {
            if(relation.getId()==-1)return false;  //检查
           relationCache.get(relation.getId()).setObject(relation);
           relationCache.get(relation.getId()).increaseCount();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }




    /**
     * 刷新存储
     */
    public void refeshCache(){
        List<Integer> list;
        if(componentCache.size()>100){
            list=clearCache(componentCache);
            if(store(Component.COMPONENT,componentCache,list))delete(Component.COMPONENT,componentCache,list);
        }
        if(statusCache.size()>100){
            list=clearCache(statusCache);
            if(store(Status.STATUS,statusCache,list))delete(Status.STATUS,statusCache,list);
        }
        if(relationCache.size()>100){
            list=clearCache(relationCache);
            if(store(Relation.RELATION,relationCache,list))delete(Relation.RELATION,relationCache,list);
        }
    }


    /**
     * 存储所有缓存中的元素
     * @return
     */
    public boolean storeAll(){
        try {
            List<Integer> list=new LinkedList<>();

            for(Map.Entry<Integer,Prioritypack<Component>> entry:componentCache.entrySet())list.add(entry.getKey());
            if(store(Component.COMPONENT,componentCache,list))delete(Component.COMPONENT,componentCache,list);

            list.clear();
            for(Map.Entry<Integer,Prioritypack<Status>> entry:statusCache.entrySet())list.add(entry.getKey());
            if(store(Status.STATUS,statusCache,list))delete(Status.STATUS,statusCache,list);

            list.clear();
            for(Map.Entry<Integer,Prioritypack<Relation>> entry:relationCache.entrySet())list.add(entry.getKey());
            if(store(Relation.RELATION,relationCache,list))delete(Relation.RELATION,relationCache,list);

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }


    /**
     * 清理超过100的引用次数低的
     * @param cache
     * @return
     */
    public List<Integer> clearCache (ConcurrentHashMap cache){
        List<Map.Entry<Integer,Prioritypack>> list=new ArrayList();
        list.addAll(cache.entrySet());

        Collections.sort(list, new
                Comparator<Map.Entry<Integer, Prioritypack>>() {
                    @Override
                    public int compare(Map.Entry<Integer, Prioritypack> o1, Map.Entry<Integer, Prioritypack> o2) {
                        if(o1.getValue().getCount()>=o2.getValue().getCount())return 1;
                        else return -1;
                    }
                });

        if(list.size()<100)return null;
        else {
            List<Integer> list1=new LinkedList();

            for(int i=99;i<list.size();i++){
                list1.add(list.get(i).getKey());
            }
            return list1;
        }
    }


    /**
     * 进行存储
     * @param classtype
     * @param cache
     * @param list
     * @return
     */
    private  boolean store(int classtype,ConcurrentHashMap cache,List<Integer> list){
        try {
            switch (classtype){
                case Component.COMPONENT:
                    for(int index:list){
                        DAOmanager.getDaOmanager().getComponentDAO().
                                modifyComponent(index,((ConcurrentHashMap<Integer,Prioritypack<Component>>)cache)
                                        .get(index).getObject());

                    }


                    break;
                case Status.STATUS:
                    for(int index:list){
                        DAOmanager.getDaOmanager().getStatusDAO()
                                .modifyStatus(index,((ConcurrentHashMap<Integer,Prioritypack<Status>>)cache)
                                        .get(index).getObject());

                    }
                    break;
                case Relation.RELATION:
                    for(int index:list){
                        DAOmanager.getDaOmanager().getRelationDAO()
                                .modifyRelation(index,((ConcurrentHashMap<Integer,Prioritypack<Relation>>)cache)
                                        .get(index).getObject());
                    }
                    break;
                default:return false;
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 根据序号删除
     * @param classtype
     * @param cache
     * @param list
     * @return
     */
    private boolean delete(int classtype,ConcurrentHashMap cache,List<Integer> list){
        try {
           for(Integer i:list){
               cache.remove(i);
           }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    /**
     * 根据Id删除信息
     */
     public boolean deleteinfo(int id , int type){
        try {
            switch (type){
                case Component.COMPONENT:
                    DAOmanager.getDaOmanager().getComponentDAO().deleteComponentById(id);
                    break;
                case Status.STATUS:
                    DAOmanager.getDaOmanager().getStatusDAO().deleteStatus(id);
                    break;
                case Relation.RELATION:
                    DAOmanager.getDaOmanager().getRelationDAO().deleRelation(id);
                    break;
            }

            IdManager.getIdManager().Unregister(id);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }




}

