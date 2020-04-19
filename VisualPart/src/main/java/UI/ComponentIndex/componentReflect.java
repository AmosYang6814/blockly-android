package UI.ComponentIndex;

import GlobalTools.DataBean.UiComponent;

/**
 * 组件反射器接口
 */
@FunctionalInterface
public interface componentReflect {
    public Object getComponent(UiComponent uiComponent);
}
