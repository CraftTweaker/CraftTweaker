package com.blamejared.crafttweaker.natives.entity;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.action.entity.ActionSetNameplate;
import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.entity.INameplateFunction;
import net.minecraft.world.entity.EntityType;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@ZenCodeType.Expansion("crafttweaker.api.entity.EntityType")
public class ExpandEntityTypeForge {
    
    /**
     * Sets the nameplate handler for this EntityType.
     *
     * @param function The function that controls how this EntityType's nameplate is rendered.
     *
     * @docParam function (entity, result) => {
     * result.setAllow();
     * result.content = "Custom name! Position: " + entity.position;
     * })
     */
    @ZenCodeType.Method
    public static void setNameplate(EntityType internal, INameplateFunction function) {
        
        CraftTweakerAPI.apply(new ActionSetNameplate(entity -> entity.getType().equals(internal), function));
    }
    
}
