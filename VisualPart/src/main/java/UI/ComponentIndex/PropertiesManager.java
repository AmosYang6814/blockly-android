package UI.ComponentIndex;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * 配置文件管理器
 */
public class PropertiesManager {
    static ArrayList<UiAttribute> simpleComponentAttributeDefine=new ArrayList<>();


    /**
     * 获取属性链
     */

    public static ArrayList<UiAttribute> getUiAttributeByComponentName(String componentName){
        ArrayList<UiAttribute> result=new ArrayList<>();

        for(UiAttribute uiAttribute:simpleComponentAttributeDefine){
            if(uiAttribute.getComponentName().equals(componentName))result.add(uiAttribute.clone());
        }
        return simpleComponentAttributeDefine;
    }


    /**
     * 从配置文件中获取数据
     */
    private static void getDataFromProperties(){
    }




}
