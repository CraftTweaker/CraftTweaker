package com.blamejared.crafttweaker.natives.event.xp;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.event.NeoForgeEventCancellationCarrier;
import com.blamejared.crafttweaker.api.event.ZenEvent;
import com.blamejared.crafttweaker.api.event.bus.NeoForgeEventBusWire;
import com.blamejared.crafttweaker.api.event.bus.IEventBus;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.event.entity.living.LivingExperienceDropEvent;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@ZenEvent
@Document("neoforge/api/event/xp/LivingExperienceDropEvent")
@NativeTypeRegistration(value = LivingExperienceDropEvent.class, zenCodeName = "crafttweaker.neoforge.api.event.xp.LivingExperienceDropEvent")
public class ExpandLivingExperienceDropEvent {
    
    @ZenEvent.Bus
    public static final IEventBus<LivingExperienceDropEvent> BUS = IEventBus.cancelable(
            LivingExperienceDropEvent.class,
            NeoForgeEventBusWire.of(),
            NeoForgeEventCancellationCarrier.of()
    );
    
    @ZenCodeType.Getter("droppedExperience")
    public static int getDroppedExperience(LivingExperienceDropEvent internal) {
        
        return internal.getDroppedExperience();
    }
    
    @ZenCodeType.Setter("droppedExperience")
    public static void setDroppedExperience(LivingExperienceDropEvent internal, int droppedExperience) {
        
        internal.setDroppedExperience(droppedExperience);
    }
    
    @ZenCodeType.Nullable
    @ZenCodeType.Getter("attackingPlayer")
    public static Player getAttackingPlayer(LivingExperienceDropEvent internal) {
        
        return internal.getAttackingPlayer();
    }
    
    @ZenCodeType.Getter("originalExperience")
    public static int getOriginalExperience(LivingExperienceDropEvent internal) {
        
        return internal.getOriginalExperience();
    }
    
}
