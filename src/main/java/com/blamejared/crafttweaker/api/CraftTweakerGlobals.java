package com.blamejared.crafttweaker.api;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistries;
import org.openzen.zencode.java.ZenCodeGlobals;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
public class CraftTweakerGlobals {
    
    @ZenCodeGlobals.Global
    public static void println(String msg) {
        CraftTweakerAPI.logger.info(msg);
    }
    
    @ZenCodeGlobals.Global
    public static void print(String msg) {
        println(msg);
    }
    
    @ZenCodeGlobals.Global
    public static ItemStack getItemStack(String name) {
        return new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation(name)));
    }
}