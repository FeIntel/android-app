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

        wordText.setText(Data.getWord(Data.getRandNum()));
        showTextByFlag(flag);
        final int[] wrongNum = {sharedPre.getInt("wrongNum", 0)};
        int cnt = sharedPre.getInt("word"+Data.getRandNum(),0);
        cnt++;
        editor.putInt("word"+Data.getRandNum(),cnt);
        editor.apply();
        knowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showNextWord();
            }
        });

        unknowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wrongNum[0]++;
                editor.putInt("wrongNum", wrongNum[0]);
                editor.putInt("wrong" + wrongNum[0], Data.getRandNum());
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
        int cnt = sharedPre.getInt("word" + Data.getRandNum(), 0);
        cnt++;
        editor.putInt("word" + Data.getRandNum(), cnt);
        editor.apply();
    }

    private void showNextWord() {
        Data.setRandNum();
        updateWordCount();
        showTextByFlag(flag);
        wordText.setText(Data.getWord(Data.getRandNum()));
    }

    private void showTextByFlag(boolean flag) {
        if (flag){
            definitionText.setText(Data.getPron(Data.getRandNum())+"\n"+Data.getwordDefine(Data.getRandNum()));
        }else {
            definitionText.setText("");
        }
    }
}