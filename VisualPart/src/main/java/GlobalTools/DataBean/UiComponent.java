package GlobalTools.DataBean;

import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import Logger.LogManager;
import UI.Tools.domain.Module;

/**
 * Created by Administrator on 2020/2/4.
 */


/**
 * UIComponent中采用的是装饰模式，其中使用结构体ComponentType区别
 * @param
 */

public class UiComponent {

    /**
     * -----------------------------------------------------------------------------------------
     * 可见性
     */

    Visibility visible;
    public Visibility getVisiblity() {
        return visible;
    }

    public void setVisiblity(Visibility visible) {
        this.visible = visible;
    }


    /**
     * ----------------------------------------------------------------------------------------------
     */
    String UUID="";

    public String getUUID() {
        return UUID;
    }

    public void setUUID(String UUID) {
        this.UUID = UUID;
    }

    /**
     * ----------------------------------------------------------------------------------------------
     * 组件名字
     */
    public String nearName;

    public void setNearName(String nearName) {
        this.nearName = nearName;
    }

    public String getNearName() {
        return nearName;
    }

    /**
     * ---------------------------------------------------------------------------------------------
     *
     * 组件的类型的定义
     */
    public enum ComponentType{
        complex,simple;
    }

    /**
     * 组件类型
     */
    ComponentType componentType=ComponentType.simple;


    public UiComponent(ComponentType componentType,String className) {
        try {
            this.componentClass=Class.forName(className);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        setComponentType(componentType);
    }


    public ComponentType getComponentType() {
        return componentType;
    }
    public void setComponentType(ComponentType componentType) {
        this.componentType = componentType;
        if(componentType.equals(ComponentType.complex))simpleComponent=new HashMap<>();
    }
    /**
     * ---------------------------------------------------------------------------------------------------------------
     *
     * 包含的简单组件
     */

    public HashMap<Integer,UiComponent> simpleComponent;
    /**
     * 添加简单组件
     * @param component
     * @return
     */
    public boolean addSimpleComponent(UiComponent component){
        try {
            simpleComponent.put(component.getId(),component);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            LogManager.print("未指定组件类型：复杂组件？");
            return false;
        }
    }

    /**
     * 获取简单组件
     * @param id
     * @return
     */
    public UiComponent getUiComponent( int id){
        return simpleComponent.get(id);
    }
    public LinkedList<UiComponent> getSimpleUiComponent(){
        LinkedList<UiComponent> list=new LinkedList<>();
        for(Map.Entry<Integer,UiComponent> entry:simpleComponent.entrySet())list.add(entry.getValue());
        return list;
    }

    /**
     * -------------------------------------------------------------------------------------------------------
     *
     * 组件中内容的引用
     */
    Object componentObj;
    Class componentClass;

    /**
     * 组件中包含的类指针
     * @return
     */
    public Class getComponentClass() { return componentClass; }
    public void setComponentClass(Class componentClass) { this.componentClass = componentClass; }
    /**
     * 获取组件
     * @return
     */
    public Object getComponentObj() {
        if(componentObj==null)reflect(componentClass);
        return componentObj;
    }

    public void setComponentObj(Object componentObj) {
        this.componentObj = componentObj;
    }

    private void reflect(Class componentClass){
        try {
            componentObj=componentClass.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    /**
     * ----------------------------------------------------------------------------------------------------
     *
     * id的获取和确认
     */
    int id=-1;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    /**
     * -------------------------------------------------------------------------------------------------------
     *
     * 组件所属的分类
     *
     */

    String classfiy="默认分类";
    public String getClassfiy() {
        return classfiy;
    }

    public void setClassfiy(String classfiy) {
        this.classfiy = classfiy;
    }




    /**
     * --------------------------------------------------------------------------------------------------------
     * 组件的属性引用
     */
    HashMap<String,Object> modifyAttributes=new HashMap<>();
    List<Attribute> attributesDefines=new ArrayList<>();

    public void addAttributeDefine(String methodName,String...type){
    }

    /**
     * 添加新的设置
     * @param attributeName
     * @param values
     * @return
     */
    public boolean AddNewSetting(String attributeName,Object... values){
        try {
            return setAttribute(attributeName,values);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    /**
     * 删除属性
     * @param attributeName
     * @return
     */
    public boolean deleteSetting(String attributeName){
        modifyAttributes.remove(attributeName);
        return setAttributeToDefault(attributeName);
    }

    /**
     * 设置属性的方法，属性的个数不允许超过三个
     * @param attributeName
     * @param values
     * @return
     */
    public boolean setAttribute(String attributeName,Object... values){
        Class componentClass=componentObj.getClass();
        Method method=null;
        try {
            switch (values.length){
                case 0:
                    method=componentClass.getMethod(attributeName);
                    method.invoke(null);
                    break;
                case 1:
                    method=componentClass.getMethod(attributeName,values[0].getClass());
                    method.invoke(values[0]);
                    break;
                case 2:
                    method=componentClass.getMethod(attributeName,values[0].getClass(),values[1].getClass());
                    method.invoke(values[0],values[1]);
                    break;
                case 3:
                    method=componentClass.getMethod(attributeName,values[0].getClass(),values[1].getClass(),values[2].getClass());
                    method.invoke(values[0],values[1],values[2]);
                    break;
                default: return false;
            }

            for(int i=0;i<values.length;i++){
                modifyAttributes.put(attributeName+"_*_"+i,values[i]);
            }

            return true;
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            return false;
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return false;
        } catch (InvocationTargetException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 设置属性方法到默认值，，，，，，方法正确性待检测
     * @param attributeName
     * @return
     */
    public boolean setAttributeToDefault(String attributeName){
        try {
            Class componentClass=componentObj.getClass();
            Method[] methods=componentClass.getMethods();
            for(int i=0;i<methods.length;i++){
                if(methods[i].getName().equals(attributeName))methods[i].invoke(methods[i].getDefaultValue());
            }
            return true;
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return false;
        } catch (InvocationTargetException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 获取全部的属性值
     */
    public HashMap<String,Object> getAllAttribute(){
        return (HashMap<String, Object>) modifyAttributes.clone();
    }

    /**
     * 获取全部已经定义的属性
     */

    public List<Attribute> getAllDefineAttribute(){
        return attributesDefines;
    }

    /**
     * 添加定义的属性
     * @param attribute
     * @return
     */
    public boolean addDefineAttribute(Attribute attribute){
        try {
            attributesDefines.add(attribute);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    public boolean clearAttributes(){
        try {
            attributesDefines.clear();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }



}

