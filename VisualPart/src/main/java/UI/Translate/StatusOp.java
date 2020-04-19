package UI.Translate;

/**
 * Created by Administrator on 2020/2/4.
 */

import java.util.LinkedList;

import DataPersistence.DataBean.Status.Status;
import GlobalTools.DataBean.Screen;

/**
 * 获取所有的状态信息
 */
public interface StatusOp {

    /**
     * 获取所有的状态信息
     * @return
     */
    public LinkedList<Screen> getAllStatus();

    /**
     * 存储状态信息
     * @param screen
     * @return
     */
    public boolean saveStatus(Screen screen);

    /**
     * 删除状态信息
     * @param screen
     * @return
     */
    public boolean deleteStatus(Screen screen);

    public Screen getStatusById(int id);

}
