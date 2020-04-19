package DataPersistence.FileStorage;

import com.google.gson.Gson;

import DataPersistence.AbstractStorage.PreserveRelation;
import DataPersistence.DataBean.Component.Component;
import DataPersistence.DataBean.RelationShip.Relation;

/**
 * Created by Administrator on 2020/2/2.
 */

public class saveRelation implements PreserveRelation {
    /**
     * 子文件夹包名
     */
    public static final String packageFile="relation/";
    @Override
    public boolean addRelation(Relation relation) {
        try {
            String data=new Gson().toJson(relation);
            String fileName=this.getFileName(relation.getId());
            String filePath=packageFile+"/"+fileName;
            DocumentTool.writtenFileData(filePath,data);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }

    @Override
    public boolean deleRelation(int linkId) {
        try {
            String[] datas=getFileContent(getFileName(linkId));
            int index=SreachInData(datas,linkId);

            datas[index]=null;
            String data=ChangeToString(datas);
            DocumentTool.writeData(packageFile+getFileName(linkId),data);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }

    @Override
    public boolean modifyRelation(int linkId, Relation relation) {
        try {
          String[] datas=getFileContent(getFileName(linkId));
          int index=SreachInData(datas,linkId);
          datas[index]=new Gson().toJson(relation);
          String data=ChangeToString(datas);
          DocumentTool.writeData(packageFile+getFileName(linkId),data);
          return true;
        } catch (Exception e) {
          e.printStackTrace();
          return false;
       }
    }

    /**
     * 通過ID獲取關係
     * @param linkId
     * @return
     */
    @Override
    public Relation getLinkById(int linkId) {
        try {
            String[] datas=getFileContent(getFileName(linkId));
            int index=SreachInData(datas,linkId);
            return new Gson().fromJson(datas[index],Relation.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取文件名
     * @param Id
     * @return
     */
    private String getFileName(int Id){
        return "component_"+(Id/10)+".data";
    }

    /**
     * 获取全部内容
     * @param fileName
     * @return
     */
    private String[] getFileContent(String fileName) throws Exception{
        String[] datas=DocumentTool.readFileContent(fileName).split(DocumentTool.OUT_SPLIT);
        return datas;
    }

    /**
     * 匹配项查询
     * @param datas
     * @return
     */
    private int SreachInData (String[] datas,int id) throws Exception{
        int i=0;
        Gson gson=new Gson();
        for(;i<datas.length;i++){
            if(gson.fromJson(datas[i],Relation.class).getId()==id)break;
        }
        return i;
    }

    /**
     * 将字符串数组拼接成字符串
     * @param datas
     * @return
     */
    private String ChangeToString(String[] datas){
        StringBuffer stringBuffer=new StringBuffer("");
       for(int i=0;i<datas.length;i++){
           if(datas[i]==null)continue;
           stringBuffer.append(datas[i]);
           stringBuffer.append(DocumentTool.OUT_SPLIT);
       }

       return stringBuffer.toString();
    }
}
