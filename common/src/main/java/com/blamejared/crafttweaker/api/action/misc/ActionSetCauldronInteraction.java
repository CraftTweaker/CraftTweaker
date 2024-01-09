package com.blamejared.crafttweaker.api.action.misc;

import com.blamejared.crafttweaker.api.action.base.IUndoableAction;
import com.blamejared.crafttweaker.api.action.internal.CraftTweakerAction;
import net.minecraft.core.cauldron.CauldronInteraction;
import net.minecraft.world.item.Item;

public class ActionSetCauldronInteraction extends CraftTweakerAction implements IUndoableAction {
    
    private final CauldronInteraction.InteractionMap map;
    private final Item key;
    private final CauldronInteraction interaction;
    private CauldronInteraction previous;
    
    public ActionSetCauldronInteraction(CauldronInteraction.InteractionMap map, Item key, CauldronInteraction interaction) {
        
        this.map = map;
        this.key = key;
        this.interaction = interaction;
    }
    
    @Override
    public void apply() {
        
        if(interaction == null) {
            previous = map.map().remove(key);
        } else {
            previous = map.map().put(key, interaction);
        }
    }
    
    @Override
    public String describe() {
        
        return "Set Cauldron interaction for " + key;
    }
    
    @Override
    public void undo() {
        
        if(previous == null) {
            map.map().remove(key);
        } else {
            map.map().put(key, previous);
        }
    }
    
    @Override
    public String describeUndo() {
        
        return "Undoing setting of Cauldron interaction for " + key;
    }
    
}
