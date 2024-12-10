package com.example.reciteword.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.reciteword.R;
import com.example.reciteword.pojo.Data;


//背诵小组件(主页面的那个)
public class ReciteFragment extends Fragment {


    private Button knowButton,unknowButton;
    private ImageButton tipsButton;
    private TextView wordText,definitionText;
    private boolean flag=true;
    private SharedPreferences sharedPre;
    private SharedPreferences.Editor editor;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_recite, container, false);

    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        knowButton = (Button)getActivity().findViewById(R.id.knowButton);
        unknowButton = (Button)getActivity().findViewById(R.id.unknowButton);
        tipsButton = (ImageButton)getActivity().findViewById(R.id.tipsButton);
        wordText = (TextView)getActivity().findViewById(R.id.wordText);
        definitionText = (TextView)getActivity().findViewById(R.id.definitionText);
        super.onActivityCreated(savedInstanceState);
        sharedPre = getActivity().getSharedPreferences("t",Context.MODE_PRIVATE);
        editor = sharedPre.edit();
        showNextWord();


        knowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showNextWord();
            }
        });

        unknowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int[] wrongNum = {sharedPre.getInt("wrongNum", 0)};      //获取错误单词的个数
                wrongNum[0]++;
                editor.putInt("wrongNum", wrongNum[0]);
                editor.putInt("wrong" + wrongNum[0], Data.getNowWordIndex());   //wrong1,表示第1一个错误单词,它映射的是单词列表的下标
                String wrongiNum = "wrong" + Data.getNowWordIndex() + "Num";
                editor.putInt(wrongiNum,sharedPre.getInt(wrongiNum,0)+1);   //wrong1,表示第1一个错误单词,它映射的是单词列表的下标
                String word = Data.getWord(Data.getNowWordIndex());

                showNextWord();
            }
        });
        tipsButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                flag = !flag;
                showTextByFlag(flag);
            }
        });
    }
    private void updateWordCount() {
        int cnt = sharedPre.getInt("word" + Data.getNowWordIndex(), 0);     //获取当前单词出现的次数,进行更新
        editor.putInt("word" + Data.getNowWordIndex(), ++cnt);
        editor.apply();
    }

    private void showNextWord() {
        Data.setNewWord();          //获取新的随机单词
        updateWordCount();
        wordText.setText(Data.getWord(Data.getNowWordIndex()));     //设置单词
        showTextByFlag(flag);                                       //设置单词释义
    }

    private void showTextByFlag(boolean flag) {
        if (flag){
            definitionText.setText(Data.getPron(Data.getNowWordIndex())+"\n"+Data.getwordDefine(Data.getNowWordIndex()));
        }else {
            definitionText.setText("");
        }
    }
}