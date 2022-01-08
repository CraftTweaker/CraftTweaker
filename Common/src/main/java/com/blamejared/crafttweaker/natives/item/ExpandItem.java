package com.blamejared.crafttweaker.natives.item;


import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.platform.Services;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.world.item.Item;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("vanilla/api/item/ItemDefinition")
@NativeTypeRegistration(value = Item.class, zenCodeName = "crafttweaker.api.item.ItemDefinition")
public class ExpandItem {
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("defaultInstance")
    @ZenCodeType.Caster(implicit = true)
    public static IItemStack getDefaultInstance(Item internal) {
        
        return Services.PLATFORM.createMCItemStack(internal.getDefaultInstance());
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("commandString")
    public static String getCommandString(Item internal) {
        
        return "<item:" + Services.REGISTRY.getRegistryKey(internal) + ">.definition";
    }
    
    
}