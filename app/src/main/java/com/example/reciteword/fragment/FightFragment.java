package com.example.reciteword.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;

import com.example.reciteword.R;
import com.example.reciteword.activity.BattleActivity;

import java.util.Timer;
import java.util.TimerTask;


//人机对决小组件(先创建过度动画, 之后跳转到对局页面)
public class FightFragment extends Fragment {

    private LinearLayout fighLayout;
    //onCreateView()方法先于onActivityCreated()方法调用。
    //创建Fragment的视图时调用。需要返回一个View对象(一个标签)作为Fragment的根视图。
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_fight, container, false);
    }
    //当Activity的onCreate()方法执行完后调用。此时Activity已经创建完成,可以在这里进行与Activity UI相关的初始化操作,如查找视图、设置适配器等。
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        fighLayout = (LinearLayout)getActivity().findViewById(R.id.fighLayout);
        fighLayout.setAlpha((float) 0.5);       //设置透明度
        TimerTask task = new TimerTask(){
            public void run(){
                // 判断当前Fragment是否为FightFragment
                if (getActivity()!=null ) {
                    Log.d("getActivity",getActivity().toString());
                    Intent intent = new Intent(getActivity(), BattleActivity.class);
                    startActivity(intent);
                }
            }
        };
        Timer timer = new Timer();
        timer.schedule(task,2000);
    }
}