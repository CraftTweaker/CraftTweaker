package com.blamejared.crafttweaker.impl_native.item;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.impl.item.MCItemStack;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.item.Item;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("vanilla/api/item/MCItemDefinition")
@NativeTypeRegistration(value = Item.class, zenCodeName = "crafttweaker.api.item.MCItemDefinition")
public class ExpandItem {
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("defaultInstance")
    @ZenCodeType.Caster(implicit = true)
    public static IItemStack getDefaultInstance(Item internal) {
        
        return new MCItemStack(internal.getDefaultInstance());
    }
    
    @ZenCodeType.Getter("commandString")
    public static String getCommandString(Item internal) {
        
        return "<item:" + internal.getRegistryName() + ">.definition";
    }
    
}