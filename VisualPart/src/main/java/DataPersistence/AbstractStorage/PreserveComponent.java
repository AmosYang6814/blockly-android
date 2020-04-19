package DataPersistence.AbstractStorage;

import DataPersistence.DataBean.Component.Component;

/**
 * Created by Administrator on 2020/2/1.
 */

public interface PreserveComponent {

    /**
     * 存储复杂模块的方法
     * @return
     */
    public boolean addComplexComponent(Component component);

    /**
     * 存储简单模块的方法
     * @return
     */
    public boolean addSimpleComponent(Component component);

    /**
     * 修改组件模块
     * @param componentId
     * @param component
     * @return
     */
    public boolean modifyComponent(int componentId,Component component);

    /**
     * 通过组件的id获取组件
     * @param ComponentId
     * @return
     */
    public Component getComponentById(int ComponentId);

    /**
     * 通过id删除组件
     * @param ComponentId
     * @return
     */
    public Component deleteComponentById(int ComponentId);

}
