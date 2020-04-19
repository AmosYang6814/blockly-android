package DataPersistence.DataBean.Status;

/**
 * Created by Administrator on 2020/2/1.
 */

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import GlobalTools.DataBean.UiComponent;

/**
 * 状态记录
 */
public class Status {
    public static final int STATUS=10003;


    int id; //自注册的id

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private String Name;

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    /**
     * 状态节点的集合
     */
    LinkedList<UiComponent> nodes=new LinkedList<>();


    /**
     * 返回模块的状态
     * @param componentId
     * @return
     */
    public UiComponent getComponentStatus(int componentId){
       return nodes.get(componentId);
    }

    /**
     * 添加组件状态
     * @return
     */
    public boolean addComponentSatus( UiComponent uiComponent){
        try {


            nodes.add(uiComponent);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }



    /**
     * 删除属性值

     * @return
     */
    public boolean deleteUiComponent(int componentId){
        try {

            UiComponent uiComponent=null;
            for(UiComponent statusNo:nodes){
                if(statusNo.getId()==componentId)uiComponent=statusNo;
            }
            nodes.remove(uiComponent);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }



    /**
     * 返回所有的节点
     * @return
     */
    public LinkedList<UiComponent> getAllStatusNode(){
        return (LinkedList<UiComponent>)nodes.clone();
    }

    /**
     * 设置
     * @param attributes
     * @return
     */
    public boolean setAttbutes(LinkedList<UiComponent> attributes){
        try {
            this.nodes=attributes;
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}
