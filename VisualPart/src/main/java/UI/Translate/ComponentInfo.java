package UI.Translate;

/**
 * Created by Administrator on 2020/2/4.
 */

import java.util.ArrayList;
import java.util.LinkedList;

import GlobalTools.DataBean.UiComponent;

/**
 * 提供所有的组件信息
 */
public interface ComponentInfo {

    /**
     * 获取所有的简单组件信息
     * @return
     */
    public LinkedList<UiComponent> getAllSimpleComponent();

    /**
     * 获取所有的复杂组件信息
     * @return
     */
    public LinkedList<UiComponent> getAllComplexCompoent();

    /**
     * 获取复杂组件中的简单组件
     * @param ComponentId
     * @return
     */
    public UiComponent getComplexComponentById(int ComponentId);

    /**
     * 根据组件的id获取组件
     * @param ComponentId
     * @return
     */
    public UiComponent getSimpleComponentById(int ComponentId);
}
