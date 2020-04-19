package DataPersistence.AbstractStorage;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Administrator on 2020/2/1.
 */

public interface PreserveId {
    public static final int ID_TYPE_COMPONENT=03;
    public static final int ID_TYPE_STATUS=04;
    public static final int ID_TYPE_RELATION=05;

    /**
     * 添加指定类型的新的id
     * @param type
     * @param id
     * @return
     */
    boolean addNewID(int type,int id);

    /**
     * 返回指定类型的全部组建id
     * @param type
     * @return
     */
    HashSet<Integer> getALLId(int type);

    /**
     * 删除id
     * @param type
     * @param id
     * @return
     */
    boolean deleteID(int type,int id);

    /**
     * 清空指定类型的全部id
     * @param type
     * @return
     */
    boolean clear(int type);

    boolean refreshAllId(int type,Set<Integer> collection);

    boolean InitFile();
}
