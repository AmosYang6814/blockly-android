package com.example.administrator.visualizationpart.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Adapter;
import android.widget.ListView;

import com.example.administrator.visualizationpart.Adapter.UiComponentIndexAdapter;
import com.example.administrator.visualizationpart.R;

public class UiComponentDisPlayPage extends AppCompatActivity {

    private ListView componentList;
    private UiComponentIndexAdapter uiComponentIndexAdapter;

    private String[] datas={};
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.uicomponent_index);
        Intent intent=getIntent();
        datas=intent.getStringArrayExtra("components");
        initView();
        uiComponentIndexAdapter=new UiComponentIndexAdapter(this,R.layout.component_list_child_view,datas);
        componentList.setAdapter(uiComponentIndexAdapter);
        RegistList();

    }


    private void initView(){
        componentList=findViewById(R.id.componentChoiceList);
    }


    /**
     * 点击跳转到属性设置界面
     */
    private void RegistList(){

    }
}
