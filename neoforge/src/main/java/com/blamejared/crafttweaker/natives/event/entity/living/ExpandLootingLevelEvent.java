package com.blamejared.crafttweaker.natives.event.entity.living;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.event.ZenEvent;
import com.blamejared.crafttweaker.api.event.bus.NeoForgeEventBusWire;
import com.blamejared.crafttweaker.api.event.bus.IEventBus;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.world.damagesource.DamageSource;
import net.neoforged.neoforge.event.entity.living.LootingLevelEvent;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@ZenEvent
@Document("neoforge/api/event/entity/living/LootingLevelEvent")
@NativeTypeRegistration(value = LootingLevelEvent.class, zenCodeName = "crafttweaker.neoforge.api.event.entity.living.LootingLevelEvent")
public class ExpandLootingLevelEvent {
    
    @ZenEvent.Bus
    public static final IEventBus<LootingLevelEvent> BUS = IEventBus.direct(
            LootingLevelEvent.class,
            NeoForgeEventBusWire.of()
    );
    
    @ZenCodeType.Nullable
    @ZenCodeType.Getter("damageSource")
    public static DamageSource getDamageSource(LootingLevelEvent internal) {
        
        return internal.getDamageSource();
    }
    
    @ZenCodeType.Getter("lootingLevel")
    public static int getLootingLevel(LootingLevelEvent internal) {
        
        return internal.getLootingLevel();
    }
    
    @ZenCodeType.Setter("lootingLevel")
    public static void setLootingLevel(LootingLevelEvent internal, int lootingLevel) {
        
        internal.setLootingLevel(lootingLevel);
    }
    
}
