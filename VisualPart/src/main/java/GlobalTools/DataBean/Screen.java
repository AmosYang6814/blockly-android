package GlobalTools.DataBean;

/**
 * Created by Administrator on 2020/2/4.
 */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import GlobalTools.GlobalManager;

/**
 * 屏幕信息包装类，用于包装该屏幕上所有显示的组件的信息
 */

public class Screen<T> {

    /*该屏幕的状态id*/
    int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    /*-----------------------------------------------------------------------------------------------------------*/

  private List<UiComponent> components=new LinkedList<>();

    /**
     * 添加组件
     * @param uiComponent
     * @return
     */

   public boolean addComponent(UiComponent uiComponent){
       try {

           components.add(uiComponent);
           return true;
       } catch (Exception e) {
           e.printStackTrace();
           return false;
       }
   }

    /**
     * 删除组件
     * @param componentid
     * @return
     */
   public boolean deleteComponent(int componentid){
       try {
           UiComponent statusNode=null;
           for(UiComponent s:components){
               if(s.getId()==componentid)statusNode=s;
           }
           components.remove(statusNode);
           return true;
       } catch (Exception e) {
           e.printStackTrace();
           return false;
       }
   }

    /**
     * 返回模块的状态
     * @param componentId
     * @return
     */
    public UiComponent getComponentStatus(int componentId){
        return components.get(componentId);
    }



    /**
     * 返回所有的节点
     * @return
     */
    public LinkedList<UiComponent> getAllStatusNode(){
        return (LinkedList<UiComponent>)components;
    }

    /*----------------------------------------------------------------------------------------------------------*/

    private String SreenName;
    private T ScreenObj;

    public String getSreenName() {
        return SreenName;
    }

    public void setSreenName(String sreenName) {
        SreenName = sreenName;
    }

    public T getScreenObj() {
        return ScreenObj;
    }

    public void setScreenObj(T screenObj) {
        ScreenObj = screenObj;
    }

    public Screen(String screenName, T ScreenObj){
        this.SreenName=screenName;
        this.ScreenObj=ScreenObj;
        this.setId(GlobalManager.registerScreen());
    }

    public Screen(String sreenName, List<UiComponent> components){
        this.SreenName=sreenName;
        this.components=components;
    }
}
