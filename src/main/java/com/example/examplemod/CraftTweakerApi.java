package com.example.examplemod;

import net.minecraft.item.*;
import net.minecraft.item.crafting.*;
import net.minecraft.util.*;
import net.minecraftforge.registries.ForgeRegistries;
import org.openzen.zencode.java.ZenCodeGlobals;

import java.util.Arrays;

public class CraftTweakerApi implements ZenCodeGlobals {
    
    @Global
    public static void addShapelessRecipe(String name, String output, String inputs) {
        NonNullList<Ingredient> list = NonNullList.create();
        list.add(Ingredient.fromStacks(new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation(inputs)))));
        ExampleMod.recipeList.add(new ShapelessRecipe(new ResourceLocation("examplemod", name), "", new ItemStack(() -> ForgeRegistries.ITEMS.getValue(new ResourceLocation(output))), list));
    }
    
    
    @Global
    public static void test(String name, MCItemStack out, MCItemStack[] ins) {
        System.out.println("Adding recipe named " + name + " for " + out + " using " + Arrays.toString(ins));
    }
    
    
    @Global
    public static void test2(String name, MCItemStack out, MCItemStack[][] ins) {
        System.out.println("Adding recipe named " + name + " for " + out + " using " + Arrays.deepToString(ins));
    }
    
    
    
}
