package DataPersistence.FileStorage;

/**
 * Created by Administrator on 2020/2/2.
 */

public class Fileproperties {

    static Fileproperties fileproperties=new Fileproperties();

    /**
     * 获取唯一实例
     * @return
     */
    public static Fileproperties getFileproperties() {
        return fileproperties;
    }

    /**
     * 存储的文件路径
     */
    String Filepath="";

    /**
     * 文件路径方法
     * @return
     */
    public String getFilepath() {
        return Filepath;
    }
    public void setFilepath(String filepath) {
        Filepath = filepath;
    }
}
