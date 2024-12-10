package com.example.reciteword.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.reciteword.R;
import com.example.reciteword.pojo.Word;

import java.util.List;

//之所以叫适配器,就是要我们自己在这里将视图上的控件和我们要设置的实体类的各个属性对应起来.
//listView
public class WordAdapter extends ArrayAdapter<Word> {       //这里传入Word作为泛型,表示这个Adapter是以Word为元素的列表.
    public int resourceId;

    public WordAdapter(Context context, int textViewResourceId, List<Word> objects) {        //
        super(context, textViewResourceId, objects);      //这里就是初始化列表的,可以不管
        resourceId = textViewResourceId;                //拿到textViewResourceId,传给resourceId,一般表示是哪个页面,引用了当前这个列表.
    }

    @Override       //这个方法会被反复调用多次,这个列表有多少个元素,就调用多少次
    public View getView(int position, View convertView, ViewGroup parent) {      //给父组件(像ArrayAdapter),赋值每一项具体要显示什么东西
        Word word = getItem(position); //获取当前项的实例,就是获取列表中的元素.List<Word>中具体的元素.
        View view = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);//加载一个布局文件,创建视图。
        TextView wordContext = view.findViewById(R.id.word_context);//找到视图中的各个控件。
        TextView wordDefinition = view.findViewById(R.id.word_definition);
        TextView wordPron = view.findViewById(R.id.word_pron);
        TextView wordFlag = view.findViewById(R.id.word_flag);
        wordContext.setText(word.getWord());        //给视图中的各个控件设置内容
        wordDefinition.setText(word.getInterpret());
        wordPron.setText(word.getPron() + "");
        wordFlag.setText(word.getShowNum() + "");           //出现次数(在错题中,就是错误次数,在刷题中,就是刷题次数)
        return view;        //返回设置完毕的视图(这里是一个item的视图)
    }
}
