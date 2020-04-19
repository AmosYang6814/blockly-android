package UI.UICenterCtrol;


import UI.ComponentIndex.AbstractDataManager;
import UI.EvenHanding.EvenManager;
import UI.ScreenStatus.ScreenManager;
import UI.ScreenStatus.ScreenNumberManager;

/**
 * 全局管理器
 */
public class UIGlobalManager {
    static EvenManager evenManager=new EvenManager();
    static AbstractDataManager dataManager=new AbstractDataManager();
    static UIGlobalManager uiGlobalManager=new UIGlobalManager();
    static ScreenNumberManager screenNumberManager=new ScreenNumberManager();



    public static ScreenNumberManager getScreenNumberManager(){
        return screenNumberManager;
    }

    public static EvenManager getEvenManager() {
        return evenManager;
    }

    public static AbstractDataManager getDataManager() {
        return dataManager;
    }

    public static UIGlobalManager getInstance() {
        return uiGlobalManager;
    }

}
