package com.example.administrator.visualizationpart.Activity;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatSeekBar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.administrator.visualizationpart.Global.GlobalApplication;
import com.example.administrator.visualizationpart.Logic.MainActivityLogic;
import com.example.administrator.visualizationpart.Adapter.MyAdapter;
import com.example.administrator.visualizationpart.R;
import com.example.administrator.visualizationpart.Tools.ActivityTools;
import com.google.gson.Gson;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.impl.AttachListPopupView;
import com.lxj.xpopup.interfaces.OnSelectListener;

import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;

import GlobalTools.DataBean.Action.Action;
import GlobalTools.DataBean.Action.ActionMean;
import GlobalTools.DataBean.Action.EventType;
import GlobalTools.DataBean.Attribute;
import GlobalTools.DataBean.Screen;
import GlobalTools.DataBean.UiComponent;
import UI.ComponentIndex.AbstractDataManager;
import UI.ComponentIndex.DisplayComponent;
import UI.ComponentIndex.componentListinterface;
import UI.Draw.Draw;
import UI.Draw.Size;
import UI.EvenHanding.ActionPopMenu;
import UI.EvenHanding.BuilderAction;
import UI.EvenHanding.EvenPopMenu;
import UI.ScreenStatus.ScreenManager;
import UI.ScreenStatus.ScreenNumberManager;
import UI.UICenterCtrol.UIGlobalManager;

public class ContentFragment extends Fragment implements  Draw<View>,componentListinterface<ExpandableListView> , DisplayComponent , ActionPopMenu<View>, EvenPopMenu<View> {
    public final static int FLAG_DRAW=101;
    public final static int SET_ATTRIBUTE=1102;
    public final static int SET_CHANGE=1103;
    public final static int SELECT_UICOMPONENT=1232;


    private  SoftReference<MainActivityLogic>  mainActivityLogic=new SoftReference<MainActivityLogic>(new MainActivityLogic());

    private String name;
    private  MyAdapter myAdapter;

    public Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            switch (msg.what){
                case FrameActivity.CREATE_NEW_FRAGMENT:
                    mainActivityLogic.get().AddAction(ContentFragment.this.getTag(),msg.getData().getString("Name"));
                    break;
                case FrameActivity.CANCEL_CREATE_FRAGMENT:
                    mainActivityLogic.get().ClearAttachAction();
                case FLAG_DRAW:
                    setClickToListPopMenu(componentList,msg.getData().getString("childName"),msg.getData().getString("groupName"));
                    break;

            }

           }
    };

    ConstraintLayout backgroundPanel;
    ExpandableListView componentList;
    ConstraintLayout componentListPanel;
    ImageButton componentflodButton;
    private View mainView;
    private TextView textView;
    private AppCompatSeekBar highSeekBar;
    private AppCompatSeekBar wightSeekBar;

    private UiComponent lastStateComponent;

    //动态组件添加
    LinkedList<UiComponent> dymicsViews=new LinkedList<>();

    //记录添加的属性改变事件的动作
    UiComponent lastSetChangeAttribute;
    Action lastAction;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        mainView=inflater.inflate(R.layout.activity_main,container,false);

        getComponentList(inflater);
        initView(mainView);/*初始化控件*/
        EventResiger();/*事件注册*/

        return mainView;

    }

    /**
     * 初始化方法
     * */
    private void initView(View view){

        backgroundPanel=view.findViewById(R.id.mainDraw);

        componentList=view.findViewById(R.id.component_list);
        componentList.setVisibility(View.INVISIBLE);
        componentList.setAdapter(myAdapter);
        myAdapter.notifyDataSetChanged();

        componentListPanel=view.findViewById(R.id.component_list_panel);
        componentListPanel.setBackgroundColor(Color.WHITE);
        componentListPanel.setVisibility(View.INVISIBLE);

        componentflodButton=view.findViewById(R.id.component_flod_button);

        textView=view.findViewById(R.id.FragmentTag);
        textView.setText(getTag());

        highSeekBar=view.findViewById(R.id.HightSeekBar);
        wightSeekBar=view.findViewById(R.id.weightSeekBar);
    }

    /**
     * 获取组件
     * @param layoutInflater
     */
    private void getComponentList(LayoutInflater layoutInflater){
        LinkedList<UiComponent> result= AbstractDataManager.getAbstractDataManager().getAllcomponent();


        /*按照标签进行分类*/
        HashSet<String> group=new HashSet<>();
        group.addAll(UIGlobalManager.getDataManager().getAllClassfy());

        /*获取各个分类，及其子项目*/
        HashMap<String, ArrayList<String>> hashMap=new HashMap<String,ArrayList<String>>();
        for(String s:group)hashMap.put(s,new ArrayList<String>());
        for(UiComponent uiComponent:result){
            ArrayList arrayList=hashMap.get(uiComponent.getClassfiy());
            arrayList.add(uiComponent.getNearName());
        }
        ArrayList<String> groupTemp=new ArrayList<>();
        groupTemp.addAll(group);


        ArrayList<ArrayList<String>> childtemp=new ArrayList<>();
        for(Map.Entry<String,ArrayList<String>> entry:hashMap.entrySet()){
            childtemp.add(entry.getValue());
        }

        myAdapter=new MyAdapter(handler,getActivity(),layoutInflater,groupTemp,childtemp);
    }


    /**
     * 设置名称并注册
     * @param name
     */
    public void setName(String name){
        this.name=name;
    }

    public String getName(){
        return name;
    }

    /**
     * 显示所有组件
     */
    @Override
    public void displayAllComponent() {
        if(componentList==null )return;

        componentList.setVisibility(View.VISIBLE);
        componentListPanel.setVisibility(View.VISIBLE);

    }

    /**
     * 事件注册方法
     */
    private void EventResiger(){
        componentflodButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.i("流程追踪","点击了打开按钮");
                displayAllComponent();
            }
        });
        componentList.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                Log.i("流程追踪","componentList焦点切换："+hasFocus);
                if(!hasFocus){
                    componentList.setVisibility(View.INVISIBLE);
                    componentListPanel.setVisibility(View.INVISIBLE);
                }
            }
        });


        /**
         * 点击背景板隐藏组件菜单
         */
        backgroundPanel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("流程追踪","背景板获取焦点");

                lastStateComponent=null;

                highSeekBar.setVisibility(View.GONE);
                wightSeekBar.setVisibility(View.GONE);

                backgroundPanel.setFocusable(true);
                componentList.setFocusable(false);
                componentList.setVisibility(View.INVISIBLE);
                componentListPanel.setVisibility(View.INVISIBLE);
            }
        });


        /**
         * 点击菜单显示点击的选项
         */
        componentList.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {


                Log.i("点击子菜单","参数："+"groupPosition:"+groupPosition+",childPosition:"+childPosition+",id:"+id);
                return false;
            }
        });


        highSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                Log.i("组件大小调整，按钮状态：",progress+"，"+String.valueOf(lastSetChangeAttribute==null));
                if(lastStateComponent==null)return;
                View view=(View)lastStateComponent.getComponentObj();
                ViewWrapper viewWrapper = new ViewWrapper(view);
                ObjectAnimator.ofInt(viewWrapper, "trueHeight", (int)(getScreenSize().getHeight()*((double)progress/100))).setDuration(0).start();
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        wightSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                if(lastStateComponent==null)return;

                View view=(View)lastStateComponent.getComponentObj();

                ViewWrapper viewWrapper = new ViewWrapper(view);
                ObjectAnimator.ofInt(viewWrapper, "trueWidth", (int)(getScreenSize().getWidth()*((double)progress/100))).setDuration(0).start();

                if(GlobalApplication.Debug){
                    Log.i("组件大小调整",progress+"");
                }
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
    }

    /**
     * 注册添加的组件的长按监听方法
     */

    private void registerViewToch(final View view){

        //注册事件的拖动
        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

               EventTag moveTag=( EventTag) view.getTag(R.id.event);
                double x=event.getRawX();
                double y=event.getRawY();
                Log.d("参数追踪", "onTouch: "+event.getAction());
                if (event.getAction()==MotionEvent.ACTION_DOWN){
                    moveTag.latsX=x;
                    moveTag.lastY=y;
                }else if (event.getAction()==MotionEvent.ACTION_MOVE){
                    double dx=x-moveTag.latsX;
                    double dy=y-moveTag.lastY;
                    Log.d("参数追踪", "onTouch: dx=="+dx+",dy=="+dy);
//            startAnimation(dx,dy);

                    //  moveMethod1(dx, dy);
                    moveMethod2(view,dx, dy);

                    moveTag.latsX=x;
                    moveTag.lastY=y;
                }
                return false;
            }
        });

        view.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                //设置大小滑块
                ContentFragment.this.lastStateComponent=(UiComponent) v.getTag(R.id.uiComponent);
                highSeekBar.setVisibility(View.VISIBLE);
                wightSeekBar.setVisibility(View.VISIBLE);


                EventTag eventTag=(EventTag) v.getTag(R.id.event);
                Log.d("双击追踪", ""+eventTag.lastClickTime);
                if(System.currentTimeMillis()-eventTag.lastClickTime>1000) eventTag.lastClickTime=System.currentTimeMillis();
                else {
                    ClickToInvokeEvenPopMenu(v);
                }
            }
        });

    }




    /**
     *------------------------------------------------------------------------------------------------------------------------------------
     * 绘制动态组件的方法
     */

    /**
     * 测量屏幕大小
     * @return
     */
    @Override
    public Size getScreenSize() {
        int screenWidth = getActivity().getWindowManager().getDefaultDisplay().getWidth(); // 屏幕宽（像素，如：480px）
        int screenHeight = getActivity().getWindowManager().getDefaultDisplay().getHeight(); // 屏幕高（像素，如：800p）
        Size.BuilderSize(screenWidth,screenHeight);
        return Size.getSize();
    }


    /**
     * 绘制动态组件
     * @param simpleComponent
     * @return
     */
    @Override
    public boolean drawInscreen(UiComponent simpleComponent) {
        try {

            if(mainActivityLogic.get()==null)mainActivityLogic=new SoftReference<MainActivityLogic>(new MainActivityLogic());
            simpleComponent=mainActivityLogic.get().InitAndSetAttribute(getActivity(),simpleComponent);

            //添加到动态组件中
            dymicsViews.add(simpleComponent);
            View newView= (View)simpleComponent.getComponentObj();

            /**
             * 设置关联
             */
            newView.setTag(R.id.uiComponent,simpleComponent);
            simpleComponent.setComponentObj(newView);
            ConstraintLayout.LayoutParams layoutParams=new ConstraintLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.topToTop=backgroundPanel.getId();
            layoutParams.leftToLeft=backgroundPanel.getId();
            layoutParams.rightToRight=backgroundPanel.getId();
            layoutParams.topMargin=500;

            newView.setClickable(false);

            registerViewToch(newView);
            backgroundPanel.addView(newView,layoutParams);

            newView.setTag(R.id.event,new EventTag());
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 组件移动的注册方法
     * @param view
     */
    @Override
    public void MoveComponent(View view) {


    }


    @Override
    public boolean refresh() {
        return false;
    }



    //根据属性动画的原理
    private void moveMethod2(View v,double dx, double dy) {

        v.setTranslationX((float) (v.getTranslationX()+dx));
        v.setTranslationY((float) (v.getTranslationY()+dy));
    }


    /**
     * 点击弹出属性菜单选项
     * @param object
     */
    @Override
    public void setClickToListPopMenu(ExpandableListView object,String... name) {
        Intent intent=new Intent(getActivity(),AttributeSettingPage.class);
        intent.setAction(AttributeSettingPage.ADD_NEW_UICOMPONENT);
        intent.putExtra("groupName",name[1]);
        intent.putExtra("childName",name[0]);
        startActivityForResult(intent,SET_ATTRIBUTE);
    }


    /**
     * 界面跳转结果接收处理,并绘制图形
     * * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode){
            case SET_ATTRIBUTE:
                if(resultCode==AttributeSettingPage.RESULT_SUCCESS){
                    drawInscreen( LoadData(data));
                }
                break;
            case SELECT_UICOMPONENT:
                if(resultCode==UiComponentDisPlayPage.RETURN_SUCESS){
                    //启动设置设置属性
                    Intent intent=new Intent(getActivity(),AttributeSettingPage.class);
                    intent.putExtra("DataId",dymicsViews.get(data.getIntExtra("DataIndex",0)).getId());
                    intent.putExtra("DataName",dymicsViews.get(data.getIntExtra("DataIndex",0)).getUUID());
                    intent.setAction(AttributeSettingPage.CHAGNE_UICOMPONENT);
                    startActivityForResult(intent,SET_CHANGE);
                }else {
                    lastSetChangeAttribute=null;
                    lastAction=null;
                }
                break;
            case SET_CHANGE:
                if(resultCode==AttributeSettingPage.RESULT_SUCCESS){

                    //封装成UiComponent
                    //属性设置完毕,添加属性改变动作
                    if(lastAction!=null)
                        UIGlobalManager.getEvenManager().getBuilderAction().builderAttributeChage(getTag(),lastSetChangeAttribute,LoadData(data),lastAction.getEvent());
                }
                break;

        }
    }

    /**
     * 加载数据,创建新的组件
     * @param data
     */
    private UiComponent LoadData(Intent data){

        if(GlobalApplication.Debug){
            Log.i("MainActivity_laodData","得到回传参数");
        }

        ArrayList<String> attributeString=data.getStringArrayListExtra("attribute");

        ArrayList<Attribute> attributes=new ArrayList<>();
        Gson g=new Gson();

        for(int i=0;i<attributeString.size();i++){
            attributes.add(g.fromJson(attributeString.get(i),Attribute.class));
        }

        UiComponent newView=AbstractDataManager.getAbstractDataManager().findComponentById(data.getIntExtra("componentid",0));
        newView.clearAttributes();

        for(Attribute attribute:attributes){
            newView.addDefineAttribute(attribute);
        }


        if(data.getStringExtra("UUID")!=null)newView.setUUID(data.getStringExtra("UUID"));
        else newView.initUUID();
        return newView;
    }

    /**
     * ------------------------------------------------------------------------------------------------------------------------------------------------------------
     * 添加动作事件
     */


    private AttachListPopupView attachListPopupView=null;
    @Override
    public void AttachActionPopMenu(final View view, final int eventPosition) {
        XPopup.Builder builder=new XPopup.Builder(getActivity()).atView(view);
        builder.asAttachList(ActionMean.getSeletionDisplay(), null,-100,0, new OnSelectListener() {
            @Override
            public void onSelect(int position, String text) {
                Log.i("悬浮菜单点击追踪","点击项"+position);
                if(mainActivityLogic.get()==null)mainActivityLogic=new SoftReference<MainActivityLogic>(new MainActivityLogic());

                if(position==0){
                    ActivityTools.getInstance().AddNewContent(handler,ContentFragment.this,(AppCompatActivity) getActivity());
                    mainActivityLogic.get().attachAddAction((UiComponent) (view.getTag(R.id.uiComponent)),eventPosition);
                }

                if(position==2){
                    String[] list=new String[dymicsViews.size()];

                    for(int i=0;i<list.length;i++){
                        list[i]=dymicsViews.get(i).getNearName()+":"+dymicsViews.get(i).getUUID();
                    }

                    Intent intent=new Intent(getActivity(),UiComponentDisPlayPage.class);
                    intent.putExtra("components",list);
                    startActivityForResult(intent,SELECT_UICOMPONENT);

                    lastSetChangeAttribute=(UiComponent) (view.getTag(R.id.uiComponent));
                    lastAction=new Action(getTag(),lastSetChangeAttribute.getUUID(),EventType.getActionTypeByIndex(eventPosition),ActionMean.getActionMeanByIndex(2));

                }
            }
        }).show();
    }


    @Override
    public void ClickToInvokeEvenPopMenu(final View target) {

            XPopup.Builder builder=new XPopup.Builder(getActivity()).atView(target);
             attachListPopupView=builder.asAttachList(EventType.getSelectionDisplay(), null,-100,0, new OnSelectListener() {
                @Override
                public void onSelect(int position, String text) {
                    Log.i("悬浮菜单点击追踪",text);
                    AttachActionPopMenu(target,position);
                }
            });
            attachListPopupView.show();
    }

    /**
     * ----------------------------------------------------------------------------------------------------------------------------------------------------------
     * 注册该屏幕的状态
     */


    /**
     * 组件包装类
     */
    private static class ViewWrapper {
        private View mTarget;

        public ViewWrapper(View view) {
            mTarget = view;
        }

        public void setTrueWidth(int width) {
            mTarget.getLayoutParams().width = width;
            mTarget.requestLayout();//必须调用，否则宽度改变但UI没有刷新
        }

        public int getTrueWidth() {
            return mTarget.getLayoutParams().width;
        }
        public void setTrueHeight(int height) {
            mTarget.getLayoutParams().height = height;
            mTarget.requestLayout();//必须调用，否则宽度改变但UI没有刷新
        }

        public int getTrueHeight() {
            return mTarget.getLayoutParams().height;
        }

    }
}

class EventTag{
    int componentId=0;
    public double latsX,lastY;
    public long lastClickTime=0;
    public int width,height;
    ArrayList<Action> actions=new ArrayList<>(4);
}




