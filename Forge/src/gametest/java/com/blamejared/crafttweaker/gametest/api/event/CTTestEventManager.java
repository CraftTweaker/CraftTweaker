package com.blamejared.crafttweaker.gametest.api.event;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.action.event.ActionRegisterEvent;
import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.EventPriority;
import org.openzen.zencode.java.ZenCodeType;

import java.util.function.Consumer;

@ZenRegister
@ZenCodeType.Name("crafttweaker.api.events.CTTestEventManager")
public class CTTestEventManager {
    
    @ZenCodeType.Method
    public static <T extends Event> void register(Class<T> typeOfT, Consumer<T> consumer) {
        
        register(typeOfT, EventPriority.NORMAL, consumer);
    }
    
    @ZenCodeType.Method
    public static <T extends Event> void register(Class<T> typeOfT, EventPriority priority, Consumer<T> consumer) {
        
        CraftTweakerAPI.apply(new ActionRegisterTestEvent<>(typeOfT, consumer, priority));
    }
    
    
}
