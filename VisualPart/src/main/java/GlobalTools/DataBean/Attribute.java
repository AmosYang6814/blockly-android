package GlobalTools.DataBean;

import android.graphics.Color;

import java.io.Serializable;

import UI.Tools.AttributeFormatException;

/**
 * Date:2020/3/23
 * Time:12:46
 * author:wenjun
 * 组件属性信息实体类
 *
 <attribute>标签表示该组件的属性设置方法
 inputTypeForm：表示传入参数的类型
 reflectMethod：表示反射调用的方法
 PassingForm="(#,null)" ：表示传入参数的格式
 # 表示：参数的位置，参数的类型在上面给定了
 */
public class Attribute implements Serializable {

    public static final int DATA_UNDEFINE=Integer.MAX_VALUE;
    public static final int DATA_NULL=Integer.MAX_VALUE-1;

    private Object Tag=null;
    public Object getTag() {
        return Tag;
    }

    public void setTag(Object tag) {
        Tag = tag;
    }


    private String name;
    //占位符的个数
    private int number;
    //PassingForm的数值
    private Object[]values;
    //inputTypeForm的值
    private String[]types;

    public String[] getMethodParameterClass() { return methodParameterClass; }

    public void setMethodParameterClass(String[] methodParameterClass) { this.methodParameterClass = methodParameterClass; }

    private String[] methodParameterClass;

    private String reflectMethod;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public Object[] getValues() {
        return values;
    }

    public void setValues(Object[] values) {
        this.values = values;
    }

    public String[] getTypes() {
        return types;
    }

    public void setTypes(String[] types) {
        this.types = types;
    }

    public String getReflectMethod() {
        return reflectMethod;
    }

    public void setReflectMethod(String reflectMethod) {
        this.reflectMethod = reflectMethod;
    }


    public static Attribute ParaseDataFromString(Attribute attribute,String passFrom,String inputForm,String classFrom){
       passFrom=passFrom.substring(1,passFrom.length()-1);
       String[] para=passFrom.split(",");


       attribute.setNumber(para.length);

        inputForm=inputForm.substring(1,inputForm.length()-1);

        String[] inputPara=inputForm.split(",");

        Object[] value=new Object[para.length];
        String[] datatype=new String[inputPara.length];

        for(int i=0;i<inputPara.length;i++){
            datatype[i]=inputPara[i];

            if(para[i].equals("#")){
                value[i]=DATA_UNDEFINE;
                continue;
            }

            switch (inputPara[i]){
                case "String":
                case "string":
                    value[i]=para[i];
                    break;
                case "int":
                case "Int":
                    value[i]=Integer.valueOf(para[i]);
                    break;
                case "boolean":
                case "Boolean":
                    value[i]=Boolean.valueOf(para[i]);
                    break;
                case "double":
                case "Double":
                    value[i]=Double.valueOf(para[i]);
                    break;
                case "color":
                case "Color":
                    value[i]=paraseColor(para[i]);
                    break;
                case "null":
                case"Null":
                    value[i]=DATA_NULL;
                    break;
            }

        }

        attribute.setTypes(datatype);
        attribute.setValues(value);

        classFrom=classFrom.substring(1,classFrom.length()-1);
        String[] parameterClass=classFrom.split(",");
        attribute.setMethodParameterClass(parameterClass);

        return attribute;
    }


    private static int paraseColor(String color){
        return Color.parseColor(color);
    }

    public static Object setValueData(String dataType,String value) throws AttributeFormatException{
        try {
            switch (dataType){
                case "String":
                case "string":
                   return value;
                case "int":
                case "Int":
                   return Integer.valueOf(value);
                case "boolean":
                case "Boolean":
                   return Boolean.valueOf(value);
                case "double":
                case "Double":
                   return Double.valueOf(value);
                case "color":
                case "Color":
                   return Color.parseColor(value);
            }
        } catch (NumberFormatException e) {
            throw new AttributeFormatException();
        }

        return null;
    }

    @Override
    public int hashCode() {
        return reflectMethod.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
       if(obj.getClass()!=this.getClass())return false;
       else {
           Attribute attribute = (Attribute) obj;

           return attribute.reflectMethod == this.reflectMethod;
       }
    }
}
