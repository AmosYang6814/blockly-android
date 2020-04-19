package Logic.Reflect;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.concurrent.PriorityBlockingQueue;

import GlobalTools.DataBean.Prioritypack;
import GlobalTools.DataBean.UiComponent;
import UI.Translate.ComponentInfo;
import UI.Translate.TranslateToComponent;

/**
 * 反射器模块针对UI层的入口，主要为了获取所有的组建的反射，提供少许缓存功能,采用模板方式
 */

/**
 * Created by Administrator on 2020/2/14.
 */

public class ComponentReflectToUI implements ComponentInfo,TranslateToComponent {

    ReflectCore reflectCore;
    PriorityBlockingQueue<Prioritypack<UiComponent>> prioritypackPriorityBlockingQueue=new PriorityBlockingQueue<>();

    public ComponentReflectToUI(){
        if(reflectCore==null)reflectCore=new ReflectCore();
    }

    /**
     * 获取所有的组件
     * @return
     */
    @Override
    public LinkedList<UiComponent> getAllSimpleComponent() {

        try {
            LinkedList<UiComponent> list=(LinkedList<UiComponent>) reflectCore.getAllComponent();
            ArrayList<UiComponent> index=new ArrayList<>();
            for(UiComponent uiComponent:list){
                if(uiComponent.getComponentType().equals(UiComponent.ComponentType.complex))index.add(uiComponent);
            }

            for(UiComponent uiComponent:index){
                list.remove(uiComponent);
            }

            return list;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }


    @Override
    public LinkedList<UiComponent> getAllComplexCompoent() {

        try {
            LinkedList<UiComponent> list=(LinkedList<UiComponent>) reflectCore.getAllComponent();
            ArrayList<UiComponent> index=new ArrayList<>();
            for(UiComponent uiComponent:list){
                if(uiComponent.getComponentType().equals(UiComponent.ComponentType.simple))index.add(uiComponent);
            }

            for(UiComponent uiComponent:index){
                list.remove(uiComponent);
            }

            return list;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public UiComponent getComplexComponentById(int ComponentId) {
        return null;
    }


    @Override
    public UiComponent getSimpleComponentById(int componentId) {
        try {
            return reflectCore.getComponentById(componentId);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 删除优先级队列中的缓存
     */
    public void deleteCache(){
        while (prioritypackPriorityBlockingQueue.size()>20){
            prioritypackPriorityBlockingQueue.poll();
        }
    }

    @Override
    public boolean saveComponent(UiComponent uiComponent) {
       return reflectCore.saveComponent(uiComponent);
    }

    @Override
    public boolean deleteComponent(UiComponent uiComponent) {
        return reflectCore.deleteComponent(uiComponent);
    }
}

