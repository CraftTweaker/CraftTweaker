package com.blamejared.crafttweaker.impl.brackets;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.annotations.*;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.impl.item.MCItemStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistries;
import org.openzen.zencode.java.ZenCodeType;

import java.util.Locale;

@ZenRegister
@ZenCodeType.Name("crafttweaker.api.BracketHandlers")
public class BracketHandlers {
    
    @BracketResolver("item")
    public static IItemStack getItem(String tokens) {
        if(!tokens.toLowerCase(Locale.ENGLISH).equals(tokens))
            CraftTweakerAPI.logWarning("Item BEP <item:%s> does not seem to be lower-cased!", tokens);
        
        final String[] split = tokens.split(":");
        if(split.length > 3)
            throw new IllegalArgumentException("Could not get item with name: <item:" + tokens + ">");
        
        final ItemStack value = new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation(split[0], split[1])));
        if(split.length == 3)
            value.setDamage(Integer.parseInt(split[2], 10));
        return new MCItemStack(value);
    }
    
}
