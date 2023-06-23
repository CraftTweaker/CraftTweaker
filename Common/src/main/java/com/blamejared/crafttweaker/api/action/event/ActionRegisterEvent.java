package com.blamejared.crafttweaker.api.action.event;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.action.base.IUndoableAction;
import com.blamejared.crafttweaker.api.action.internal.CraftTweakerAction;
import com.blamejared.crafttweaker.api.event.bus.IEventBus;
import com.blamejared.crafttweaker.api.event.bus.IHandlerToken;
import com.blamejared.crafttweaker.api.zencode.IScriptLoadSource;
import com.google.common.reflect.TypeToken;
import org.apache.logging.log4j.Logger;

import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;

public final class ActionRegisterEvent<T> extends CraftTweakerAction implements IUndoableAction {
    
    private final TypeToken<T> typeOfT;
    private final Function<IEventBus<T>, IHandlerToken<T>> function;
    
    private IHandlerToken<T> token;
    
    private ActionRegisterEvent(
            final TypeToken<T> typeOfT,
            final Function<IEventBus<T>, IHandlerToken<T>> function
    ) {
        
        this.typeOfT = typeOfT;
        this.function = function;
        this.token = null;
    }
    
    public static <T> ActionRegisterEvent<T> of(
            final TypeToken<T> token,
            final Consumer<T> consumer,
            final BiFunction<IEventBus<T>, Consumer<T>, IHandlerToken<T>> registrationFunction
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
    
    @Override
    public boolean shouldApplyOn(IScriptLoadSource source, Logger logger) {
        
        return true;
    }
    
}
