package DataPersistence.AbstractStorage;

import DataPersistence.DataBean.Status.Status;

/**
 * Created by Administrator on 2020/2/1.
 */

public interface PreserveStatus {

    /**
     * 增加状态
     * @param status
     * @return
     */
    public boolean addStatus(Status status);

    /**
     * 删除状态id
     * @param statusId
     * @return
     */
    public boolean deleteStatus(int statusId);

    /**
     * 修改状态
     * @param statusId
     * @param status
     * @return
     */
    public boolean modifyStatus(int statusId,Status status);

    /**
     * 查询状态
     * @param statusId
     * @return
     */
    public Status getStatusById(int statusId);

}
