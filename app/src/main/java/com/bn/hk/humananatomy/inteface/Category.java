package com.bn.hk.humananatomy.inteface;

import java.util.ArrayList;

public class Category {
    public  String categoryName=""; //运动系统，内脏学，blabla...
    public  String categoryPic="";
    public  char categoryLock;//0是该目录没解锁，1是解锁
    public  ArrayList<String> categoryChildName=new ArrayList<>();//关节学，骨学，肌学，blabla...
    public  ArrayList<String> categoryChildPic=new ArrayList<>();
    public  ArrayList<Character> categoryChildLock=new ArrayList<>();

}
