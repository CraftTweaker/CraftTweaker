package com.blamejared.crafttweaker.impl_native.event.entity.player.interact;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.impl.util.MCDirection;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import org.openzen.zencode.java.ZenCodeType;

/**
 * This event is fired on both sides whenever the player right clicks while targeting a block.
 * This event controls which of Item.onItemUseFirst, Block.onBlockActivated, and Item.onItemUse will be called.
 *
 * @docEvent canceled will cause none of the above three to be called.
 */
@ZenRegister
@Document("vanilla/api/event/entity/player/interact/MCRightClickBlockEvent")
@NativeTypeRegistration(value = PlayerInteractEvent.RightClickBlock.class, zenCodeName = "crafttweaker.api.event.entity.player.interact.MCRightClickBlockEvent")
public class ExpandRightClickBlockEvent {
    
    /**
     * The face of the block that was clicked
     * Unlike in {@link PlayerInteractEvent} this is known not to be null
     */
    @ZenCodeType.Method
    @ZenCodeType.Getter("face")
    public static MCDirection getFace(PlayerInteractEvent internal) {
        return MCDirection.get(internal.getFace());
    }
}
