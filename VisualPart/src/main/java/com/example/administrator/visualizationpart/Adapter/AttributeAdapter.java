package com.example.administrator.visualizationpart.Adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.administrator.visualizationpart.Global.GlobalApplication;
import com.example.administrator.visualizationpart.R;

import java.util.HashMap;
import java.util.List;

import FileManager.LogicFileManager;
import GlobalTools.DataBean.Attribute;

public class AttributeAdapter extends ArrayAdapter<Attribute> {

    private int resourceId;

    private String CoponentUUID;
    protected HashMap<Attribute,Integer> map=new HashMap<>();

    public HashMap<Attribute, Integer> getMap() {
        return map;
    }

    public AttributeAdapter(Context context, int textViewResourceId, List<Attribute> objects,String UUID) {
        super(context, textViewResourceId, objects);
        resourceId = textViewResourceId;
        this.CoponentUUID=UUID;
    }


    //获取坐标
    public int getPosition(Attribute attribute){
        return map.get(attribute);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Attribute adapterData = getItem(position);

        if(GlobalApplication.Debug){
            Log.i("Attribute日志","----------------------------");
            Log.i("Attribute日志",adapterData.getName());
            Log.i("Attribute日志",adapterData.getReflectMethod());
            Log.i("Attribute日志",adapterData.getNumber()+"");
            Log.i("Attribute日志",Logger.Tools.PrintArray(adapterData.getTypes()));
            Log.i("Attribute日志",Logger.Tools.PrintArray(adapterData.getValues()));
            Log.i("Attribute日志","----------------------------");
        }


        View view = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);
        TextView methodName=view.findViewById(R.id.method_text);
        EditText editText=view.findViewById(R.id.method_parameter);
        methodName.setText(adapterData.getName());

        Button button=view.findViewById(R.id.AddLogic);

        RegisterClick(button,editText);
        map.put(adapterData,position);

        return view;
    }


    private void RegisterClick(View view, final EditText text){
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                //向左边的代码块逻辑中插入代码块,但是不立即跳转代码块
                LogicFileManager.getInstance().getLogicStatesOpatrate().NotifiedLogicStatesChaged(CoponentUUID);
                text.setText(CoponentUUID);
            }
        });
    }

}

