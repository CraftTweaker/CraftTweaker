package com.blamejared.crafttweaker.api.item;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import net.minecraft.item.ItemStack;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@ZenCodeType.Expansion("crafttweaker.ItemStack")
public class ExpandNativeItemStack {
    
    @ZenCodeType.Method
    public static void print(ItemStack _this) {
        CraftTweakerAPI.logInfo("%s", _this);
    }
}
