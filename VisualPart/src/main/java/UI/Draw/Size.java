package UI.Draw;

/**
 * Created by Administrator on 2020/2/4.
 */

public class Size {

    static Size size=null;
    int width,height;

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public  static Size BuilderSize(int width, int height) {
        if(size==null){
            size=new Size();
            size.width=width;
            size.height=height;
        }
        return size;
    }

    public static Size getSize(){
        if(size==null)throw new NullPointerException();
        else return size;
    }

}
