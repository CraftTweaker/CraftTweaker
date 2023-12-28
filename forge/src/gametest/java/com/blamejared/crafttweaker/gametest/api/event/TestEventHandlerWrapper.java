package com.blamejared.crafttweaker.gametest.api.event;

import com.blamejared.crafttweaker.api.logging.CommonLoggers;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.Event;

import java.util.function.Consumer;

public class TestEventHandlerWrapper<T extends Event> implements Consumer<T> {
    
    public TestEventHandlerWrapper(Consumer<T> consumer) {
        
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
        MinecraftForge.EVENT_BUS.unregister(this);
    }
    
}
