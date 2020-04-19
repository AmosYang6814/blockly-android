package UI.Tools.domain;

import java.util.LinkedList;

/**
 * Date:2020/3/23
 * Time:12:46
 * author:wenjun
 * 组件分类信息实体类，分类信息可以用很多个的
 *
 */
public class Classify {

    private LinkedList<ClassifyItem> classifyItems;

    public LinkedList<ClassifyItem> getClassifyItems() {
        return classifyItems;
    }

    public void setClassifyItems(LinkedList<ClassifyItem> classifyItems) {
        this.classifyItems = classifyItems;
    }

}
