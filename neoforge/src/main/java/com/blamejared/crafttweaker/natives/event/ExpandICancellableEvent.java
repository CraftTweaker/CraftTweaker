package com.blamejared.crafttweaker.natives.event;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.neoforged.bus.api.ICancellableEvent;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("neoforge/api/event/ICancellableEvent")
@NativeTypeRegistration(value = ICancellableEvent.class, zenCodeName = "crafttweaker.neoforge.api.event.ICancellableEvent")
public class ExpandICancellableEvent {
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("canceled")
    public static boolean isCanceled(ICancellableEvent internal) {
        
        return internal.isCanceled();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Setter("canceled")
    public static void setCanceled(ICancellableEvent internal, boolean cancel) {
        
        internal.setCanceled(cancel);
    }
    
    /**
     * Cancels the event. Same as `setCanceled(true)`
     */
    @ZenCodeType.Method
    public static void cancel(ICancellableEvent internal) {
        
        setCanceled(internal, true);
    }
    
}
