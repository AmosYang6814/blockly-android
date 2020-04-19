package UI.Tools;

import java.util.LinkedList;
import java.util.SimpleTimeZone;

import GlobalTools.GlobalManager;
import UI.Tools.domain.Module;
import UI.Tools.domain.SimpleComponentDescribe;

public class ClassNotDefineException extends Exception {


    /**
     * 判断给定的xml文件的组件是否存在
     * @param
     * @param className
     * @return
     */
    public boolean isRight(String className) {
        SimpleComponentDescribe status = SimpleComponentTool.getStatusFromXML(GlobalManager.getPerproties().getSimpleComponentDefineFile());
        LinkedList<Module> modules = status.getModuleLinkedList();
        for (Module module : modules) {
            if (module.getClassName().equals(className)) {
                return true;
            }
        }
        printStackTrace();
        return false;
    }

    @Override
    public void printStackTrace() {
        super.printStackTrace();
        System.out.println("类型超出定义");
    }
}
