package DataPersistence.DataBean.Component;

import GlobalTools.DataBean.Visibility;

/**
 * Created by Administrator on 2020/2/1.
 */

public interface Component {
    static final int COMPONENT=10001;
    static final int COMPLEX_COMPONENT_TYPE=01;
    static final int SIMPLE_COMPONENT_TYPE=02;

    int getId();

    void setId(int id);

    /**
     * 获取组件类型
     * @return
     */
    int getType();

    String getNearName();
    void setNearName(String s);

    void setVisiblity(Visibility visble);
    Visibility getVisiblity();
}
