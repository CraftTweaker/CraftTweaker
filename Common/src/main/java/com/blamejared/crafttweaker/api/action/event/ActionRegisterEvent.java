package com.blamejared.crafttweaker.api.action.event;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.action.base.IUndoableAction;
import com.blamejared.crafttweaker.api.action.internal.CraftTweakerAction;
import com.blamejared.crafttweaker.api.event.bus.EventBus;
import com.blamejared.crafttweaker.api.event.bus.HandlerToken;
import com.google.common.reflect.TypeToken;

import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;

public final class ActionRegisterEvent<T> extends CraftTweakerAction implements IUndoableAction {
    
    private final TypeToken<T> typeOfT;
    private final Function<EventBus<T>, HandlerToken<T>> function;
    
    private HandlerToken<T> token;
    
    private ActionRegisterEvent(
            final TypeToken<T> typeOfT,
            final Function<EventBus<T>, HandlerToken<T>> function
    ) {
        
        this.typeOfT = typeOfT;
        this.function = function;
        this.token = null;
    }
    
    public static <T> ActionRegisterEvent<T> of(
            final TypeToken<T> token,
            final Consumer<T> consumer,
            final BiFunction<EventBus<T>, Consumer<T>, HandlerToken<T>> registrationFunction
    ) {
        
        // Just in case we want to add additional behavior to the consumer in the meantime
        return new ActionRegisterEvent<>(token, it -> registrationFunction.apply(it, consumer));
    }
    
    @Override
    public void apply() {
        
        this.token = this.function.apply(CraftTweakerAPI.getRegistry().getEventRegistry().busOf(this.typeOfT));
    }
    
    @Override
    public String describe() {
        
        return "Registering event listener for " + this.typeOfT;
    }
    
    @Override
    public void undo() {
        
        if (this.token != null) {
            CraftTweakerAPI.getRegistry().getEventRegistry().busOf(this.typeOfT).unregisterHandler(this.token);
        }
    }
    
    @Override
    public String describeUndo() {
        
        return "Unregistering event listener for " + this.typeOfT;
    }
    
}
