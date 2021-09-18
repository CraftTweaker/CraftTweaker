package com.blamejared.crafttweaker.impl_native.event.entity.player.interact;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;

/**
 * This event is fired when a player left clicks while targeting a block.
 * This event controls which of {@link net.minecraft.block.Block#onBlockClicked} and/or the item harvesting methods will be called.
 *
 * Note that if the event is canceled and the player holds down left mouse, the event will continue to fire.
 * This is due to how vanilla calls the left click handler methods.
 *
 * Also note that creative mode directly breaks the block without running any other logic.
 *
 * @docEvent canceled none of the above noted methods to be called.
 */
@ZenRegister
@Document("vanilla/api/event/entity/player/interact/MCLeftClickBlockEvent")
@NativeTypeRegistration(value = PlayerInteractEvent.LeftClickBlock.class, zenCodeName = "crafttweaker.api.event.entity.player.interact.MCLeftClickBlockEvent")
public class ExpandLeftClickBlockEvent {
}
