package com.example.administrator.visualizationpart.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;

import com.example.administrator.visualizationpart.Global.GlobalApplication;
import com.example.administrator.visualizationpart.R;

import java.util.List;

import GlobalTools.DataBean.Attribute;
import GlobalTools.DataBean.UiComponent;

public class UiComponentIndexAdapter extends ArrayAdapter<String> {
    String[] datas;
    Context context=null;
    private int resourceId;

    public UiComponentIndexAdapter(@NonNull Context context, int resource, @NonNull String[] strings) {
        super(context, resource, strings);

        this.context=context;
        this.resourceId=resource;
        this.datas=strings;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
       String adapterData = getItem(position);

        View view = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);
        TextView componentName=view.findViewById(R.id.uiComponent_list_child_name);
        componentName.setText(adapterData);
        return view;
    }
}
