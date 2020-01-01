package com.blamejared.crafttweaker.api.events;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import net.minecraftforge.eventbus.api.Event;
import org.openzen.zencode.java.ZenCodeType;

import java.util.function.Consumer;

@ZenRegister()
@ZenCodeType.Name("crafttweaker.api.event.IEvent")
public abstract class IEvent<E extends IEvent<E, V>, V extends Event> {
    
    protected V internal;
    
    private Consumer<E> handler;
    
    public IEvent(V internal) {
        this.internal = internal;
    }
    
    @ZenCodeType.Constructor
    public IEvent(Consumer<E> handler) {
        this.handler = handler;
    }
    
    public void setInternal(V internal) {
        this.internal = internal;
    }
    
    public abstract Consumer<V> getConsumer();
    
    
    public V getInternal() {
        return internal;
    }
    
    public Consumer<E> getHandler() {
        return handler;
    }
    
    public void setHandler(Consumer<E> handler) {
        this.handler = handler;
    }

    public String getName() {
        //Or should we leave this abstract and force them to give a name?
        return this.getClass().getSimpleName();
    }
}
