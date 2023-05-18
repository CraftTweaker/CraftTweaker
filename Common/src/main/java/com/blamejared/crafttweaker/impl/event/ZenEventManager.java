package com.blamejared.crafttweaker.impl.event;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.action.event.ActionRegisterEvent;
import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.event.bus.IHandlerToken;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.google.common.reflect.TypeToken;
import org.openzen.zencode.java.ZenCodeGlobals;
import org.openzen.zencode.java.ZenCodeType;

import java.util.function.BiFunction;
import java.util.function.Consumer;

@ZenRegister
@Document("vanilla/api/event/EventManager")
@ZenCodeType.Name("crafttweaker.api.events.EventManager")
public final class ZenEventManager {
    
    @ZenCodeGlobals.Global("events")
    public static final ZenEventManager EVENTS = new ZenEventManager();
    
    private ZenEventManager() {}
    
    @ZenCodeType.Method
    public <T> void register(final Class<T> typeOfT, final Consumer<T> consumer) {
        this.registerOnBus(typeOfT, consumer, EventBus::registerHandler);
    }
    
    @ZenCodeType.Method
    public <T> void register(final Class<T> typeOfT, final ZenEventPhase phase, final Consumer<T> consumer) {
        this.registerOnBus(typeOfT, consumer, (bus, event) -> bus.registerHandler(phase.phase(), event));
    }
    
    @ZenCodeType.Method
    public <T> void register(final Class<T> typeOfT, final boolean listenToCanceled, final Consumer<T> consumer) {
        this.registerOnBus(typeOfT, consumer, (bus, event) -> bus.registerHandler(listenToCanceled, event));
    }
    
    @ZenCodeType.Method
    public <T> void register(final Class<T> typeOfT, final ZenEventPhase phase, final boolean listenToCanceled, final Consumer<T> consumer) {
        this.registerOnBus(typeOfT, consumer, (bus, event) -> bus.registerHandler(phase.phase(), listenToCanceled, event));
    }
    
    private <T> void registerOnBus(
            final Class<T> typeOfT,
            final Consumer<T> consumer,
            final BiFunction<EventBus<T>, Consumer<T>, IHandlerToken<T>> regFun
    ) {
        
        CraftTweakerAPI.apply(ActionRegisterEvent.of(TypeToken.of(typeOfT), consumer, regFun));
    }
    
}
