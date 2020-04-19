package GlobalTools;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import UI.Tools.domain.Module;
import UI.Tools.domain.SimpleComponentDescribe;

public class FunctionClassXMLParaser {

    static  Document document=null;


    /**
     * 程序加载时就需要调用
     * @param inputStream
     * @throws ParserConfigurationException
     * @throws IOException
     * @throws SAXException
     */
    public static void parser(InputStream inputStream) throws ParserConfigurationException, IOException, SAXException {
        //存放解析处理的组件信息
        LinkedList<Module> moduleLinkedList = new LinkedList<Module>();
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        document = builder.parse(inputStream);
    }


    public static  <T> T getClassName(String labelName){
        NodeList nodeList = document.getElementsByTagName(labelName);

        System.out.println(labelName+"功能模块个数"+nodeList.getLength());

        if(nodeList.getLength()==0)return null;
        String className=nodeList.item(0).getAttributes().getNamedItem("classpath").getNodeValue();

        /**
         * 反射类
         */
        try {
            Class functionClass=Class.forName(className);
            return (T)functionClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }



}
