package com.blamejared.crafttweaker.natives.event.interact;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.event.ZenEvent;
import com.blamejared.crafttweaker.api.event.bus.ForgeEventBusWire;
import com.blamejared.crafttweaker.api.event.bus.IEventBus;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;

/**
 * This event is fired when a player left clicks while targeting a block.
 * This event controls which of {@link net.minecraft.world.level.block.Block#attack(BlockState, Level, BlockPos, Player)} and/or the item harvesting methods will be called.
 *
 * Note that if the event is canceled and the player holds down left mouse, the event will continue to fire.
 * This is due to how vanilla calls the left click handler methods.
 *
 * Also note that creative mode directly breaks the block without running any other logic.
 *
 * @docEvent canceled none of the above noted methods to be called.
 */
@ZenRegister
@ZenEvent
@Document("forge/api/event/interact/LeftClickEmptyEvent")
@NativeTypeRegistration(value = PlayerInteractEvent.LeftClickEmpty.class, zenCodeName = "crafttweaker.api.event.interact.LeftClickEmptyEvent")
public class ExpandLeftClickEmptyEvent {
    
    @ZenEvent.Bus
    public static final IEventBus<PlayerInteractEvent.LeftClickEmpty> BUS = IEventBus.direct(
            PlayerInteractEvent.LeftClickEmpty.class,
            ForgeEventBusWire.of()
    );
    
}
