package com.blamejared.crafttweaker.api.action.entity;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.CraftTweakerConstants;
import com.blamejared.crafttweaker.api.action.base.IUndoableAction;
import com.blamejared.crafttweaker.api.entity.INameplateFunction;
import com.blamejared.crafttweaker.api.zencode.IScriptLoadSource;
import com.blamejared.crafttweaker.impl.event.CTClientEventHandler;
import com.blamejared.crafttweaker.platform.Services;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.fml.LogicalSide;

import java.util.Objects;
import java.util.function.Predicate;

public class ActionSetNameplate implements IUndoableAction {
    
    private final Predicate<Entity> predicate;
    private final INameplateFunction function;
    
    public ActionSetNameplate(Predicate<Entity> predicate, INameplateFunction function) {
        
        this.predicate = predicate;
        this.function = function;
    }
    
    @Override
    public void apply() {
    
        Services.CLIENT.NAMEPLATES.put(predicate, function);
    }
    
    @Override
    public void undo() {
    
        Services.CLIENT.NAMEPLATES.remove(predicate);
    }
    
    @Override
    public String describe() {
        
        return "Adding a custom nameplate for an entity!";
    }
    
    @Override
    public String describeUndo() {
        
        return "Undoing addition of a custom nameplate for an entity!";
    }
    
    @Override
    public boolean shouldApplyOn(IScriptLoadSource source) {
        
        return Services.DISTRIBUTION.isClient();
    }
    
}
