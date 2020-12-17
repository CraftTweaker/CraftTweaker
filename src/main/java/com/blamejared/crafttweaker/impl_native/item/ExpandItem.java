package com.blamejared.crafttweaker.impl_native.item;

import com.blamejared.crafttweaker.api.annotations.*;
import com.blamejared.crafttweaker.api.item.*;
import com.blamejared.crafttweaker.impl.item.MCItemStack;
import com.blamejared.crafttweaker_annotations.annotations.*;
import net.minecraft.item.*;
import org.openzen.zencode.java.*;

@ZenRegister
@DocumentAsType
@NativeExpansion(Item.class)
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
