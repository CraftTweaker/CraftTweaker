package com.blamejared.crafttweaker.gametest.api.event;

import com.blamejared.crafttweaker.api.action.base.IUndoableAction;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.EventPriority;

import java.util.function.Consumer;

public class ActionRegisterTestEvent<T extends Event> implements IUndoableAction {
    
    private final Class<T> typeOfT;
    private final Consumer<T> consumer;
    private final EventPriority priority;
    
    public ActionRegisterTestEvent(Class<T> typeOfT, Consumer<T> consumer, EventPriority priority) {
        
        this.typeOfT = typeOfT;
        this.consumer = new TestEventHandlerWrapper<>(consumer);
        this.priority = priority;
    }
    
    @Override
    public void apply() {
        
        MinecraftForge.EVENT_BUS.addListener(priority, false, typeOfT, consumer);
    }
    
    @Override
    public String describe() {
        
        return "Registering test event listener for " + typeOfT.getSimpleName() + ".";
    }
    
    @Override
    public void undo() {
    
    }
    
    @Override
    public String describeUndo() {
        
        return "null";
    }
    
    @Override
    public String systemName() {
        
        return "CraftTweaker-Tests";
    }
    
}
