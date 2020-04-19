package Logic.LogicCore.StatusLogic;

import java.util.LinkedList;

import DataPersistence.DataBean.Status.Status;
import GlobalTools.DataBean.Screen;
import UI.Translate.StatusOp;

/**
 * Created by Administrator on 2020/2/19.
 */

/*向UI层提供服务*/
public class StatusServiceToUi implements StatusOp {
    StatusChageCore statusChageCore=null;

    public StatusServiceToUi(){
        statusChageCore=new StatusChageCore();
    }


    @Override
    public LinkedList<Screen> getAllStatus() {
        try {
            return statusChageCore.getAllStatus();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean saveStatus(Screen screen) {
        try {
            return statusChageCore.saveStatus(screen);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean deleteStatus(Screen screen) {
        try {
            return statusChageCore.deleteStatus(screen);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Screen getStatusById(int id) {
        return statusChageCore.getStatusById(id);
    }
}
