package com.blamejared.crafttweaker.natives.item;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import com.blamejared.crafttweaker_annotations.annotations.TaggableElement;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("vanilla/api/item/ItemDefinition")
@NativeTypeRegistration(value = Item.class, zenCodeName = "crafttweaker.api.item.ItemDefinition")
@TaggableElement(value = "minecraft:item")
public class ExpandItem {
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("defaultInstance")
    @ZenCodeType.Caster(implicit = true)
    public static IItemStack getDefaultInstance(Item internal) {
        
        return IItemStack.of(internal.getDefaultInstance());
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("registryName")
    public static ResourceLocation getRegistryName(Item internal) {
        
        return Registry.ITEM.getKey(internal);
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("commandString")
    public static String getCommandString(Item internal) {
        
        return "<item:" + Registry.ITEM.getKey(internal) + ">.definition";
    }
    
    
}