package com.blamejared.crafttweaker.api.action.event;

import com.blamejared.crafttweaker.api.action.base.IUndoableAction;
import com.blamejared.crafttweaker.api.event.EventHandlerWrapper;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.EventPriority;

import java.util.function.Consumer;

public class ActionRegisterEvent<T extends Event> implements IUndoableAction {
    
    private final Class<T> typeOfT;
    private final boolean listenToCancelled;
    private final Consumer<T> consumer;
    private final EventPriority priority;
    
    public ActionRegisterEvent(Class<T> typeOfT, Consumer<T> consumer, EventPriority priority) {
        
        this.typeOfT = typeOfT;
        this.listenToCancelled = false;
        this.consumer = new EventHandlerWrapper<>(consumer);
        this.priority = priority;
    }
    
    public ActionRegisterEvent(Class<T> typeOfT, boolean listenToCancelled, Consumer<T> consumer, EventPriority priority) {
        
        this.typeOfT = typeOfT;
        this.listenToCancelled = listenToCancelled;
        this.consumer = new EventHandlerWrapper<>(consumer);
        this.priority = priority;
    }
    
    @Override
    public void apply() {
        //Let's go completely safe and use the type
        MinecraftForge.EVENT_BUS.addListener(priority, listenToCancelled, typeOfT, consumer);
    }
    
    @Override
    public String describe() {
        
        return "Registering event listener for " + typeOfT.getSimpleName() + ".";
    }
    
    @Override
    public void undo() {
        
        MinecraftForge.EVENT_BUS.unregister(consumer);
    }
    
    @Override
    public String describeUndo() {
        
        return "Unregistering event listener for " + typeOfT.getSimpleName() + ".";
    }
    
}
