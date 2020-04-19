package UI.ComponentIndex;

import GlobalTools.DataBean.UiComponent;

/**
 * 属性列表接口
 */
@FunctionalInterface
public interface attributeList {

    String[] getAllattributeName(UiComponent uiComponent);
}