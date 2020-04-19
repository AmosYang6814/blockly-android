package DataPersistence.AbstractStorage;

import DataPersistence.DataBean.RelationShip.Relation;

/**
 * Created by Administrator on 2020/2/1.
 */

public interface PreserveRelation {

    /**
     * 存储关系
     * @param Relation
     * @return
     */
    public boolean addRelation(Relation Relation);

    /**
     * 删除链接
     * @param linkId
     * @return
     */
    public boolean deleRelation(int linkId);

    /**
     * 修改链接
     * @param linkId
     * @param Relation
     * @return
     */
    public boolean modifyRelation(int linkId,Relation Relation);

    /**
     * 通过id查询链接
     * @param linkId
     * @return
     */
    public Relation getLinkById(int linkId);

}
