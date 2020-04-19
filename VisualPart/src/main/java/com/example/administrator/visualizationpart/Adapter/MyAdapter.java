package com.example.administrator.visualizationpart.Adapter;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.visualizationpart.Activity.ContentFragment;
import com.example.administrator.visualizationpart.R;

import java.util.ArrayList;

public class MyAdapter extends BaseExpandableListAdapter {

    private Handler MainActivity_handler=null;
    private Context mContext;
    private ArrayList<String> mGroup;
    private ArrayList<ArrayList<String>> mItemList;
    private final LayoutInflater mInflater;

    public MyAdapter(Handler handler,Context context, String[] group, ArrayList<ArrayList<String>> itemList){
        ArrayList<String> list=new ArrayList<>();
        for(int i=0;i<group.length;i++)list.add(group[i]);

        this.mContext = context;
        this.mGroup = list;
        this.mItemList = itemList;
        mInflater = LayoutInflater.from(context);
        MainActivity_handler=handler;
    }

    public MyAdapter(Handler handler,Context context, ArrayList<String> group, ArrayList<ArrayList<String>> itemList){
        this.mContext = context;
        this.mGroup = group;
        this.mItemList = itemList;
        mInflater = LayoutInflater.from(context);
        MainActivity_handler=handler;
    }



    public MyAdapter(Handler handler,Context context,LayoutInflater layoutInflater, ArrayList<String> group, ArrayList<ArrayList<String>> itemList){
        this.mContext = context;
        this.mGroup = group;
        this.mItemList = itemList;
        mInflater = layoutInflater;
        MainActivity_handler=handler;
    }
    //父项的个数
    @Override
    public int getGroupCount() {
        return mGroup.size();
    }
    //某个父项的子项的个数
    @Override
    public int getChildrenCount(int groupPosition) {

        if(mItemList.size()<=groupPosition)return 0;
        return mItemList.get(groupPosition).size();
    }
    //获得某个父项
    @Override
    public Object getGroup(int groupPosition) {
        return mGroup.get(groupPosition);
    }
    //获得某个子项
    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return mItemList.get(groupPosition).get(childPosition);
    }
    //父项的Id
    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }
    //子项的id
    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }
    @Override
    public boolean hasStableIds() {
        return false;
    }
    //获取父项的view
    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        if (convertView == null){
            convertView = mInflater.inflate(R.layout.component_list_group_view,parent,false);
        }
        String group = mGroup.get(groupPosition);
        TextView tvGroup = (TextView) convertView.findViewById(R.id.tv_group);
        tvGroup.setText(group);
        return convertView;
    }
    //获取子项的view
    @Override
    public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        final String child = mItemList.get(groupPosition).get(childPosition);
        if (convertView == null){
            convertView = mInflater.inflate(R.layout.component_list_child_view,parent,false);
        }
        TextView tvChild = (TextView)convertView.findViewById(R.id.tv_name);
        tvChild.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext,child,Toast.LENGTH_SHORT).show();

                Message message=new Message();
                message.what= ContentFragment.FLAG_DRAW;
                Bundle bundle=new Bundle();
                bundle.putString("childName",child);
                bundle.putString("groupName",mGroup.get(groupPosition));
                message.setData(bundle);
                MainActivity_handler.sendMessage(message);
            }
        });
        tvChild.setText(child);
        return convertView;
    }
    //子项是否可选中,如果要设置子项的点击事件,需要返回true
    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    public ArrayList<ArrayList<String>> getmItemList(){
        return mItemList;
    }
}

