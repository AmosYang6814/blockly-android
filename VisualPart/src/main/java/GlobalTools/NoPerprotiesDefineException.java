package GlobalTools;

public class NoPerprotiesDefineException extends Exception {
    @Override
    public void printStackTrace() {
        super.printStackTrace();
        System.out.println("未指定配置文件");
    }
}
