package UI.Tools.domain;

import java.util.LinkedList;

import GlobalTools.DataBean.Attribute;

/**
 * Date:2020/3/23
 * Time:12:42
 * 组件的实体类
 * <module> 表示组件的标签
 *   属性如下：
 *   className=:表示匹配的属性的全类路径名/all表示下面的属性针对所有组件都需要设置
 *   displayName=表示组件的显示的名称
 *   classify:表示组件的分类
 *   <attribute>标签表示该组件的属性设置方法
 */
public class Module {

    public Integer getModuleId() {
        return moduleId;
    }

    public void setModuleId(Integer moduleId) {
        this.moduleId = moduleId;
    }

    //每个模块需要一个id
    private Integer moduleId;
    private String className;
    private String displayName;
    //组件的分类信息
    private ClassifyItem classifyItem;
    //一个组件可以包含多个属性信息
    private LinkedList<Attribute> attributeLinkedList;

    public LinkedList<Attribute> getAttributeLinkedList() {
        return attributeLinkedList;
    }

    public void setAttributeLinkedList(LinkedList<Attribute> attributeLinkedList) {
        this.attributeLinkedList = attributeLinkedList;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public ClassifyItem getClassifyItem() {
        return classifyItem;
    }


    @Override
    public String toString() {
        return "Module{" +
                "moduleId=" + moduleId +
                ", className='" + className + '\'' +
                ", displayName='" + displayName + '\'' +
                ", classifyItem=" + classifyItem +
                ", attributeLinkedList=" + attributeLinkedList.toString() +
                '}';
    }

    public void setClassifyItem(ClassifyItem classifyItem) {
        this.classifyItem = classifyItem;
    }
}
