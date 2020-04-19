package Logic.Tools;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

/**
 * Created by wenjun on 2019/12/21 14:26
 */

public class LogicXMLParser {
    public void parser(String path)  {

        Element element= null;
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(path);
            element = document.getDocumentElement();
            NodeList nodeList=element.getChildNodes();

            Node module=null,server=null;
            for(int i=0;i<nodeList.getLength();i++){
                if(nodeList.item(i).getNodeName().equals("module"))module=nodeList.item(i);
                if(nodeList.item(i).getNodeName().equals("server"))server=nodeList.item(i);
            }

            NodeList moduleReflectList =getNoteByName(module.getChildNodes(),"Reflect").getChildNodes();
            NodeList moduleStatustList =getNoteByName(module.getChildNodes(),"StatusModule").getChildNodes();
            NodeList moduleActiontList =getNoteByName(module.getChildNodes(),"ActionModule").getChildNodes();

            getProperties.reflectToUiClass=getNoteByName(moduleReflectList,"classToUi").getFirstChild().getNodeValue();
            getProperties.reflectToDataClass=getNoteByName(moduleReflectList,"classToData").getFirstChild().getNodeValue();
            getProperties.ActionToUiClass=getNoteByName(moduleActiontList,"classToUi").getFirstChild().getNodeValue();
            getProperties.ActionToDataClass=getNoteByName(moduleActiontList,"classToData").getFirstChild().getNodeValue();
            getProperties.StatusToUiClass=getNoteByName(moduleStatustList,"classToUi").getFirstChild().getNodeValue();
            getProperties.StatsToDataClass=getNoteByName(moduleStatustList,"classToData").getFirstChild().getNodeValue();
            getProperties.port=Integer.valueOf(getNoteByName(server.getChildNodes(),"port").getFirstChild().getNodeValue());
            getProperties.linkCount=Integer.valueOf(getNoteByName(server.getChildNodes(),"linkMaxCount").getFirstChild().getNodeValue());

            /**
             * 设置初始化完成标志位
             */
            getProperties.isInit=true;
        } catch (ParserConfigurationException e) {
        } catch (SAXException e) {
        } catch (IOException e) {
        }
        finally {
            return;
        }
    }

    public static void main(String[] args){
       // new getProperties();
        //System.out.println(getProperties.ActionToUiClass);
    }


    /**
     * 根据名字获取节点
     * @param nodeList
     * @param name
     * @return
     */
    private static Node getNoteByName(NodeList nodeList,String name){
        for(int i=0;i<nodeList.getLength();i++){
            if(nodeList.item(i).getNodeName().equals(name))return nodeList.item(i);
        }
        return null;
    }

}
