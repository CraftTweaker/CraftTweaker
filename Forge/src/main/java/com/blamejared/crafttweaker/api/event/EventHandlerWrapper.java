package com.blamejared.crafttweaker.api.event;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.CraftTweakerConstants;
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
            CraftTweakerAPI.getLogger(CraftTweakerConstants.MOD_NAME)
                    .error("Error occurred in event handler", throwable);
        }
    }
    
}
