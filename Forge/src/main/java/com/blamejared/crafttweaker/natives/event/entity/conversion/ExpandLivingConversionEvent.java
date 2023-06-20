package com.blamejared.crafttweaker.natives.event.entity.conversion;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.event.ForgeEventCancellationCarrier;
import com.blamejared.crafttweaker.api.event.ZenEvent;
import com.blamejared.crafttweaker.api.event.bus.ForgeEventBusWire;
import com.blamejared.crafttweaker.api.event.bus.IEventBus;
import com.blamejared.crafttweaker.api.util.GenericUtil;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.event.entity.living.LivingConversionEvent;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("forge/api/event/entity/living/conversion/LivingConversionEvent")
@NativeTypeRegistration(value = LivingConversionEvent.class, zenCodeName = "crafttweaker.forge.api.event.entity.conversion.LivingConversionEvent")
public class ExpandLivingConversionEvent {
    
    
    @ZenRegister
    @ZenEvent
    @Document("forge/api/event/entity/conversion/LivingConversionPreEvent")
    @NativeTypeRegistration(value = LivingConversionEvent.Pre.class, zenCodeName = "crafttweaker.forge.api.event.conversion.LivingConversionPreEvent")
    public static class ExpandLivingConversionPreEvent {
        
        @ZenEvent.Bus
        public static final IEventBus<LivingConversionEvent.Pre> BUS = IEventBus.cancelable(
                LivingConversionEvent.Pre.class,
                ForgeEventBusWire.of(),
                ForgeEventCancellationCarrier.of()
        );
        
        @ZenCodeType.Getter("outcome")
        public static EntityType<Entity> getOutcome(LivingConversionEvent.Pre internal) {
            
            return GenericUtil.uncheck(internal.getOutcome());
        }
        
        @ZenCodeType.Setter("conversionTimer")
        public static void setConversionTimer(LivingConversionEvent.Pre internal, int ticks) {
            
            internal.setConversionTimer(ticks);
        }
        
    }
    
    @ZenRegister
    @ZenEvent
    @Document("forge/api/event/entity/conversion/LivingConversionPostEvent")
    @NativeTypeRegistration(value = LivingConversionEvent.Post.class, zenCodeName = "crafttweaker.forge.api.event.entity.conversion.LivingConversionPostEvent")
    public static class ExpandLivingConversionPostEvent {
        
        @ZenEvent.Bus
        public static final IEventBus<LivingConversionEvent.Post> BUS = IEventBus.direct(
                LivingConversionEvent.Post.class,
                ForgeEventBusWire.of()
        );
        
        @ZenCodeType.Getter("outcome")
        public static LivingEntity getOutcome(LivingConversionEvent.Post internal) {
            
            return internal.getOutcome();
        }
        
    }
    
}
