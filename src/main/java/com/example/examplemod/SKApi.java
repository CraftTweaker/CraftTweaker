package com.example.examplemod;

import net.minecraft.item.*;
import net.minecraft.item.crafting.*;
import net.minecraft.util.*;
import net.minecraftforge.registries.ForgeRegistries;
import org.openzen.zencode.java.ZenCodeGlobals;

public class SKApi implements ZenCodeGlobals {
    
    @Global
    public static void addShapelessRecipe(String name, String output, String inputs) {
        NonNullList<Ingredient> list = NonNullList.create();
        list.add(Ingredient.fromStacks(new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation(inputs)))));
        ExampleMod.recipeList.add(new ShapelessRecipe(new ResourceLocation("examplemod", name), "", new ItemStack(new IItemProvider() {
            @Override
            public Item asItem() {
                return ForgeRegistries.ITEMS.getValue(new ResourceLocation(output));
            }
        }), list));
    }
    
    
    
}
