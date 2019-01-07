package com.crafttweaker.crafttweaker.main.zencode;

import com.crafttweaker.crafttweaker.api.recipes.crafting.MCRecipeManager;
import com.crafttweaker.crafttweaker.main.TestClass;

import java.util.*;

public class ZenCodeInterface {
    
    public static Collection<Class<?>> collectClassesToRegister() {
        return Arrays.asList(MCRecipeManager.class, TestClass.class);
    }
}
