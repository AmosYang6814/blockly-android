package Logic.Reflect;

/**
 * Created by Administrator on 2020/2/14.
 */

import java.util.List;

import GlobalTools.DataBean.UiComponent;

/**
 * 实现的功能：
 *   1.传递的组件转化成
 */
public interface ReflectToDataPe {
    /**
     * 获取全部的组件
     * @return
     */
    List<UiComponent> getAllComponent () throws Exception;

    /**
     * 根据ID获取组件
     * @return
     */
    UiComponent getComponentById(int id) throws Exception;

    /**
     * 存储组件
     * @param uicomponent
     * @return
     */
    boolean saveComponent(UiComponent uicomponent) throws Exception;

    /**
     * 删除组件
     * @param uicomponent
     * @return
     */
    boolean deleteComponent(UiComponent uicomponent) throws Exception;



}
