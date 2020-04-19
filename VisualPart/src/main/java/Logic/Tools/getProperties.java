package Logic.Tools;

import java.io.File;

/**
 * 通过配置文件获取相应的配置
 */
public class getProperties {

    protected static boolean isInit=false;
    public static String reflectToUiClass;
    public static String reflectToDataClass;
    public static String StatusToUiClass;
    public static String StatsToDataClass;
    public static String ActionToUiClass;
    public static String ActionToDataClass;

    public static int port;
    public static int linkCount;


    /**
     * 为第一个加载的类
     */
    getProperties(){
        String[] propertiesPath={"/Users/amos/StudioProjects/VisualizationPart/app/src/main/java/SimpleProperties.bk.xml","/Users/amos/StudioProjects/VisualizationPart/app/src/main/java/Logic/SimpleProperties.bk.xml"};

        for(String s:propertiesPath){
            if(isInit)break;
            new LogicXMLParser().parser(s);
        }
    }

}
