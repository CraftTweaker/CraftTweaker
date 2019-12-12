package com.blamejared.crafttweaker.api.events;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import net.minecraftforge.eventbus.api.Event;
import org.openzen.zencode.java.ZenCodeType;

import java.util.function.Consumer;

@ZenRegister
@ZenCodeType.Name("crafttweaker.api.event.IEvent")
public abstract class IEvent<E extends IEvent, V extends Event> {
    
    private V internal;
    
    private IEventHandler<E> handler;
    
    public IEvent(V internal) {
        this.internal = internal;
    }
    
    @ZenCodeType.Constructor
    public IEvent(IEventHandler<E> handler) {
        this.handler = handler;
    }
    
    public void setInternal(V internal) {
        this.internal = internal;
    }
    
    public abstract Consumer<V> getConsumer();
    
    
    public V getInternal() {
        return internal;
    }
    
    public IEventHandler<E> getHandler() {
        return handler;
    }
    
    public void setHandler(IEventHandler<E> handler) {
        this.handler = handler;
    }
}
