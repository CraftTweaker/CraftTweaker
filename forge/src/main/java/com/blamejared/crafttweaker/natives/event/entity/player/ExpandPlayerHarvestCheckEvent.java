package com.blamejared.crafttweaker.natives.event.entity.player;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.event.ZenEvent;
import com.blamejared.crafttweaker.api.event.bus.ForgeEventBusWire;
import com.blamejared.crafttweaker.api.event.bus.IEventBus;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.entity.player.PlayerEvent;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@ZenEvent
@Document("forge/api/event/entity/player/PlayerHarvestCheckEvent")
@NativeTypeRegistration(value = PlayerEvent.HarvestCheck.class, zenCodeName = "crafttweaker.forge.api.event.entity.player.PlayerHarvestCheckEvent")
public class ExpandPlayerHarvestCheckEvent {
    
    @ZenEvent.Bus
    public static final IEventBus<PlayerEvent.HarvestCheck> BUS = IEventBus.direct(
            PlayerEvent.HarvestCheck.class,
            ForgeEventBusWire.of()
    );
    
    @ZenCodeType.Getter("targetBlock")
    public static BlockState getTargetBlock(PlayerEvent.HarvestCheck internal) {
        
        return internal.getTargetBlock();
    }
    
    @ZenCodeType.Getter("canHarvest")
    public static boolean canHarvest(PlayerEvent.HarvestCheck internal) {
        
        return internal.canHarvest();
    }
    
    @ZenCodeType.Setter("canHarvest")
    public static void setCanHarvest(PlayerEvent.HarvestCheck internal, boolean success) {
        
        internal.setCanHarvest(success);
    }
    
}
