package DataPersistence.DataBean.Component;

/**
 * Created by Administrator on 2020/2/1.
 */

public class attribute {

    public attribute(String name, String value, String defValue) {
        this.name = name;
        this.value = value;
        this.defValue = defValue;
    }

    /**
     * 数据类型名字
     */
    String name;

    /**
     * 数据值
     * 为Gson字符串
     *
     */
    String value;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    String defValue;

    public String getDefValue() {
        return defValue;
    }

    public void setDefValue(String defValue) {
        this.defValue = defValue;
    }
}
