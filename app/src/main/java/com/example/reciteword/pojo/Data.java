package com.example.reciteword.pojo;

import android.content.Context;
import android.content.res.AssetManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 初始化wordList(从txt文件中读取单词), 并管理wordList,比如获取总数组长度.根据下标, 获取对应的word的各个属性.
 */
public class Data {
    private static List<Word> wordList = new ArrayList<>();
    public static void initWordList(Context context) {
        AssetManager assetManager = context.getAssets();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(assetManager.open("words.txt")))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 5) {
                    String name = parts[0];
                    String pronunciation = parts[1];
                    String meaning = parts[2];
                    int field1 = Integer.parseInt(parts[3]);
                    int field2 = Integer.parseInt(parts[4]);
                    Word word = new Word(name, pronunciation, meaning, field1, field2);
                    wordList.add(word);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private static int numCount = 0, nowWordIndex = getRandomNum(91);
    public static void setNumCount(int numCount){
        Data.numCount = numCount;
    }

    public static void setNewWord(){
        Data.nowWordIndex = getRandomNum(91);
    }

    public static int getNumCount(){return numCount;}
    public static int getNowWordIndex(){return nowWordIndex;}
    public static String getWord(int cnt){
            return wordList.get(cnt).getWord();
    }
    public static String getPron(int cnt){
        return wordList.get(cnt).getPron();
    }
    public static String getwordDefine(int cnt){
        return wordList.get(cnt).getInterpret();
    }
    public static int getShowNum(int cnt){
        return wordList.get(cnt).getShowNum();
    }

    public static int getRandomNum(int endNum){
        if(endNum > 0){
            Random random = new Random();
            return random.nextInt(endNum);
        }
        return 0;
    }

}
