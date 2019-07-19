package com.gzhc365.minidemo.web.common.constants;


abstract class Base{
    public Base(int i ){
        System.out.println("..");
    }
    {

    }
    public abstract void f();
}
public class A {
    public static Base getBase(int i){
        return new Base(i) {
            {
                System.out.println("...");
            }
            @Override
            public void f() {

            }
        };
    }
}
