package com.example.reciteword.activity;

import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.reciteword.R;
import com.example.reciteword.pojo.Data;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class InterfaceActivity extends AppCompatActivity  {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interface);  //
        
        Data.initWordList(this);  // 初始化单词列表,从文件加载单词数据
        
        // 底部导航栏上的各个按钮.
        BottomNavigationView navView = findViewById(R.id.nav_view);
        navView.setItemIconTintList(null);  // 设置 BottomNavigationView 的图标颜色,null 表示使用原始图标颜色

        //隐藏上面的"我背单词"
        ActionBar actionBar = getSupportActionBar();  // 获取 ActionBar
        if(actionBar != null){
            actionBar.hide();  // 隐藏 ActionBar()
        }
        
        // 创建 AppBarConfiguration 对象,用于配置 导航栏(navController) 的行为
        // 传入的参数是各个目的地的 ID,表示这样可以精准导航到对应的页面
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_recite, R.id.navigation_brush, R.id.navigation_fight,
                R.id.navigation_wrong).build();
        
        // NavController, 底部导航栏
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        
        // 将 appBarConfiguration(真实跳转到那里) 与 NavController(底部导航栏) 关联起来
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        
        // 将 navView(各个按钮) 与 NavController(底部导航栏) 关联起来
        // 这样当用户点击 navView 上的项时,NavController 会自动导航到相应的目的地
        NavigationUI.setupWithNavController(navView, navController);
    }
}