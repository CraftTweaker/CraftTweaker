package com.blamejared.crafttweaker.api.action.network;

import com.blamejared.crafttweaker.api.CraftTweakerConstants;
import com.blamejared.crafttweaker.api.action.base.IUndoableAction;
import com.blamejared.crafttweaker.api.action.internal.CraftTweakerAction;
import com.blamejared.crafttweaker.api.network.CTNetwork;
import com.blamejared.crafttweaker.api.network.CTNetworkReceiver;
import com.blamejared.crafttweaker.api.zencode.IScriptLoadSource;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;

public class ActionAddDataReceiver extends CraftTweakerAction implements IUndoableAction {
    
    private final String id;
    private final CTNetworkReceiver receiver;
    
    public ActionAddDataReceiver(String id, CTNetworkReceiver receiver) {
        
        this.id = id;
        this.receiver = receiver;
    }
    
    @Override
    public void apply() {
        
        CTNetwork.INSTANCE.clientReceivers.computeIfAbsent(id, s -> new ArrayList<>()).add(receiver);
    }
    
    @Override
    public void undo() {
        CTNetwork.INSTANCE.clientReceivers.computeIfPresent(id, (s, receivers) -> {
            receivers.remove(receiver);
            return receivers.isEmpty() ? null : receivers;
        });
    }
    
    @Override
    public String describe() {
        
        return "Adding a data receiver with id: " + id;
    }
    
    @Override
    public String describeUndo() {
        
        return "Undoing addition of a data receiver with id: " + id;
    }
    
    @Override
    public boolean shouldApplyOn(IScriptLoadSource source, Logger logger) {
        
        return true;
    }
    
}
