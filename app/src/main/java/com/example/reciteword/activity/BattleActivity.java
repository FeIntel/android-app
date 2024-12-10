package com.example.reciteword.activity;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.reciteword.R;
import com.example.reciteword.pojo.Data;

import java.util.Random;

// BattleActivity 用于实现单词对战功能
public class BattleActivity extends AppCompatActivity {
    // 定义页面上的一些控件
    private Button choose1Button, choose2Button, choose3Button;  // 三个选择按钮
    private TextView user1Score, user2Score, wordFightText;  // 显示分数和单词的文本框

    // 定义一些游戏数据
    private int round = 1, rightNum = 0, myScore = 0, youScore = 0, fullScore = 50, thisWordNum;
    private String word, definition1, definition2, definition3;

    // SharedPreferences 用于存储一些持久化数据
    private SharedPreferences sharedPre;
    private SharedPreferences.Editor editor;        //获取数据可中的一项,用来操作这个项的一个editor.
    private int[] wrongNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load); // 设置页面布局为 activity_load.xml

        // 隐藏下方的导航栏
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        // 获取 SharedPreferences 实例,并取出 wrongNum 的值
        //SharedPreferences是android开发中一个轻量的可存储数据的东西,用于保存一些简单的键值对数据。它的数据会持久化保存,即使应用关闭或设备重启,数据也不会丢失。
        //这里使用 getSharedPreferences("t",Context.MODE_PRIVATE) 方法获取名为 "t" 的 SharedPreferences 实例:
        //第一个参数 "t" 是这个 SharedPreferences 的名字,你可以随意取名。如果不存在,系统会自动创建一个。
        //第二个参数 Context.MODE_PRIVATE 表示这个 SharedPreferences 的数据只能被本应用访问,其他应用无法访问。
        sharedPre = this.getSharedPreferences("t", Context.MODE_PRIVATE);        //这里是获取
        wrongNum = new int[]{sharedPre.getInt("wrongNum", 0)};
        System.out.println(wrongNum[0]);

        // 获取页面控件的引用
        choose1Button = (Button) findViewById(R.id.choose1Button);
        choose2Button = (Button) findViewById(R.id.choose2Button);
        choose3Button = (Button) findViewById(R.id.choose3Button);
        user1Score = (TextView) findViewById(R.id.user1Score);
        user2Score = (TextView) findViewById(R.id.user2Score);
        wordFightText = (TextView) findViewById(R.id.wordFightText);

        // 初始化单词数据
        initWord();

        // 为三个选择按钮设置点击事件
        choose1Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pressButton(0);
            }
        });

        // choose2Button 和 choose3Button 的逻辑与 choose1Button 类似,只是判断正确答案的条件不同
        choose2Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pressButton(1);
            }
        });
        choose3Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pressButton(2);
            }
        });
    }

    private void pressButton(int buttonId) {
        //总结一下这段段代码逻辑:如果你对了,对方80%概率加10分,如果你错误了,对方40%加十分
        if (rightNum == buttonId) {  // 如果选对了
            myScore += 10;  // 我方加10分
            int x = getRandomNum(10);
            if (x >= 2) youScore += 10;  // 有一定概率对方也加10分
        } else {  // 如果选错了
            editor = sharedPre.edit();
            int x = getRandomNum(10);
            if (x >= 6) youScore += 10;  // 有一定概率对方加10分
            wrongNum[0]++;  // 错误次数加1
            editor.putInt("wrongNum", wrongNum[0]);  // 保存错误次数
            editor.putInt("wrong" + wrongNum[0], thisWordNum);  // 保存错误的单词编号
            String wrongiNum = "wrong" + thisWordNum + "Num";
            editor.putInt(wrongiNum, sharedPre.getInt(wrongiNum, 0) + 1);   //wrong1,表示第1一个错误单词,它映射的是单词列表的下标
            editor.commit();
        }
        // 如果任意一方达到满分,则跳转到成功或失败页面
        if (myScore >= fullScore || youScore >= fullScore) {
            if (myScore >= youScore) {
                Intent intent = new Intent(BattleActivity.this, SuccessActivity.class);
                ;
                startActivity(intent);
            } else {
                Intent intent = new Intent(BattleActivity.this, FailActivity.class);
                startActivity(intent);
            }
        }
        initWord();  // 初始化下一个单词
    }

    // 初始化单词数据
    private void initWord() {
        int rNum = getRandomNum(90);  // 随机取一个0-89之间的数作为单词的编号
        thisWordNum = rNum;
        word = Data.getWord(rNum);  // 根据编号获取单词
        rightNum = getRandomNum(3);  // 随机取0-2之间的数,决定正确答案在哪个位置

        final SharedPreferences sharedPre = this.getSharedPreferences("t", Context.MODE_PRIVATE);
        editor = sharedPre.edit();
        int cnt = sharedPre.getInt("word" + rNum, 0);  // 获取这个单词的出现次数
        cnt++;
        editor.putInt("word" + rNum, cnt);  // 次数加1并保存
        editor.apply();

        // 根据 rightNum 的值,随机生成三个定义,其中一个是正确的
        switch (rightNum) {
            case 0:
                definition1 = Data.getwordDefine(rNum);
                definition2 = Data.getwordDefine(getRandomNum(90));
                definition3 = Data.getwordDefine(getRandomNum(90));
                break;
            case 1:
                definition1 = Data.getwordDefine(getRandomNum(90));
                definition2 = Data.getwordDefine(rNum);
                definition3 = Data.getwordDefine(getRandomNum(90));
                break;
            default:
                definition1 = Data.getwordDefine(getRandomNum(90));
                definition2 = Data.getwordDefine(getRandomNum(90));
                definition3 = Data.getwordDefine(rNum);
                break;
        }

        // 将单词和定义显示在页面上
        choose1Button.setText(definition1);
        choose2Button.setText(definition2);
        choose3Button.setText(definition3);
        wordFightText.setText(word);
        user1Score.setText("得分:" + myScore);
        user2Score.setText("得分:" + youScore);
    }

    // 工具方法,随机生成一个0到endNum-1之间的整数
    private static int getRandomNum(int endNum) {
        if (endNum > 0) {
            Random random = new Random();
            return random.nextInt(endNum);
        }
        return 0;
    }
}