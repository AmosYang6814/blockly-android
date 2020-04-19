package UI.Tools;

public class AttributeFormatException extends RuntimeException {


    @Override
    public void printStackTrace() {
        super.printStackTrace();
        System.out.println("属性格式错误");
    }
}
