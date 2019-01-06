package com.example.examplemod;

import org.openzen.zencode.java.*;

public class TestClass {
    
    
    TestClass(){}
    
    @ZenCodeGlobals.Global("testInstance")
    public static final TestClass TestInstance = new TestClass();
    
    @ZenCodeType.Method
    public void print(){
        System.out.println("Hello");
    }
    
}
