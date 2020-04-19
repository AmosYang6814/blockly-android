package UI.ComponentIndex;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * 从配置文件中获取的内容
 */
public class UiAttribute {
    String componentName;
    String attributeDisplayName;
    String methodName;
    int parameterNumber;

    public UiAttribute(String componentName, String attributeDisplayName, String mnethodName, int parameterNumber) {
        this.componentName = componentName;
        this.attributeDisplayName = attributeDisplayName;
        this.methodName = mnethodName;
        this.parameterNumber = parameterNumber;
    }

    public UiAttribute(String componentName, String attributeDisplayName, String mnethodName, int parameterNumber,HashMap<Integer,Object> parameters) {
        this.componentName = componentName;
        this.attributeDisplayName = attributeDisplayName;
        this.methodName = methodName;
        this.parameterNumber = parameterNumber;
        this.parameters=(HashMap<Integer, Object>) parameters.clone();
    }

    //public UiAttribute(){};

    public String getComponentName() {
        return componentName;
    }

    public void setComponentName(String componentName) {
        this.componentName = componentName;
    }

    public String getAttributeDisplayName() {
        return attributeDisplayName;
    }

    public void setAttributeDisplayName(String attributeDisplayName) {
        this.attributeDisplayName = attributeDisplayName;
    }

    public String getNethodName() {
        return methodName;
    }

    public void setNethodName(String nethodName) {
        this.methodName = nethodName;
    }

    public int getParameterNumber() {
        return parameterNumber;
    }

    public void setParameterNumber(int parameterNumber) {
        this.parameterNumber = parameterNumber;
    }


    /**
     * 参数值得HashMap
     */
    private HashMap<Integer,Object> parameters=new HashMap<>();


    public void addParameter(int index,Object value){
        parameters.put(index,value);
    }

    public void refreashParameters(){
        parameters.clear();
    }

    public Object getParameter(int index){return parameters.get(index);}

    public void setParameters(HashMap<Integer,Object> parameters){this.parameters=parameters;}


    /**
     * 参数类型的hashMap
     */
    HashMap<Integer,String> parametersType=new HashMap<>();

    public void addParameterType(int index,String type){parametersType.put(index,type);}

    public void refreashParameter(){parametersType.clear();}

    public String getParameterType(int index){return parametersType.get(index);}

    public void setParametersType(HashMap<Integer,String> parametersType){this.parametersType=parametersType;}

    @Override
    protected UiAttribute clone()  {
        return new UiAttribute(this.componentName,this.attributeDisplayName,this.methodName,this.parameterNumber,this.parameters);

    }

    @Override
    public String toString() {
        return "UiAttribute{" +
                "componentName='" + componentName + '\'' +
                ", attributeDisplayName='" + attributeDisplayName + '\'' +
                ", methodName='" + methodName + '\'' +
                ", parameterNumber=" + parameterNumber +
                ", parameters=" + parameters +
                ", parametersType=" + parametersType +
                '}';
    }
}
