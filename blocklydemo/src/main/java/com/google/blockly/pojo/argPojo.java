package com.google.blockly.pojo;

import java.util.List;

public class argPojo {

    private String type;
    private String name;
    private List<String> options;//格式: [key1,value1],[...]



    public void setOptions(List<String> options){
//
//        options.set(0,"[" + options.get(0));
//        for(int i=1;i<options.size()-1;++i)
//        {
//            if(i%2==0)
//            {
//                options.set(i,"],[" + options.get(i));
//            }
//
//        }
//        options.set(options.size()-1, options.get(options.size()-1) + "]");

        this.options=options;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public String getName() {
        return name;
    }
    public List<String> getOptions(){
        return options;
    }
}
