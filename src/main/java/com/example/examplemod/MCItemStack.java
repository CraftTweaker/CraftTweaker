package com.example.examplemod;

import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistries;
import org.openzen.zencode.java.ZenCodeType;

public class MCItemStack implements ZenCodeType {
    
    private ItemStack stack;
    
    public MCItemStack(ItemStack stack) {
        this.stack = stack;
    }
    
    @Constructor
    public MCItemStack(String name) {
        this.stack = new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation(name)));
    }
    
    @ZenCodeType.Method
    public static MCItemStack create(String input) {
        return new MCItemStack(input);
    }
    
    @Method
    public void print() {
        System.out.println(stack);
    }
    
    public ItemStack getInternal() {
        return this.stack;
    }
}
