package UI.ComponentIndex;

/**
 * 组件列表的接口，在安卓具体实现该接口
 */

@FunctionalInterface
public interface componentListinterface<T> {
    /**
     * 显示全部组件
     */
    public  void setClickToListPopMenu(T object,String...name);

}
