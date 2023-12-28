package com.blamejared.crafttweaker.natives.event.xp;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.event.ForgeEventCancellationCarrier;
import com.blamejared.crafttweaker.api.event.ZenEvent;
import com.blamejared.crafttweaker.api.event.bus.ForgeEventBusWire;
import com.blamejared.crafttweaker.api.event.bus.IEventBus;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.living.LivingExperienceDropEvent;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@ZenEvent
@Document("forge/api/event/xp/LivingExperienceDropEvent")
@NativeTypeRegistration(value = LivingExperienceDropEvent.class, zenCodeName = "crafttweaker.forge.api.event.xp.LivingExperienceDropEvent")
public class ExpandLivingExperienceDropEvent {
    
    @ZenEvent.Bus
    public static final IEventBus<LivingExperienceDropEvent> BUS = IEventBus.cancelable(
            LivingExperienceDropEvent.class,
            ForgeEventBusWire.of(),
            ForgeEventCancellationCarrier.of()
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
