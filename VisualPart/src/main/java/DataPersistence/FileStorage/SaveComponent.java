package DataPersistence.FileStorage;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;

import DataPersistence.AbstractStorage.PreserveComponent;
import DataPersistence.DataBean.Component.Component;

/**
 * Created by Administrator on 2020/2/2.
 */


/**
 * 1.文件存储机制，使用Json转化器，将数据转花成Json字符串存储
 * 2.每一个小文件存储10个组件
 *
 */

public class SaveComponent implements PreserveComponent {

    /**
     * 子文件夹包名
     */
    public static final String packageFile="component";


    /**
     * 添加组件方法
     */
    @Override
    public boolean addComplexComponent(Component component) {
        try {
            String data=new Gson().toJson(component);
            String fileName=this.getFileName(component.getId());
            String filePath=packageFile+"/"+fileName;
            DocumentTool.writtenFileData(filePath,data);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 添加简单组件
     * @param component
     * @return
     */
    @Override
    public boolean addSimpleComponent(Component component) {
        return addComplexComponent(component);
    }

    /**
     * 修改组件
     * @param componentId
     * @param component
     * @return
     */
    @Override
    public boolean modifyComponent(int componentId, Component component) {
        try {
            String[] datas=DocumentTool.readFileContent(this.getFileName(componentId)).split(DocumentTool.OUT_SPLIT);

            Gson gson=new Gson();

            int i=0;
            for(;i<datas.length;i++){
                if(gson.fromJson(datas[i],Component.class).getId()==componentId)break;
            }

            datas[i]=gson.toJson(component);

            StringBuffer stringBuffer=new StringBuffer("");
            for(int n=0;n<datas.length;n++){
                stringBuffer.append(datas[i]);
                stringBuffer.append(DocumentTool.OUT_SPLIT);
            }

            DocumentTool.writeData(getFileName(componentId),stringBuffer.toString());

            return true;
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 根据ID获取组件
     * @param componentId
     * @return
     */
    @Override
    public Component getComponentById(int componentId) {
        try {
            String[] datas=DocumentTool.readFileContent(this.getFileName(componentId)).split(DocumentTool.OUT_SPLIT);

            Gson gson=new Gson();

            int i=0;
            for(;i<datas.length;i++){
                if(gson.fromJson(datas[i],Component.class).getId()==componentId)break;
            }
            return gson.fromJson(datas[i],Component.class);
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 根据ID删除组件
     * @param componentId
     * @return
     */
    @Override
    public Component deleteComponentById(int componentId) {
        try {
            String[] datas=DocumentTool.readFileContent(this.getFileName(componentId)).split(DocumentTool.OUT_SPLIT);
            Gson gson=new Gson();
            int i=0;
            for(;i<datas.length;i++){
                if(gson.fromJson(datas[i],Component.class).getId()==componentId)break;
            }

            Component component=gson.fromJson(datas[i],Component.class);
            datas[i]=null;

            StringBuffer stringBuffer=new StringBuffer("");
            for(int n=0;n<datas.length;n++){
                if(datas[i]==null)continue;
                stringBuffer.append(datas[i]);
                stringBuffer.append(DocumentTool.OUT_SPLIT);
            }

            DocumentTool.writeData(getFileName(componentId),stringBuffer.toString());
            return component;
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
            return null;
        }
    }


    /**
     * 从组件id中转化出文件名
     * @param componnentId
     * @return
     */
    private String getFileName(int componnentId){
        return "component_"+(componnentId/10)+".data";
    }


}
