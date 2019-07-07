package com.blamejared.crafttweaker.impl.brackets;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.annotations.*;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.api.managers.IRecipeManager;
import com.blamejared.crafttweaker.api.managers.RecipeManagerWrapper;
import com.blamejared.crafttweaker.impl.tag.MCTag;
import com.blamejared.crafttweaker.impl.item.MCItemStack;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraftforge.registries.ForgeRegistries;
import org.openzen.zencode.java.ZenCodeType;

import java.util.Locale;
import java.util.Optional;

@ZenRegister
@ZenCodeType.Name("crafttweaker.api.BracketHandlers")
public class BracketHandlers {
    
    @BracketResolver("item")
    public static IItemStack getItem(String tokens) {
        if(!tokens.toLowerCase(Locale.ENGLISH).equals(tokens))
            CraftTweakerAPI.logWarning("Item BEP <item:%s> does not seem to be lower-cased!", tokens);
        
        final String[] split = tokens.split(":");
        if(split.length != 2)
            throw new IllegalArgumentException("Could not get item with name: <item:" + tokens + ">! Syntax is <item:modid:itemname>");
        ResourceLocation key = new ResourceLocation(split[0], split[1]);
        if(!ForgeRegistries.ITEMS.containsKey(key)) {
            throw new IllegalArgumentException("Could not get item with name: <item:" + tokens + ">! Item does not appear to exist!");
        }
        final ItemStack value = new ItemStack(ForgeRegistries.ITEMS.getValue(key));
        return new MCItemStack(value);
    }
    
    
    @BracketResolver("tag")
    public static MCTag getTag(String tokens) {
        if(!tokens.toLowerCase(Locale.ENGLISH).equals(tokens))
            CraftTweakerAPI.logWarning("Tag BEP <tag:%s> does not seem to be lower-cased!", tokens);
        final String[] split = tokens.split(":");
        if(split.length != 2)
            throw new IllegalArgumentException("Could not get Tag with name: <tag:" + tokens + ">! Syntax is <tag:modid:tagname>");
        return new MCTag(new ResourceLocation(tokens));
    }
    
    @BracketResolver("recipetype")
    public static IRecipeManager getRecipeManager(String tokens) {
        if(!tokens.toLowerCase(Locale.ENGLISH).equals(tokens))
            CraftTweakerAPI.logWarning("RecipeType BEP <recipetype:%s> does not seem to be lower-cased!", tokens);
        Optional<IRecipeType<?>> value = Registry.RECIPE_TYPE.getValue(new ResourceLocation(tokens));
        
        if(value.isPresent()) {
            return new RecipeManagerWrapper(value.get());
        } else {
            throw new IllegalArgumentException("Could not get RecipeType with name: <recipetype:" + tokens + ">! RecipeType does not appear to exist!");
        }
    }
}
