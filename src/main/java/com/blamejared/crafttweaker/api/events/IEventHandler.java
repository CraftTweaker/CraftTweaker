package com.blamejared.crafttweaker.api.events;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@ZenCodeType.Name("crafttweaker.api.events.IEventHandler")
public interface IEventHandler<E extends IEvent> {
    
    void handle(E event);
    
}