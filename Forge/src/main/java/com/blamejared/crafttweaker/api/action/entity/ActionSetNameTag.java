package com.blamejared.crafttweaker.api.action.entity;

import com.blamejared.crafttweaker.api.action.base.IUndoableAction;
import com.blamejared.crafttweaker.api.action.internal.CraftTweakerAction;
import com.blamejared.crafttweaker.api.entity.INameTagFunction;
import com.blamejared.crafttweaker.api.zencode.IScriptLoadSource;
import com.blamejared.crafttweaker.platform.Services;
import net.minecraft.world.entity.Entity;

import java.util.function.Predicate;

public class ActionSetNameTag extends CraftTweakerAction implements IUndoableAction {
    
    private final Predicate<Entity> predicate;
    private final INameTagFunction function;
    
    public ActionSetNameTag(Predicate<Entity> predicate, INameTagFunction function) {
        
        this.predicate = predicate;
        this.function = function;
    }
    
    @Override
    public void apply() {
        
        Services.CLIENT.NAMETAGS.put(predicate, function);
    }
    
    @Override
    public void undo() {
        
        Services.CLIENT.NAMETAGS.remove(predicate);
    }
    
    @Override
    public String describe() {
        
        return "Adding a custom name tag for an entity!";
    }
    
    @Override
    public String describeUndo() {
        
        return "Undoing addition of a custom name tag for an entity!";
    }
    
    @Override
    public boolean shouldApplyOn(IScriptLoadSource source) {
        
        return Services.DISTRIBUTION.isClient();
    }
    
}
