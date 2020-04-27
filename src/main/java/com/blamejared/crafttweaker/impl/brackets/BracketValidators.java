package com.blamejared.crafttweaker.impl.brackets;

import com.blamejared.crafttweaker.api.*;
import com.blamejared.crafttweaker.api.annotations.*;
import net.minecraft.util.*;
import net.minecraftforge.registries.*;
import org.openzen.zencode.java.*;

import java.util.*;

@ZenRegister
@ZenCodeType.Name("crafttweaker.api.BracketValidators")
public class BracketValidators {
    
    private BracketValidators() {
    }
    
    @ZenCodeType.Method
    @BracketValidator("item")
    public static boolean validateItemBracket(String tokens) {
        if(!tokens.toLowerCase(Locale.ENGLISH).equals(tokens)) {
            CraftTweakerAPI.logWarning("Item BEP <item:%s> does not seem to be lower-cased!", tokens);
        }
    
        final String[] split = tokens.split(":");
        if(split.length != 2) {
            CraftTweakerAPI.logError("Could not get item with name: <item:" + tokens + ">! Syntax is <item:modid:itemname>");
            return false;
        }
        ResourceLocation key = new ResourceLocation(split[0], split[1]);
        if(((ForgeRegistry<?>) ForgeRegistries.ITEMS).isLocked() && !ForgeRegistries.ITEMS.containsKey(key)) {
            CraftTweakerAPI.logError("Could not get item with name: <item:" + tokens + ">! Item does not appear to exist!");
            return false;
        }
        
        return true;
    }
}
