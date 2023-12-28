package com.blamejared.crafttweaker.natives.event;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraftforge.eventbus.api.Event;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("forge/api/event/Event")
@NativeTypeRegistration(value = Event.class, zenCodeName = "crafttweaker.forge.api.event.Event")
public class ExpandEvent {
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("cancelable")
    public static boolean isCancelable(Event internal) {
        
        return internal.isCancelable();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("canceled")
    public static boolean isCanceled(Event internal) {
        
        return internal.isCanceled();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Setter("canceled")
    public static void setCanceled(Event internal, boolean cancel) {
        
        internal.setCanceled(cancel);
    }
    
    /**
     * Cancels the event. Same as `setCanceled(true)`
     */
    @ZenCodeType.Method
    public static void cancel(Event internal) {
        
        setCanceled(internal, true);
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("hasResult")
    public static boolean hasResult(Event internal) {
        
        return internal.hasResult();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("result")
    public static Event.Result getResult(Event internal) {
        
        return internal.getResult();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Setter
    public static void setResult(Event internal, Event.Result result) {
        
        internal.setResult(result);
    }
    
    /**
     * sets the event's result to `allow`
     */
    @ZenCodeType.Method
    public static void setAllow(Event internal) {
        
        internal.setResult(Event.Result.ALLOW);
    }
    
    /**
     * sets the event's result to `deny`
     */
    @ZenCodeType.Method
    public static void setDeny(Event internal) {
        
        internal.setResult(Event.Result.DENY);
    }
    
    /**
     * sets the event's result to `default`
     */
    @ZenCodeType.Method
    public static void setDefault(Event internal) {
        
        internal.setResult(Event.Result.DEFAULT);
    }
    
}
