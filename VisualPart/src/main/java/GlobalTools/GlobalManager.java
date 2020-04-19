package GlobalTools;

import DataPersistence.CenterControl.IdManager;

/**
 * Created by Administrator on 2020/2/19.
 */

/*全局管理器，提供多种管理器的调用*/
public class GlobalManager {

    static Perproties perproties;

    public static Perproties getPerproties() {

        try {
            if(perproties==null)throw new NoPerprotiesDefineException();
        } catch (NoPerprotiesDefineException e) {
            e.printStackTrace();
            System.exit(0);
        }
        return perproties;
    }

    public static void registerPerproties(Perproties perproties)  {

        GlobalManager.perproties = perproties;
    }

    /**
     * 注册屏幕的id
     * @return
     */
    public static int registerScreen(){
        return IdManager.getIdManager().registerStatusInOtherLayer();
    }

    /**
     * 注册动作的id
     */
    public  static  int registerAction(){
        return IdManager.getIdManager().registerRelationInOtherLayer();
    }

}
