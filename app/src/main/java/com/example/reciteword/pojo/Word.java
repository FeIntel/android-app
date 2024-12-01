package com.example.reciteword.pojo;


public class Word {
    private String word;    //单词
    private String pron;    //音标
    private String interpret;  //中文释义
    private int showNum;        //出现次数
    private int errorNum;           //错误次数

    public Word(String word, String pron, String definition, int showNum, int flag){
        this.word = word;
        this.pron = pron;
        this.interpret = definition;
        this.showNum = showNum;
        this.errorNum = flag;
    }

    public void setErrorNum(int errorNum) {
        this.errorNum = errorNum;
    }

    public void setShowNum(int showNum) {
        this.showNum = showNum;
    }

    public String getWord(){ return  word; }
    public String getPron(){ return  pron; }
    public String getInterpret(){ return interpret; }
    public int getShowNum(){ return  showNum; }
    public int getErrorNum(){ return errorNum; }

    @Override
    public String toString() {
        return "Word{" +
                "word='" + word + '\'' +
                ", pron='" + pron + '\'' +
                ", definition='" + interpret + '\'' +
                ", showNum=" + showNum +
                ", flag=" + errorNum +
                '}';
    }
}

