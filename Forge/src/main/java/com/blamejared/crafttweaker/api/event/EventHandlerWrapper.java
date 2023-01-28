package com.blamejared.crafttweaker.api.event;

import com.blamejared.crafttweaker.api.logging.CommonLoggers;
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
            CommonLoggers.api().error("Error occurred in event handler", throwable);
        }
    }
    
}
