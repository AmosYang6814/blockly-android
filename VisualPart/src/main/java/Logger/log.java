package Logger;

/**
 * Created by Administrator on 2020/2/15.
 */

@FunctionalInterface
public interface log {

    /**
     * 输出
     * @param errorMessage
     */
    public void print(String errorMessage);
}
