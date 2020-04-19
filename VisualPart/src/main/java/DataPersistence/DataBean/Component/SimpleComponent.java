package DataPersistence.DataBean.Component;

/**
 * Created by Administrator on 2020/2/1.
 */


import java.util.LinkedList;

import GlobalTools.DataBean.Visibility;

/**
 * 简单模块的存储实体类
 */
public class SimpleComponent implements Component {


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
     * id of the component,is auto increase
     */
    int Id=-1;

    /**
     * the name of the component,
     */
    String ClassName;
    /**
     * point of the component,describle the location of the component in the screen
     */
    int point_x;
    int point_y;

    /**
     * 模块的支持的动作列表，
     * @return
     */
    LinkedList<Action> actions=new LinkedList<>();

    public LinkedList<Action> getActions() {
        return actions;
    }

    public void addActions(Action action) {
        actions.add(action);
    }
    /**
     * 模块支持的属性描述列表
     */
    LinkedList<attribute> attributes=new LinkedList<>();


    public LinkedList<attribute> getAttributes() {
        return attributes;
    }

    public void addAttributes(attribute attribute) {
        attributes.add(attribute);
    }

    public int getId() {
        return Id;
    }

    @Override
    public void setId(int id) {
        this.Id=id;
    }

    /**
     * 返回组件模块类型
     * @return
     */
    @Override
    public int getType() {
        return SIMPLE_COMPONENT_TYPE;
    }


    String componentName="";
    @Override
    public String getNearName() {
        return componentName;
    }

    @Override
    public void setNearName(String s) {
        this.componentName=s;
    }

    public String getComponentClassName() {
        return this.ClassName;
    }

    public void setComponentClassName(String componentName) {
        this.ClassName = componentName;
    }

    public int getPoint_x() {
        return point_x;
    }

    public void setPoint_x(int point_x) {
        this.point_x = point_x;
    }

    public int getPoint_y() {
        return point_y;
    }

    public void setPoint_y(int point_y) {
        this.point_y = point_y;
    }


}
