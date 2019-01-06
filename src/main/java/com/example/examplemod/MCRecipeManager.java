package com.example.examplemod;

import org.openzen.zencode.java.*;

import java.util.Arrays;

public class MCRecipeManager {
    
    @ZenCodeGlobals.Global("recipes")
    public static final MCRecipeManager Instance = new MCRecipeManager();
    
    private MCRecipeManager(){}
    
    @ZenCodeType.Method
    public void testUsingRecipeManager(String name, MCItemStack out, MCItemStack[] ins) {
        System.out.println("RecipeManager: Adding recipe named " + name + " for " + out + " using " + Arrays.toString(ins));
    }
    
    
    @ZenCodeType.Method
    public void testUsingRecipeManager2(String name, MCItemStack out, MCItemStack[][] ins) {
        System.out.println("RecipeManager: Adding recipe named " + name + " for " + out + " using " + Arrays.deepToString(ins));
    }
}
