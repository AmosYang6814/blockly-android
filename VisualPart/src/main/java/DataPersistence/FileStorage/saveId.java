package DataPersistence.FileStorage;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;


/**
 * ID常驻内存，因此无须拆分成小文件
 */
import DataPersistence.AbstractStorage.PreserveId;

/**
 * Created by Administrator on 2020/2/2.
 */

public class saveId implements PreserveId {
    final String PACKAGEFILE="ID/";
    final String COMPONENT_ID_FILE=PACKAGEFILE+"componentId.data";
    final String RELATION_ID_FILE=PACKAGEFILE+"relationId.data";
    final String STATUS_ID_FILE=PACKAGEFILE+"statusId.data";



    @Override
    public boolean addNewID(int type, int id) {
        try {
            HashSet<Integer> hashSet=getAllIdFromFile(type);
            hashSet.add(id);
            return storeAllId(type,hashSet);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }

    @Override
    public HashSet<Integer> getALLId(int type) {
        try {
            return getAllIdFromFile(type);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean deleteID(int type, int id) {
        try {
            HashSet<Integer> hashSet=getAllIdFromFile(type);
            hashSet.remove(id);
            return storeAllId(type,hashSet);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean clear(int type) {
        try {
            HashSet<Integer> hashSet=getAllIdFromFile(type);
            hashSet.clear();
            return storeAllId(type,hashSet);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean refreshAllId(int type, Set<Integer> collection) {
        try {
            HashSet<Integer> hashSet=getAllIdFromFile(type);
            hashSet.clear();
            for(Integer integer:collection)hashSet.add(integer);
            return storeAllId(type,hashSet);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    /**
     * 初始化文件夹
     * @return
     */
    @Override
    public boolean InitFile() {
        if(!DocumentTool.isFolderExists(PACKAGEFILE)){
            DocumentTool.addFolder(PACKAGEFILE);
        }
        if(!DocumentTool.isFileExists(COMPONENT_ID_FILE))DocumentTool.addFile(COMPONENT_ID_FILE);
        if(!DocumentTool.isFileExists(STATUS_ID_FILE))DocumentTool.addFile(STATUS_ID_FILE);
        if(!DocumentTool.isFileExists(RELATION_ID_FILE))DocumentTool.addFile(RELATION_ID_FILE);
        return false;
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
     * 根据类型从文件中获取所有的
     * @param type
     * @return
     * @throws Exception
     */
    private HashSet<Integer> getAllIdFromFile (int type) throws Exception{
        HashSet<Integer> hashSet=new HashSet<>();
        String[] datas=null;
        switch (type){
            case PreserveId.ID_TYPE_COMPONENT:
                datas=getFileContent(COMPONENT_ID_FILE);
                break;
            case PreserveId.ID_TYPE_STATUS:
                datas=getFileContent(STATUS_ID_FILE);
                break;
            case PreserveId.ID_TYPE_RELATION:
                datas=getFileContent(RELATION_ID_FILE);
                break;
        }

        for(int i=0;i<datas.length;i++){

            if(datas[i].length()==0)continue;
            hashSet.add(Integer.valueOf(datas[i]));
        }
        return hashSet;
    }

    /**
     * 存储所有的ID的值
     * @param type
     * @param hashSet
     * @return
     */
    private boolean storeAllId(int type,HashSet<Integer> hashSet){
        try {
            String[] datas=new String[hashSet.size()];
            Iterator<Integer> iterator=hashSet.iterator();
            for(int i=0;i<hashSet.size();i++){
                datas[i]=String.valueOf(iterator.next());
            }

            switch (type){
                case PreserveId.ID_TYPE_COMPONENT:
                    DocumentTool.writeData(COMPONENT_ID_FILE,ChangeToString(datas));
                    break;
                case PreserveId.ID_TYPE_STATUS:
                    DocumentTool.writeData(STATUS_ID_FILE,ChangeToString(datas));
                    break;
                case PreserveId.ID_TYPE_RELATION:
                    DocumentTool.writeData(RELATION_ID_FILE,ChangeToString(datas));
                    break;
            }

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
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
