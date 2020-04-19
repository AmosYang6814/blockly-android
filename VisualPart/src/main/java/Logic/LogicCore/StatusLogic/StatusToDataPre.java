package Logic.LogicCore.StatusLogic;

import java.util.List;

import DataPersistence.DataBean.Status.Status;
import GlobalTools.DataBean.Screen;
import GlobalTools.DataBean.UiComponent;

/**
 * Created by Administrator on 2020/2/19.
 */

/*面向数据持久化层的接口*/
public interface StatusToDataPre {

    /**
     * 获取全部的组件
     * @return
     */
    List<Screen> getAllStatus () throws Exception;

    /**
     * 根据ID获取组件
     * @return
     */
    Screen getStatusById(int id) throws Exception;

    /**
     * 存储组件
     * @param screen
     * @return
     */
    boolean saveStatus (Screen screen) throws Exception;

    /**
     * 删除组件
     * @param screen
     * @return
     */
    boolean deleteStatus(Screen screen) throws Exception;

}
