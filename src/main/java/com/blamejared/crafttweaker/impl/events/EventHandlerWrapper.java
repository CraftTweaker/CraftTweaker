package com.blamejared.crafttweaker.impl.events;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import net.minecraftforge.eventbus.api.Event;

import java.util.function.Consumer;

public class EventHandlerWrapper<T extends Event> implements Consumer<T> {
    
    public EventHandlerWrapper(Consumer<T> consumer) {
        
        this.consumer = consumer;
    }
    
    private final Consumer<T> consumer;
    
    @Override
    public void accept(T t) {
        
        try {
            consumer.accept(t);
        } catch(Throwable throwable) {
            CraftTweakerAPI.logError(throwable.getMessage(), throwable);
        }
    }
    
}
