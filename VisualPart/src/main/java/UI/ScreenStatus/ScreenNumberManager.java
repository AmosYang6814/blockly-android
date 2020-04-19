package UI.ScreenStatus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import GlobalTools.DataBean.Screen;
import GlobalTools.FunctionClassXMLParaser;

public class ScreenNumberManager {
    HashMap<String,Screen> hashMap=new HashMap<>();
    final ScreenOprate screenOprate= FunctionClassXMLParaser.<ScreenOprate>getClassName("ScreenOprate");

    //添加屏幕
    public boolean addScreen(Screen screen){
        try {
            hashMap.put(screen.getSreenName(),screen);
            screenOprate.notifidScreenNumberChaged(screen.getSreenName());  //通知屏幕数量更新
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    //获取屏幕
    public List<Screen> getAllScreen(){
        ArrayList<Screen> arrayList=new ArrayList<>();
        for(Map.Entry<String,Screen> entry:hashMap.entrySet()){
            arrayList.add(entry.getValue());
        }

        return arrayList;
    }

    /**
     * 根据组件的名字返回组件
     * @param name
     * @param <T>
     * @return
     */
    public <T> Screen<T> getScreenByName(String name){
        return hashMap.get(name);
    }


}

