package UI.Tools;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.Random;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import GlobalTools.DataBean.Attribute;
import UI.Tools.domain.ClassifyItem;
import UI.Tools.domain.Module;
import UI.Tools.domain.SimpleComponentDescribe;

/**
 * 解析SimpleProperties文件
 */
public class UIComponentXMLParser {

    /**
     * xml文件对应的实体类
     */
    private SimpleComponentDescribe simpleComponentDescribe = new SimpleComponentDescribe();


    public SimpleComponentDescribe parser(InputStream inputStream) throws ParserConfigurationException, IOException, SAXException {
        //存放解析处理的组件信息
        LinkedList<Module> moduleLinkedList = new LinkedList<Module>();
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(inputStream);
        //解析module 标签
        NodeList modules = document.getElementsByTagName("module");
        for (int i = 0; i < modules.getLength(); i++) {
            //存放解析处理的属性信息
            LinkedList<Attribute> attributeLinkedList = new LinkedList<Attribute>();
            Element module = (Element) modules.item(i);
            //获取了第一层的属性module
            Node module_className = module.getAttributes().getNamedItem("className");
            Node module_displayName = module.getAttributes().getNamedItem("displayName");
            Node module_classify = module.getAttributes().getNamedItem("classify");
            //将解析的组件数据加入组件
            Module CreateModule = new Module();
            //设置分类信息的内容
            ClassifyItem classifyItem = new ClassifyItem();
            classifyItem.setId(module_classify!=null?module_classify.getNodeValue():"null");
            //设置组件数据
            //1.className
            CreateModule.setClassName(module_className!=null?module_className.getNodeValue():"null");
            //2. displayName
            CreateModule.setDisplayName(module_displayName!=null?module_displayName.getNodeValue():"null");
            //3.将分类信息加入生成组件
            CreateModule.setClassifyItem(classifyItem);
            //4.每个模块设置一个随机id，需要确定随机数种子，这样每次解析模块的id都是固定的
            CreateModule.setModuleId(new Random(10).nextInt(50) + i * 2);

            // System.out.println("模块信息" + module_className + "" + module_displayName + " " + module_classify);
            NodeList ModuleAttributes = module.getChildNodes();

            //输出attribute 的属性
            for (int j = 0; j < ModuleAttributes.getLength(); j++) {
                if (ModuleAttributes.item(j).getNodeType() == Node.ELEMENT_NODE) {
                    Node attribute_name = ModuleAttributes.item(j).getAttributes().getNamedItem("name");
                    Node attribute_inputTypeForm = ModuleAttributes.item(j).getAttributes().getNamedItem("inputTypeForm");
                    Node attribute_reflectMethod = ModuleAttributes.item(j).getAttributes().getNamedItem("reflectMethod");
                    Node attribute_passingForm = ModuleAttributes.item(j).getAttributes().getNamedItem("passingForm");
                    Node attribute_classForm=ModuleAttributes.item(j).getAttributes().getNamedItem("classForm");

                    //设置获取的组件属性
                    Attribute CreateAttribute = new Attribute();
                    CreateAttribute.setName(attribute_name.getNodeValue());

                    CreateAttribute=Attribute.ParaseDataFromString(CreateAttribute,attribute_passingForm.getNodeValue(),attribute_inputTypeForm.getNodeValue(),attribute_classForm.getNodeValue());

                    CreateAttribute.setReflectMethod(attribute_reflectMethod.getNodeValue());
                    attributeLinkedList.add(CreateAttribute);
                }
                //将属性信息加入到模块
                CreateModule.setAttributeLinkedList(attributeLinkedList);
            }
            //将生成的模块封装到SimpleComponentDiscrible
            moduleLinkedList.add(CreateModule);
        }

        //classify 标签
        NodeList classifies = document.getElementsByTagName("classify");
        for (int k = 0; k < classifies.getLength(); k++) {
            //存放解析处理的分类信息
            LinkedList<ClassifyItem> classifyItemLinkedList = new LinkedList<ClassifyItem>();
            Element classify = (Element) classifies.item(k);
            //输出attribute 的属性
            NodeList classifyItems = classify.getChildNodes();
            for (int j = 0; j < classifyItems.getLength(); j++) {
                if (classifyItems.item(j).getNodeType() == Node.ELEMENT_NODE) {
                    Node Item_id = classifyItems.item(j).getAttributes().getNamedItem("id");
                    String content = classifyItems.item(j).getFirstChild().getNodeValue();
                    //  System.out.println("测试获取内容---"+content);
                    //获取的分类信息封装到实体类
                    ClassifyItem createClassifyItem = new ClassifyItem();
                    createClassifyItem.setId(Item_id.getNodeValue());
                    createClassifyItem.setContent(content);
                    // 将分类item加入到classifyItemLinkedList
                    classifyItemLinkedList.add(createClassifyItem);
                    //     System.out.println("分类信息" + Item_id);
                }
            }


            //将整个文件生成的分类信息加入到simpleComponentDiscrible
            simpleComponentDescribe.setClassifyItemLinkedList(classifyItemLinkedList);
            //将生成的模块封装到SimpleComponentDiscrible
            simpleComponentDescribe.setModuleLinkedList(getContentById(classifyItemLinkedList, moduleLinkedList));

        }
        return simpleComponentDescribe;
    }

    /**
     * 去除字符串的括号
     * @param tempString
     * @return
     */
    private String removeBrackets(String tempString){
        int start=tempString.indexOf("(")+1;
        int end=tempString.indexOf(")");
        return tempString.substring(start,end);
    }

    /**
     * 根据分类id，找到分类内容,并设置模块对应的分类内容
     *
     * @return
     */
    private LinkedList<Module> getContentById(LinkedList<ClassifyItem> classifies, LinkedList<Module> modules) {
        String classifyId = null;
        String tempId = null;
        for (Module module : modules) {
            for (ClassifyItem classify : classifies) {

                classifyId = module.getClassifyItem().getId();
                tempId = classify.getId();
                if (classifyId != null) {
                    int start = classifyId.indexOf("@") + 1;
                    classifyId = classifyId.substring(start).trim();
                }
                if (tempId != null) {
                    tempId = tempId.trim();
                }
                //找到id相同的，可以设置
                if (tempId.equals(classifyId)) {
                    module.setClassifyItem(new ClassifyItem(classifyId, classify.getContent()));
                }
            }
        }
        return modules;
    }

}