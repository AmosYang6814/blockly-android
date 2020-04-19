package GlobalTools.DataBean.Action;

/**
 * 表示动作含义，动作的含义总共有三种：
 * 1.跳转到其他的界面
 * 2.激活某一组件
 * 3.改变当前界面下若干Compoet下面的属性
 */
public enum ActionMean {
    ACTION_MEAN_JUMP_TO_ANOTHER,ACTION_MEAN_ACTIVE_COMPONENT,ACTION_MEAN_CHANGE_ATTRIBUTE;

    public boolean Check(ActionMean action){
        switch (action){
            case ACTION_MEAN_ACTIVE_COMPONENT:
            case ACTION_MEAN_JUMP_TO_ANOTHER:
            case ACTION_MEAN_CHANGE_ATTRIBUTE:
                return true;
                default:return false;
        }
    }

    /**
     * 返回选项的字符串显示
     * @return
     */
    public static String[] getSeletionDisplay(){
        String[] strings=new String[3];
        strings[0]="跳转到其他页面";
        strings[1]="激活组件";
        strings[2]="修改组件的属性";
        return strings;
    }

    /**
     * 通过索引返回意义类型
     * @param index
     * @return
     */
    public static ActionMean getActionMeanByIndex(int index){
        switch (index){
            case 0:return ACTION_MEAN_JUMP_TO_ANOTHER;
            case 1:return ACTION_MEAN_ACTIVE_COMPONENT;
            case 2:return ACTION_MEAN_CHANGE_ATTRIBUTE;
            default:return null;
        }
    }
}
