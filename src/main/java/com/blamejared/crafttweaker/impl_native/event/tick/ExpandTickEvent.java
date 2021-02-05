package com.blamejared.crafttweaker.impl_native.event.tick;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraftforge.event.TickEvent;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("vanilla/api/event/tick/MCTickEvent")
@NativeTypeRegistration(value = TickEvent.class, zenCodeName = "crafttweaker.api.event.tick.MCTickEvent")
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
    public static String getType(TickEvent internal) {
        
        return internal.type.name();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("phase")
    public static String getPhase(TickEvent internal) {
        
        return internal.phase.name();
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
