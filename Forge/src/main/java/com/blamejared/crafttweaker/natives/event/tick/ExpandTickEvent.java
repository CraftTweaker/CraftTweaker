package com.blamejared.crafttweaker.natives.event.tick;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraftforge.event.TickEvent;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("forge/api/event/tick/TickEvent")
@NativeTypeRegistration(value = TickEvent.class, zenCodeName = "crafttweaker.api.event.tick.TickEvent")
public class ExpandTickEvent {
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("side")
    public static String getSide(TickEvent internal) {
        
        return internal.side.name();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("client")
    public static boolean isClient(TickEvent internal) {
        
        return internal.side.isClient();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("server")
    public static boolean isServer(TickEvent internal) {
        
        return internal.side.isServer();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("type")
    public static TickEvent.Type getType(TickEvent internal) {
        
        return internal.type;
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("phase")
    public static TickEvent.Phase getPhase(TickEvent internal) {
        
        return internal.phase;
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("start")
    public static boolean isStart(TickEvent internal) {
        
        return internal.phase == TickEvent.Phase.START;
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("end")
    public static boolean isEnd(TickEvent internal) {
        
        return internal.phase == TickEvent.Phase.END;
    }
    
    
}
