package com.blamejared.crafttweaker.impl.misc;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.impl.actions.misc.ActionSetCompostable;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import org.openzen.zencode.java.ZenCodeGlobals;
import org.openzen.zencode.java.ZenCodeType;

/**
 * @docParam this composter
 */
@ZenRegister
@ZenCodeType.Name("crafttweaker.api.Composter")
@Document("vanilla/api/misc/Composter")
public class CTComposter {
    
    @ZenCodeGlobals.Global("composter")
    public static final CTComposter INSTANCE = new CTComposter();
    
    /**
     * Sets the compost value of the given IItemStack.
     *
     * The amount should be between 0 and 1, anything above 1 would just be wasted.
     *
     * @param stack  The stack to be compostable
     * @param amount The amount of Compost that should be added when the item is put in a Composter.
     *
     * @docParam stack <item:minecraft:diamond>
     * @docParam amount 0.85
     */
    @ZenCodeType.Method
    public void setValue(IItemStack stack, float amount) {
        
        CraftTweakerAPI.apply(new ActionSetCompostable(stack, amount));
    }
    
}
