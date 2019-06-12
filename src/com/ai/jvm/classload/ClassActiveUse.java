package com.ai.jvm.classload;

import java.util.Random;

/**
 * new，直接使用
 * 访问某个类的静态变量，或对静态变量进行赋值操作
 * 调用静态方法
 * 反射某个类
 * 初始化一个子类
 * 启动类
 */
public class ClassActiveUse {
    public static void main(String[] args) throws ClassNotFoundException {
        //new Obj();
        //System.out.println(Obj.i);Obj.i =2;
        //Obj.sayHello();
        //Class.forName("com.ai.jvm.classload.Obj");
        //new ChildObj();
        //System.out.println(ChildObj.a);
        //Obj[] objs = new Obj[10];
        //System.out.println(Obj.OO);
        //System.out.println(Obj.x);
    }
}

class ChildObj extends Obj{
    public static int a = 1;
}

class Obj implements I{

    static{
        System.out.println("obj 被初始化");
    }
    public final static long OO = 999L;
    public final static int x = new Random().nextInt(1000);
    public static int i =0;
    public static void sayHello(){
        System.out.println("say hello...");
    }
}

interface I{
    int A = 0;
}
