package GlobalTools.DataBean.Action;

/**
 * 用于描述事件类型
 */
public enum EventType {
    ACTION_TYPE_CLICK,ACTION_TYPE_LONG_CLOCLK,ACTION_TYPE_LOSE_FOCUS,ACTION_TYPE_GET_FOCUS;

    /**
     * 检查动作是否属于该结构体
     * @param eventType
     * @return
     */
    public static boolean CheckAction(EventType eventType){
        switch (eventType){
            case ACTION_TYPE_CLICK:
            case ACTION_TYPE_LONG_CLOCLK:
            case ACTION_TYPE_GET_FOCUS:
            case ACTION_TYPE_LOSE_FOCUS:
                return true;
                default:return false;

        }
    }

    /**
     * 获取选项的显示
     * @return
     */
    public static String[] getSelectionDisplay(){
        String[] strings=new String[4];
        strings[0]="点击";
        strings[1]="长按";
        strings[2]="失去焦点";
        strings[3]="获取焦点";
        return strings;
    }

    /**
     * 通过索引获取选项的内容
     * @param index
     * @return
     */
    public static EventType getActionTypeByIndex(int index){
        switch (index){
            case 0:
                return ACTION_TYPE_CLICK;
            case 1:
                return ACTION_TYPE_LONG_CLOCLK;
            case 2:
                return ACTION_TYPE_LOSE_FOCUS;
            case 3:
                return ACTION_TYPE_GET_FOCUS;
                default:return null;
        }
    }
}
