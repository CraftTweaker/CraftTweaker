package com.blamejared.crafttweaker.impl.misc;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.impl.actions.misc.ActionSetCompostable;
import org.openzen.zencode.java.ZenCodeGlobals;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@ZenCodeType.Name("crafttweaker.api.Composter")
public class CTComposter {
    
    @ZenCodeGlobals.Global("composter")
    public static final CTComposter INSTANCE = new CTComposter();
    
    @ZenCodeType.Method
    public void setValue(IItemStack stack, float amount){
        CraftTweakerAPI.apply(new ActionSetCompostable(stack, amount));
    }
    
}
