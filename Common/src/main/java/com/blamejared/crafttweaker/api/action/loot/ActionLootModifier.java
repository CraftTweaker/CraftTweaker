package com.blamejared.crafttweaker.api.action.loot;

import com.blamejared.crafttweaker.api.action.base.IRuntimeAction;
import com.blamejared.crafttweaker.api.action.internal.CraftTweakerAction;
import com.blamejared.crafttweaker.api.loot.modifier.ILootModifier;
import com.google.common.collect.ImmutableMap;
import net.minecraft.resources.ResourceLocation;
import org.apache.logging.log4j.Logger;

import java.util.Map;
import java.util.function.Supplier;

public abstract class ActionLootModifier extends CraftTweakerAction implements IRuntimeAction {
    
    private final Supplier<Map<ResourceLocation, ILootModifier>> modifiersGetter;
    
    protected ActionLootModifier(final Supplier<Map<ResourceLocation, ILootModifier>> modifiersGetter) {
        
        this.modifiersGetter = modifiersGetter;
    }
    
    protected Map<ResourceLocation, ILootModifier> modifiersMap() {
        
        return this.modifiersGetter.get();
    }
    
    @Override
    public boolean validate(final Logger logger) {
        
        if(this.modifiersMap() == null) {
            logger.error("Unable to modify loot modifier registry without access to it", new NullPointerException("Illegal loot modifiers registry getter"));
            return false;
        }
        if(this.modifiersMap() instanceof ImmutableMap) { // Just in case the map getter is broken (e.g. if a mod attempts to use this class with the wrong getter)
            logger.error("Unable to modify loot modifier registry because it's frozen: you may need to update", new IllegalAccessError("Loot modifiers registry is frozen"));
            return false;
        }
        return true;
    }
    
}
