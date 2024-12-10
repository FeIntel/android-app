package com.example.reciteword.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import com.example.reciteword.R;
import com.example.reciteword.pojo.Data;
import com.example.reciteword.pojo.Word;
import com.example.reciteword.adapter.WordAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

//刷单词记录小组件
public class BrushFragment extends Fragment {
    private List<Word> wordList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_brush, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initWordList();
        WordAdapter adapter = new WordAdapter(getActivity(), R.layout.word_item, wordList);
        ListView listView = getActivity().findViewById(R.id.word_list_view);
        listView.setAdapter(adapter);       //给列表视图,添加一个适配器
    }

    private void initWordList() {
        final SharedPreferences sharedPre = getActivity().getSharedPreferences("t", Context.MODE_PRIVATE); // 去轻量数据库中获取数据
        int[] ints = new int[90];
        wordList.clear();

        for (int i = 0; i < 90; i++) {
            ints[i] = i;
        }
        for (int i = 0; i < 90; i++) {
            int temp, x;
            x = Data.getRandomNum(90);
            temp = ints[x];
            ints[x] = ints[i];
            ints[i] = temp;
        }
        for (int i = 0; i < 90; i++) {
            Word word = new Word(Data.getWord(ints[i]),
                    Data.getPron(ints[i]),
                    Data.getwordDefine(ints[i]),
                    sharedPre.getInt("word" + i, 1), // 获取单词的出现次数，默认为1
                     0);
            wordList.add(word);
        }
    }



}