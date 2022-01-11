package com.blamejared.crafttweaker.api.event;

import com.blamejared.crafttweaker.api.command.event.ICTCommandRegisterEvent;
import com.blamejared.crafttweaker.api.recipe.replacement.event.IGatherReplacementExclusionEvent;
import com.blamejared.crafttweaker.api.zencode.bracket.IgnorePrefixCasingBracketParser;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;

import java.util.Arrays;
import java.util.function.Consumer;

public class CraftTweakerEvents {
    
    public static final Event<Consumer<ICTCommandRegisterEvent>> COMMAND_REGISTER_EVENT = EventFactory.createArrayBacked(Consumer.class, listeners -> event -> Arrays.stream(listeners)
            .forEach(handler -> handler.accept(event)));
    
    public static final Event<Consumer<IgnorePrefixCasingBracketParser>> REGISTER_BEP_EVENT = EventFactory.createArrayBacked(Consumer.class, listeners -> event -> Arrays.stream(listeners)
            .forEach(handler -> handler.accept(event)));
    
    public static final Event<Consumer<IGatherReplacementExclusionEvent>> GATHER_REPLACEMENT_EXCLUSION_EVENT = EventFactory.createArrayBacked(Consumer.class, listeners -> event -> Arrays.stream(listeners)
            .forEach(handler -> handler.accept(event)));
    
}
