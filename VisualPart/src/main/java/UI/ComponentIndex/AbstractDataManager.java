package UI.ComponentIndex;

import android.util.Log;

import com.example.administrator.visualizationpart.Global.GlobalApplication;
import com.example.administrator.visualizationpart.Tools.StringTools;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import GlobalTools.DataBean.Attribute;
import GlobalTools.DataBean.UiComponent;
import GlobalTools.DataBean.Visibility;
import UI.Tools.SimpleComponentTool;
import UI.Tools.domain.*;

/**
 * 抽象层数据管理器，为具体实现层提供数据的交互
 */
public class AbstractDataManager {
    static AbstractDataManager abstractDataManager;


    /**
     * 返回唯一实例
     * @return
     */
    public static AbstractDataManager getAbstractDataManager(){
        if(abstractDataManager==null)abstractDataManager=new AbstractDataManager();
        return abstractDataManager;
    }

    /**
     * 实现层的测试方法，在之后需要重写该方法
     * @return
     */
    public LinkedList<UiComponent> getAllcomponent(){


        List<Module> list= SimpleComponentTool.getAllComponent();


        LinkedList<UiComponent>uiComponents=new LinkedList<>();
        for(Module m:list){
            if(m.getClassName().equals("all"))continue;

            UiComponent uiComponent=new UiComponent(UiComponent.ComponentType.simple,m.getClassName());
            uiComponent.setId(m.getModuleId());
            uiComponent.setVisiblity(Visibility.visible);
            uiComponent.setNearName(m.getDisplayName());
            uiComponent.setClassfiy(m.getClassifyItem().getContent());

            for(Attribute attribute:m.getAttributeLinkedList()){
                uiComponent.addDefineAttribute(attribute);
            }

            uiComponents.add(uiComponent);
            Log.i("display_module",m.toString());
        }
        return uiComponents;
    }

    //通过id返回数据
    public UiComponent findComponentById(int id){
        Module m=SimpleComponentTool.findModuleById(id);

        if(m==null)return null;

        UiComponent uiComponent=new UiComponent(UiComponent.ComponentType.simple,m.getClassName());
        uiComponent.setId(m.getModuleId());
        uiComponent.setVisiblity(Visibility.visible);
        uiComponent.setNearName(m.getDisplayName());
        uiComponent.setClassfiy(m.getClassifyItem().getContent());

        for(Attribute attribute:m.getAttributeLinkedList()){
            uiComponent.addDefineAttribute(attribute);
        }
        return uiComponent;
    }

    /**
     * 返回所有的分类
     * @return
     */
    public List<String> getAllClassfy(){
        List<ClassifyItem> list=SimpleComponentTool.getAllClassify();
        ArrayList<String> strings=new ArrayList<>();
        for(int i=0;i<list.size();i++){
            strings.add(list.get(i).getContent());
        }
        return strings;
    }

    public UiComponent findConponentByName(String classfyName,String componentName){

        if(GlobalApplication.Debug){
            Log.i("AbstractDataManager获取模块",classfyName+":"+componentName);
        }

        classfyName= StringTools.deleteSpaceAfterString(classfyName);
        componentName=StringTools.deleteSpaceAfterString(componentName);

        Module m=SimpleComponentTool.getModule(classfyName,componentName);
        UiComponent uiComponent=new UiComponent(UiComponent.ComponentType.simple,m.getClassName());
        uiComponent.setId(m.getModuleId());
        uiComponent.setVisiblity(Visibility.visible);
        uiComponent.setNearName(m.getDisplayName());
        uiComponent.setClassfiy(m.getClassifyItem().getContent());

        for(Attribute attribute:m.getAttributeLinkedList()){
            uiComponent.addDefineAttribute(attribute);
        }
        return uiComponent;
    }
}
