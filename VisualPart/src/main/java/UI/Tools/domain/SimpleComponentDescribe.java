package UI.Tools.domain;

import java.util.LinkedList;

/**
 *SimpleProperties.xml对应的实体类
 */
public class SimpleComponentDescribe {

    /**
     * 当前状态包含的组件
     */
    private LinkedList <Module>moduleLinkedList;


    /**
     * 当前状态包含的组件类型
     */
    private LinkedList<ClassifyItem> classifyItemLinkedList;

    public LinkedList<ClassifyItem> getClassifyItemLinkedList() {
        return classifyItemLinkedList;
    }

    public void setClassifyItemLinkedList(LinkedList<ClassifyItem> classifyItemLinkedList) {
        this.classifyItemLinkedList = classifyItemLinkedList;
    }

    public LinkedList<Module> getModuleLinkedList() {

        return moduleLinkedList;
    }

    public void setModuleLinkedList(LinkedList<Module> moduleLinkedList) {
        this.moduleLinkedList = moduleLinkedList;
    }


}
