package com.crafttweaker.crafttweaker.api.recipes.crafting;

import com.crafttweaker.crafttweaker.main.items.MCItemStack;
import org.openzen.zencode.java.*;

import java.util.Arrays;

public class MCRecipeManager {
    
    @ZenCodeGlobals.Global("recipes")
    public static final MCRecipeManager INSTANCE = new MCRecipeManager();
    
    private MCRecipeManager() {
    }
    
    @ZenCodeType.Method
    public void addShapeless(String name, MCItemStack out, MCItemStack[] ins) {
        System.out.println("RecipeManager: Adding recipe named " + name + " for " + out + " using " + Arrays.toString(ins));
    }
    
    
    @ZenCodeType.Method
    public void addShaped(String name, MCItemStack out, MCItemStack[][] ins) {
        System.out.println("RecipeManager: Adding recipe named " + name + " for " + out + " using " + Arrays.deepToString(ins));
    }
    
}

