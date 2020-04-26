package com.example.administrator.visualizationpart.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.administrator.visualizationpart.Adapter.UiComponentIndexAdapter;
import com.example.administrator.visualizationpart.R;

public class UiComponentDisPlayPage extends AppCompatActivity {
    public final static int RETURN_SUCESS=123;

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


        uiComponentIndexAdapter=new UiComponentIndexAdapter(this,R.layout.uicomponent_list_child_view,datas);
        componentList.setAdapter(uiComponentIndexAdapter);
        RegistList();

    }


    private void initView(){
        componentList=findViewById(R.id.componentChoiceList);
    }


    /**
     * 点击跳转,返回到之前的界面
     */
    private void RegistList(){
        componentList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent();
                intent.putExtra("DataIndex",position);

                setResult(RETURN_SUCESS,intent);
                finish();
            }
        });
    }
}
