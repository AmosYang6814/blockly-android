package UI.Tools;


import org.xml.sax.SAXException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import javax.xml.parsers.ParserConfigurationException;

import GlobalTools.GlobalManager;
import UI.Tools.domain.ClassifyItem;
import UI.Tools.domain.Module;
import UI.Tools.domain.SimpleComponentDescribe;

/**
 * Date:2020/3/23
 * Time:20:33
 * author:wenjun
 * 显示层组件的工具类
 */
public class SimpleComponentTool {

    /**
     * 解析xml获取状态
     * @param
     * @return
     */
     public static SimpleComponentDescribe getStatusFromXML(InputStream inputStream){
        UIComponentXMLParser uiComponentXMLParser=new UIComponentXMLParser();
        SimpleComponentDescribe status=null;
         try {
              status = uiComponentXMLParser.parser(inputStream);
             inputStream.close();
         } catch (ParserConfigurationException e) {
             e.printStackTrace();
         } catch (IOException e) {
             e.printStackTrace();
         } catch (SAXException e) {
             e.printStackTrace();
         }

         return  status;
     }

    /**
     * 获取当前文件的分类信息
     * @param
     * @return
     */
     public static List<ClassifyItem> getAllClassify(){
         SimpleComponentDescribe status = getStatusFromXML(GlobalManager.getPerproties().getSimpleComponentDefineFile());
         LinkedList<ClassifyItem> classifyItems = status.getClassifyItemLinkedList();
         return classifyItems;
     }

    /**
     * 根据分类信息和组件名字输出组件
     * @param
     * @param classifyName  分类名
     * @param displayName   显示的名字
     * @return
     */
     public static Module getModule(String classifyName,String displayName){

         SimpleComponentDescribe status = getStatusFromXML(GlobalManager.getPerproties().getSimpleComponentDefineFile());
         Module res=null;
         List<Module> modules=status.getModuleLinkedList();
         for (Module module:modules){
             String moduleClassifyName=module.getClassifyItem().getContent();
             String moduleDisplayName=module.getDisplayName();
             if (moduleDisplayName!=null){
                 moduleDisplayName=moduleDisplayName.trim();
             }
             if (moduleClassifyName!=null){
                 moduleClassifyName=moduleClassifyName.trim();
             }
             if (classifyName.equals(moduleClassifyName)&&
             displayName.equals(moduleDisplayName)){
                 res=module;
             }
         }
         return res;
     }


    /**
     * 根据分类名获取使用此类别的所有组件名
     * @param classifyName
     * @return
     */
     public static List<String> getComponentByClassifyName(String classifyName){
         SimpleComponentDescribe status = getStatusFromXML(GlobalManager.getPerproties().getSimpleComponentDefineFile());
         LinkedList<Module> modules = status.getModuleLinkedList();
         List<String> list=new ArrayList<String>();
         for (Module module:modules){
             String moduleClassifyName=module.getClassifyItem().getContent();
             if (moduleClassifyName!=null){
                 moduleClassifyName=moduleClassifyName.trim();
             }
             if (classifyName.equals(moduleClassifyName)&&module.getClassifyItem().getId()!=null){
                 list.add(module.getClassName());
             }
         }
         return  list;

     }

    /**
     * 使用模块id查询模块
     * @param
     * @param id
     * @return
     */
    public static Module findModuleById(int id){
        SimpleComponentDescribe status = getStatusFromXML(GlobalManager.getPerproties().getSimpleComponentDefineFile());
        LinkedList<Module> moduleLinkedList = status.getModuleLinkedList();
        for (Module module:moduleLinkedList){
            if (id==module.getModuleId()){
                return module;
            }
        }
        return null;
    }

    /**
     * 获取xml中的全部组件
     * @param
     * @return
     */
    public static LinkedList<Module> getAllComponent(){
        SimpleComponentDescribe status = getStatusFromXML(GlobalManager.getPerproties().getSimpleComponentDefineFile());
        return status.getModuleLinkedList();
    }

}
