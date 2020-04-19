package Logic.Reflect;

import com.google.gson.Gson;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import DataPersistence.DataBean.Component.ComplexComponent;
import DataPersistence.DataBean.Component.Component;
import DataPersistence.DataBean.Component.SimpleComponent;
import DataPersistence.DataBean.Component.attribute;
import DataPersistence.UpperService.ReadManager;
import DataPersistence.UpperService.WriteManager;
import GlobalTools.DataBean.UiComponent;

/**
 * Created by Administrator on 2020/2/14.
 *
 * 功能：提供反射模块
 */


public class ReflectCore implements ReflectToDataPe {

    /**
     * 根据名字反射组件，底层Component组件要求全类名
     * @return
     */
    @Override
    public List<UiComponent> getAllComponent() throws Exception {
        List<Component> list=ReadManager.getAllComponent();
        List<UiComponent> result=new LinkedList<>();
        Class temp;
        UiComponent uiComponenttemp;
        for(Component component:list){
            if(component.getType()==Component.SIMPLE_COMPONENT_TYPE){
                uiComponenttemp=new UiComponent(UiComponent.ComponentType.simple,component.getNearName());
                uiComponenttemp.setVisiblity(component.getVisiblity());
                uiComponenttemp.setId(component.getId());
                temp=Class.forName(((SimpleComponent)component).getComponentClassName());
                Object o=temp.newInstance();
                uiComponenttemp.setComponentObj(o);
                result.add(uiComponenttemp);
            }
            else {
                ComplexComponent complexComponenttemp=(ComplexComponent)component;
                uiComponenttemp=new UiComponent(UiComponent.ComponentType.complex,component.getNearName());
                uiComponenttemp.setVisiblity(component.getVisiblity());
                uiComponenttemp.setId(component.getId());
                for(SimpleComponent simpleComponent:complexComponenttemp.getSimpleComponents()){
                    UiComponent utemp=new UiComponent(UiComponent.ComponentType.simple,simpleComponent.getNearName());
                    utemp.setVisiblity(simpleComponent.getVisiblity());
                    utemp.setId(simpleComponent.getId());
                    temp=Class.forName(((SimpleComponent)component).getComponentClassName());
                    Object o=temp.newInstance();
                    uiComponenttemp.setComponentObj(o);
                    uiComponenttemp.addSimpleComponent(utemp);
                }
                result.add(uiComponenttemp);
            }

        }
        return result;
    }

    @Override
    public UiComponent getComponentById(int id)throws Exception {
        UiComponent result;
       Component component=ReadManager.getComponentById(id);
        if(component.getType()==Component.SIMPLE_COMPONENT_TYPE){
            result=new UiComponent(UiComponent.ComponentType.simple,component.getNearName());
            result.setId(id);
            result.setVisiblity(component.getVisiblity());
            Class temp=Class.forName(((SimpleComponent)component).getComponentClassName());
            Object o=temp.newInstance();
            result.setComponentObj(o);
        }else {
            ComplexComponent complexComponenttemp=(ComplexComponent)component;
            result=new UiComponent(UiComponent.ComponentType.complex,component.getNearName());
            result.setId(id);
            for(SimpleComponent simpleComponent:complexComponenttemp.getSimpleComponents()){
                UiComponent utemp=new UiComponent(UiComponent.ComponentType.simple,simpleComponent.getNearName());
                utemp.setVisiblity(simpleComponent.getVisiblity());
                utemp.setId(simpleComponent.getId());
                Class temp=Class.forName(((SimpleComponent)component).getComponentClassName());
                Object o=temp.newInstance();
                result.setComponentObj(o);
                result.addSimpleComponent(utemp);
            }
        }

        return result;
    }

    @Override
    public boolean saveComponent(UiComponent uicomponent) {
        try {
            if(uicomponent.getComponentType().equals(UiComponent.ComponentType.simple)){
                SimpleComponent component=new SimpleComponent();
                component.setNearName(uicomponent.getNearName());
                component.setComponentClassName(uicomponent.getComponentObj().getClass().getName());
                component.setVisiblity(uicomponent.getVisiblity());
                component.setId(uicomponent.getId());
                /*添加属性*/
                for(Map.Entry<String,Object> entry:uicomponent.getAllAttribute().entrySet()){
                    component.addAttributes(new attribute(entry.getKey(),new Gson().toJson(entry.getValue()),""));
                }

                WriteManager.getWritManager().submitComponent(component);
            }

            else{
                ComplexComponent complexComponent=new ComplexComponent();
                complexComponent.setNearName(uicomponent.getNearName());
                complexComponent.setVisiblity(uicomponent.getVisiblity());
                complexComponent.setId(uicomponent.getId());
                for(UiComponent simpleUiComponent:uicomponent.getSimpleUiComponent()){
                    SimpleComponent component=new SimpleComponent();
                    component.setNearName(simpleUiComponent.getNearName());
                    component.setComponentClassName(simpleUiComponent.getComponentObj().getClass().getName());
                    component.setVisiblity(simpleUiComponent.getVisiblity());
                     /*添加属性*/
                    for(Map.Entry<String,Object> entry:simpleUiComponent.getAllAttribute().entrySet()){
                        component.addAttributes(new attribute(entry.getKey(),new Gson().toJson(entry.getValue()),""));
                    }

                    /*添加组件*/
                    complexComponent.addSimpleComponent(component);
                }

                WriteManager.getWritManager().submitComponent(complexComponent);
            }

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean deleteComponent(UiComponent uicomponent) {
        return WriteManager.getWritManager().deleteinfo(Component.COMPONENT,uicomponent.getId());
    }


}
