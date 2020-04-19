package DataPersistence.FileStorage;

import com.google.gson.Gson;

import DataPersistence.AbstractStorage.PreserveStatus;
import DataPersistence.DataBean.Status.Status;

/**
 * Created by Administrator on 2020/2/2.
 */

public class saveStatus implements PreserveStatus {
    public static final String packageFile="status/";
    @Override
    public boolean addStatus(Status status) {
        try {
            String data=new Gson().toJson(status);
            String fileName=this.getFileName(status.getId());
            String filePath=packageFile+"/"+fileName;
            DocumentTool.writtenFileData(filePath,data);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean deleteStatus(int statusId) {
        try {
            String[] datas=getFileContent(getFileName(statusId));
            int index=SreachInData(datas,statusId);

            datas[index]=null;
            String data=ChangeToString(datas);
            DocumentTool.writeData(packageFile+getFileName(statusId),data);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean modifyStatus(int statusId, Status status) {
        try {
            String[] datas=getFileContent(getFileName(statusId));
            int index=SreachInData(datas,statusId);
            datas[index]=new Gson().toJson(status);
            String data=ChangeToString(datas);
            DocumentTool.writeData(packageFile+getFileName(statusId),data);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Status getStatusById(int statusId) {
        try {
            String[] datas=getFileContent(getFileName(statusId));
            int index=SreachInData(datas,statusId);
            return new Gson().fromJson(datas[index],Status.class);
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
            if(gson.fromJson(datas[i],Status.class).getId()==id)break;
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
