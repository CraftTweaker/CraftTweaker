package com.blamejared.crafttweaker.natives.event;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.neoforged.bus.api.Event;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("neoforge/api/event/Event")
@NativeTypeRegistration(value = Event.class, zenCodeName = "crafttweaker.neoforge.api.event.Event")
public class ExpandEvent {
    
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
