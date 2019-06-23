package com.blamejared.crafttweaker.impl.brackets;

import com.blamejared.crafttweaker.api.annotations.*;
import com.blamejared.crafttweaker.api.item.*;
import com.blamejared.crafttweaker.impl.item.*;
import net.minecraft.item.*;
import net.minecraft.util.*;
import net.minecraftforge.registries.*;
import org.openzen.zencode.java.*;

@ZenRegister
@ZenCodeType.Name("crafttweaker.api.BracketHandlers")
public class BracketHandlers {
    
    @BracketResolver("item")
    public static IItemStack getItem(String tokens) {
        return new MCItemStack(new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation(tokens))));
    }
    
}
