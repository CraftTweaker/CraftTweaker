package com.blamejared.crafttweaker.natives.event.tick;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.BracketEnum;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.fml.LogicalSide;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("forge/api/event/tick/TickEvent")
@NativeTypeRegistration(value = TickEvent.class, zenCodeName = "crafttweaker.forge.api.event.tick.TickEvent")
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
    @Document("forge/api/event/tick/TickEventType")
    @NativeTypeRegistration(value = TickEvent.Type.class, zenCodeName = "crafttweaker.forge.api.event.tick.TickEventType")
    @BracketEnum("forge:event/tick/type")
    public static class ExpandTickEventType {
    
    }
    
    @ZenRegister
    @Document("forge/api/event/tick/TickEventPhase")
    @NativeTypeRegistration(value = TickEvent.Phase.class, zenCodeName = "crafttweaker.forge.api.event.tick.TickEventPhase")
    @BracketEnum("forge:event/tick/phase")
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
