package com.blamejared.crafttweaker.impl.actions.entities;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.actions.IUndoableAction;
import com.blamejared.crafttweaker.api.entity.INameplateFunction;
import com.blamejared.crafttweaker.impl.events.CTClientEventHandler;
import net.minecraft.entity.Entity;
import net.minecraftforge.fml.LogicalSide;

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
        
        CTClientEventHandler.NAMEPLATES.put(predicate, function);
    }
    
    @Override
    public void undo() {
        
        CTClientEventHandler.NAMEPLATES.remove(predicate);
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
    public boolean shouldApplyOn(LogicalSide side) {
        
        return !CraftTweakerAPI.isServer();
    }
    
}
