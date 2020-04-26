package com.example.administrator.visualizationpart.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.example.administrator.visualizationpart.Adapter.AttributeAdapter;
import com.example.administrator.visualizationpart.Global.GlobalApplication;
import com.example.administrator.visualizationpart.R;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import GlobalTools.DataBean.Attribute;
import GlobalTools.DataBean.UiComponent;
import UI.ComponentIndex.AbstractDataManager;

//属性设置页
public class AttributeSettingPage extends Activity {
    public static final int RESULT_SUCCESS=10002;
    public static final int RESULT_FIRARE=10003;

    public static final String ADD_NEW_UICOMPONENT="com.example.VisualPart.addNewUiComponent";
    public static final String CHAGNE_UICOMPONENT="com.example.VisualPart.ChangeUiComponent";

    List<Attribute> list=new ArrayList<>();
    Button commit,cancel;
    ListView listView;
    AttributeAdapter attributeAdapter;
    UiComponent currentComponent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.attribute_setting_page);

        Intent intent=getIntent();

        if(intent !=null &&intent.getAction().equals(ADD_NEW_UICOMPONENT))
        list=loadMethodData(intent.getStringExtra("groupName"),intent.getStringExtra("childName"));

        else if(intent !=null && intent.getAction().equals(CHAGNE_UICOMPONENT)){
            list=loadMethodData(intent.getIntExtra("DataId",0),intent.getStringExtra("DataName"));
        }
        else finish();


       initView();
       setEventListener();
    }

    private void initView(){
        commit=findViewById(R.id.commitButton);
        cancel=findViewById(R.id.cancelButton);

        attributeAdapter=new AttributeAdapter(this,R.layout.attribute_setting_list_view,list,currentComponent.getUUID());
        listView=findViewById(R.id.AttributeList);
        listView.setAdapter(attributeAdapter);
    }

    private void setEventListener(){



        commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LinearLayout layout=null;


                for(Attribute attribute:list){
                    for(int i=0;i<attribute.getValues().length;i++){

                        layout = (LinearLayout)listView.getChildAt(attributeAdapter.getPosition(attribute));// 获得子item的layo
                        EditText et = (EditText) layout.findViewById(R.id.method_parameter);// 从layout中获得控件,根据其id

                        String data=et.getEditableText().toString();

                        if(GlobalApplication.Debug){
                            Log.i("AttributeSettingPage_79",data);
                        }
                        if(attribute.getValues()[i].equals(Attribute.DATA_UNDEFINE )&& et.getText().length()!=0){
                           attribute.getValues()[i]=Attribute.setValueData(attribute.getTypes()[i],data);
                        }
                    }
                }

                Intent intent=new Intent();

                ArrayList<String> resultData=new ArrayList<>();

                Gson gson=new Gson();
                for(int i=0;i<list.size();i++){
                    resultData.add(gson.toJson(list.get(i)));
                }

                if(GlobalApplication.Debug){
                    Log.i("AttributeSettingPage_77",resultData.toString());
                }

                intent.putStringArrayListExtra("attribute",resultData);
                intent.putExtra("componentid",currentComponent.getId());
                intent.putExtra("UUID",currentComponent.getUUID());
                setResult(RESULT_SUCCESS,intent);

                finish();


            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_FIRARE);
                finish();
            }
        });
    }


    /**
     * 加载数据
     * @param groupName
     * @param childName
     * @return
     */
    private List<Attribute> loadMethodData(String groupName,String childName){
        currentComponent=AbstractDataManager.getAbstractDataManager().findConponentByName(groupName, childName);
        currentComponent.initUUID();
        return currentComponent.getAllDefineAttribute();
    }


    private List<Attribute> loadMethodData(int uiComponentId,String componentName){
        currentComponent=AbstractDataManager.getAbstractDataManager().findComponentById(uiComponentId);
        currentComponent.setUUID(componentName);
        return currentComponent.getAllDefineAttribute();
    }



}
