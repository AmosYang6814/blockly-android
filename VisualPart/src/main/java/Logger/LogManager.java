package Logger;

/**
 * Created by Administrator on 2020/2/15.
 */

/**
 * 日志系统
 */
public class LogManager {
    static log Mylogger;

    public static void print(String errorMessage){
        Mylogger.print(errorMessage);
    }
}
