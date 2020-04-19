package UI.Draw;

/**
 * Created by Administrator on 2020/2/4.
 */

import java.util.ArrayList;
import java.util.List;

import GlobalTools.DataBean.UiComponent;

/**
 * 绘制接口
 */
public interface Draw<T> {

    /**
     * 获取屏幕的长宽
     * @return
     */
    public Size getScreenSize();

    /**
     * 简单组件的绘制
     * @param simpleComponent
     * @return
     */
    public boolean drawInscreen(UiComponent simpleComponent);



    public void MoveComponent(T view);
    /**
     * 刷新
     * @return
     */
    public boolean refresh();
}
