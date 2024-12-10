package com.example.reciteword.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.reciteword.R;
import com.example.reciteword.pojo.Data;
import com.example.reciteword.pojo.Word;
import com.example.reciteword.adapter.WordAdapter;

import java.util.ArrayList;
import java.util.List;

//错题小组件
public class WrongFragment extends Fragment {

    private List<Word> wordList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_wrong, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // 获取 SharedPreferences 实例，名为 "t"，用于存储和检索持久化数据
        final SharedPreferences sharedPre = getActivity().getSharedPreferences("t", Context.MODE_PRIVATE); // 去轻量数据库中获取数据

        // 从 SharedPreferences 中获取键为 "wrongNum" 的整数值，如果不存在则返回默认值 0
        // 使用数组封装是为了将数据从值类型转换为引用类型，方便在内部类中修改该变量的值
        // 获取总共错误了多少个错误单词
        final int[] wrongNum = {sharedPre.getInt("wrongNum", 0)}; // 这里是获取sharedPre中key为wrongNum的value,如果不存在,就返回0这个安全值.之后封装到一个数据里面,目的是为了把数据从值类型.变成引用类型.方便修改这个变量的值

        // 打印当前错误次数的值，便于调试
        System.out.println(wrongNum[0]);


        // 清空当前的单词列表，准备重新加载错误的单词
        wordList.clear();

        // 遍历所有错误的单词编号，从1到wrongNum[0]
        for (int i = 1; i <= wrongNum[0]; i++) {
            // 从 SharedPreferences 中获取每个错误单词的编号，键名为 "wrong" + i，如果不存在则返回 0
            int wordIndex = sharedPre.getInt("wrong" + i, 0);        //获取第i个错误单词,并得到它在单词列表中的下标

            // 根据获取的单词编号，创建一个新的 Word 对象
            // 参数依次为单词内容、音标、定义、出现次数（默认为1）、标记（默认为0）
            Word word = new Word(
                    Data.getWord(wordIndex), // 获取单词内容
                    Data.getPron(wordIndex), // 获取单词音标
                    Data.getwordDefine(wordIndex), // 获取单词定义
                    sharedPre.getInt("wrong"+wordIndex+"Num",0),
                    // 获取单词的出现次数，默认为1
                    0 // 初始化标记为0
            );

            // 打印随机数和单词出现次数，便于调试
            System.out.println(Data.getNowWordIndex() + ":" + sharedPre.getInt("word" + wordIndex, 9));

            // 将创建的 Word 对象添加到单词列表中
            wordList.add(word);         //到最后, 这个wordList,是按照用户答单词错误的顺序,展示错误单词
        }

        // 创建一个自定义的 WordAdapter，将单词列表传递给适配器
        WordAdapter adapter = new WordAdapter(getActivity(), R.layout.word_item, wordList);

        // 获取布局文件中的 ListView 组件，ID为 wrong_list_view
        ListView listView = getActivity().findViewById(R.id.wrong_list_view);


        // 将适配器设置给 ListView，以显示错误的单词列表
        listView.setAdapter(adapter);
    }
}