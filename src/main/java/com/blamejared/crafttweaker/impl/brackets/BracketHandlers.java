package com.blamejared.crafttweaker.impl.brackets;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.annotations.BracketDumper;
import com.blamejared.crafttweaker.api.annotations.BracketResolver;
import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.api.managers.IRecipeManager;
import com.blamejared.crafttweaker.api.managers.RecipeManagerWrapper;
import com.blamejared.crafttweaker.impl.blocks.MCBlockState;
import com.blamejared.crafttweaker.impl.entity.MCEntityType;
import com.blamejared.crafttweaker.impl.item.MCItemStack;
import com.blamejared.crafttweaker.impl.tag.MCTag;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.ForgeRegistryEntry;
import org.openzen.zencode.java.ZenCodeType;

import java.util.*;
import java.util.stream.Collectors;

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
    
    @BracketDumper("item")
    public static Collection<String> getItemBracketDump() {
        final HashSet<String> result = new HashSet<>();
        for(ResourceLocation key : ForgeRegistries.ITEMS.getKeys()) {
            result.add(String.format(Locale.ENGLISH, "<item:%s>", key));
        }
        return result;
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
    
    @BracketDumper("recipetype")
    public static Collection<String> getRecipeTypeDump() {
        final HashSet<String> result = new HashSet<>();
        for(ResourceLocation location: Registry.RECIPE_TYPE.keySet()) {
            result.add(String.format(Locale.ENGLISH, "<recipetype:%s>", location));
        }
        return result;
    }
    
    @BracketResolver("blockstate")
    public static MCBlockState getBlockState(String tokens) {
        if(!tokens.toLowerCase(Locale.ENGLISH).equals(tokens))
            CraftTweakerAPI.logWarning("BlockState BEP <blockstate:%s> does not seem to be lower-cased!", tokens);
        String[] split = tokens.split(":", 4);
        
        if(split.length > 1) {
            String blockName = split[0] + ":" + split[1];
            String properties = split.length > 2 ? split[2] : "";
            if(!ForgeRegistries.BLOCKS.containsKey(new ResourceLocation(blockName))) {
                CraftTweakerAPI.logThrowing("Error creating BlockState!", new IllegalArgumentException("Could not get BlockState from: <blockstate:" + tokens + ">! The block does not appear to exist!"));
            } else {
                return getBlockState(blockName, properties);
            }
        }
        CraftTweakerAPI.logThrowing("Error creating BlockState!", new IllegalArgumentException("Could not get BlockState from: <blockstate:" + tokens + ">!"));
        return null;
    }
    
    public static MCBlockState getBlockState(String name, String properties) {
        
        Block block = ForgeRegistries.BLOCKS.getValue(new ResourceLocation(name));
        if(block == null) {
            return null;
        }
        
        MCBlockState blockState = new MCBlockState(block.getDefaultState());
        if(properties != null && !properties.isEmpty()) {
            for(String propertyPair : properties.split(",")) {
                String[] splitPair = propertyPair.split("=");
                if(splitPair.length != 2) {
                    CraftTweakerAPI.logWarning("Invalid blockstate property format '" + propertyPair + "'. Using default property value.");
                    continue;
                }
                blockState = blockState.withProperty(splitPair[0], splitPair[1]);
            }
        }
        
        return blockState;
    }

    @BracketResolver("entityType")
    public static MCEntityType getEntityType(String tokens) {
        final int length = tokens.split(":").length;
        if(length == 0 || length > 2) {
            CraftTweakerAPI.logError("Could not get EntityType <entityType:%s>", tokens);
            return null;
        }
        final ResourceLocation resourceLocation = new ResourceLocation(tokens);
        if(!ForgeRegistries.ENTITIES.containsKey(resourceLocation)) {
            CraftTweakerAPI.logError("Could not get EntityType <entityType:%s>", tokens);
            return null;
        }

        //Cannot be null since we checked containsKey
        //noinspection ConstantConditions
        return new MCEntityType(ForgeRegistries.ENTITIES.getValue(resourceLocation));
    }

    @BracketDumper("entityType")
    public static Collection<String> getEntityTypeDump() {
        return ForgeRegistries.ENTITIES.getKeys().stream()
                .map(key -> "<entityType:" + key + ">")
                .collect(Collectors.toList());
    }
}
