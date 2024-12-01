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
        setContentView(R.layout.activity_interface);
        Data.initWordList(this);
        //下面在初始化页面底部的四个按钮. 使得它们点击有效果
        BottomNavigationView navView = findViewById(R.id.nav_view);
        navView.setItemIconTintList(null);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.hide();
        }
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_recite, R.id.navigation_brush, R.id.navigation_fight,
                R.id.navigation_wrong).build();
        NavController navController = Navigation.findNavController(InterfaceActivity.this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);
        System.out.println("there is Interface");
    }
}