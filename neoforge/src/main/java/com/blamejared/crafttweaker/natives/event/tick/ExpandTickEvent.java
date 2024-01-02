package com.blamejared.crafttweaker.natives.event.tick;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.BracketEnum;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.neoforged.fml.LogicalSide;
import net.neoforged.neoforge.event.TickEvent;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("neoforge/api/event/tick/TickEvent")
@NativeTypeRegistration(value = TickEvent.class, zenCodeName = "crafttweaker.neoforge.api.event.tick.TickEvent")
public class ExpandTickEvent {
    
    @ZenCodeType.Getter
    public static TickEvent.Type getType(TickEvent internal) {
        
        return internal.type;
    }
    
    @ZenCodeType.Getter
    public static LogicalSide getSide(TickEvent internal) {
        
        return internal.side;
    }
    
    @ZenCodeType.Getter
    public static TickEvent.Phase getPhase(TickEvent internal) {
        
        return internal.phase;
    }
    
    @ZenRegister
    @Document("neoforge/api/event/tick/TickEventType")
    @NativeTypeRegistration(value = TickEvent.Type.class, zenCodeName = "crafttweaker.neoforge.api.event.tick.TickEventType")
    @BracketEnum("neoforge:event/tick/type")
    public static class ExpandTickEventType {
    
    }
    
    @ZenRegister
    @Document("neoforge/api/event/tick/TickEventPhase")
    @NativeTypeRegistration(value = TickEvent.Phase.class, zenCodeName = "crafttweaker.neoforge.api.event.tick.TickEventPhase")
    @BracketEnum("neoforge:event/tick/phase")
    public static class ExpandTickEventPhase {
        
        @ZenCodeType.Getter("isStart")
        public static boolean isStart(TickEvent.Phase internal) {
            
            return internal == TickEvent.Phase.START;
        }
        
        @ZenCodeType.Getter("isEnd")
        public static boolean isEnd(TickEvent.Phase internal) {
            
            return internal == TickEvent.Phase.END;
        }
        
    }
    
}
