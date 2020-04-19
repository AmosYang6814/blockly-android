package com.example.administrator.visualizationpart.Tools;

import java.util.Iterator;

public class StringTools {

    public static  String deleteSpaceAfterString(String s){
        StringBuffer stringBuffer=new StringBuffer(s);

        while(stringBuffer.charAt(stringBuffer.length()-1)==' ')stringBuffer.deleteCharAt(stringBuffer.length()-1);

        return stringBuffer.toString();
    }
}
