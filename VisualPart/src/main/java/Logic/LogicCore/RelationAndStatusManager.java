package Logic.LogicCore;

import Logic.LogicCore.RelationLogic.RelationToDataPre;
import Logic.LogicCore.StatusLogic.StatusServiceToUi;
import Logic.LogicCore.StatusLogic.StatusToDataPre;
import UI.Translate.RelationOp;
import UI.Translate.StatusOp;

import Logic.Tools.*;

/**
 * 状态和链接的管理器
 */
public class RelationAndStatusManager {


    static RelationAndStatusManager relationAndStatusManager=new RelationAndStatusManager();

    RelationToDataPre relationToDataPre;
    RelationOp relationOp;

    StatusToDataPre statusToDataPre;
    StatusOp statusOp;


    public RelationToDataPre getRelationToDataPre() {
        return relationToDataPre;
    }

    public RelationOp getRelationOp() {
        return relationOp;
    }

    public StatusToDataPre getStatusToDataPre() {
        return statusToDataPre;
    }

    public StatusOp getStatusOp() {
        return statusOp;
    }


    RelationAndStatusManager(){
        initRelationAndStatus();
    }


    /**
     * 获取该唯一实例的方法
     * @return
     */
    public static RelationAndStatusManager getInstance(){
        return relationAndStatusManager;
    }

    /**
     * 获取唯一实例
     */

    private boolean initRelationAndStatus(){
        try {
            Class relationToUiClass=Class.forName(getProperties.ActionToUiClass);
            Class relationToData=Class.forName(getProperties.ActionToDataClass);
            Class statusToUi=Class.forName(getProperties.StatusToUiClass);
            Class statusToData=Class.forName(getProperties.StatsToDataClass);

            relationOp=(RelationOp) relationToUiClass.newInstance();
            relationToDataPre=(RelationToDataPre) relationToData.newInstance();

            statusOp=(StatusOp) statusToUi.newInstance();
            statusToDataPre=(StatusToDataPre) statusToData.newInstance();

            return true;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        } catch (InstantiationException e) {
            e.printStackTrace();
            return false;
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return false;
        }
    }

}
