package com.blamejared.crafttweaker.impl_native.event.entity.player.interact;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.impl.util.MCDirection;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import org.openzen.zencode.java.ZenCodeType;

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
