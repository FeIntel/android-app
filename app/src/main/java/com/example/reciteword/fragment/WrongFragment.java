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
import com.example.reciteword.pojo.WordAdapter;

import java.util.ArrayList;
import java.util.List;

//错题小组件
public class WrongFragment extends Fragment {

    private List<Word> wordList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_wrong, container, false);
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        final SharedPreferences sharedPre = getActivity().getSharedPreferences("t",Context.MODE_PRIVATE);

        final int[] wrongNum = {sharedPre.getInt("wrongNum", 0)};
        System.out.println(wrongNum[0]);
        wordList.clear();
        for(int i = 1;i<=wrongNum[0];i++){
            int temp = sharedPre.getInt("wrong"+i, 0);
            Word word = new Word(Data.getWord(temp),Data.getPron(temp),Data.getwordDefine(temp),sharedPre.getInt("word"+temp,1),0);
            System.out.println(Data.getRandNum()+":"+sharedPre.getInt("word"+temp,9));
            wordList.add(word);
        }

        WordAdapter adapter = new WordAdapter(getActivity(),R.layout.word_item,wordList);
        ListView listView = (ListView) getActivity().findViewById(R.id.wrong_list_view);
        listView.setAdapter(adapter);
    }
}