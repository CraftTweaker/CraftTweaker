package com.crafttweaker.crafttweaker.main;

import org.openzen.zencode.java.*;

public class TestClass {
    
    
    @ZenCodeGlobals.Global("testInstance")
    public static final TestClass TestInstance = new TestClass();
    
    TestClass() {
    }
    
    @ZenCodeType.Method
    public void print() {
        System.out.println("Hello");
    }
    
}
