package com.blamejared.crafttweaker.impl_native.event.block;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.impl.util.MCDirection;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraftforge.event.world.BlockEvent;
import org.openzen.zencode.java.ZenCodeType;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Fired when a physics update occurs on a block. This event acts as
 * a way for mods to detect physics updates, in the same way a BUD switch
 * does. This event is only called on the server.
 *
 * @docEvent canceled vanilla logic won't be executed
 */
@ZenRegister
@Document("vanilla/api/event/block/MCBlockNeighborNotifyEvent")
@NativeTypeRegistration(value = BlockEvent.NeighborNotifyEvent.class, zenCodeName = "crafttweaker.api.event.block.MCBlockNeighborNotifyEvent")
public class ExpandBlockNeighborNotifyEvent {
    
    /**
     * Gets if a redstone update was forced during setBlock call
     */
    @ZenCodeType.Method
    @ZenCodeType.Getter("forceRedstoneUpdate")
    public static boolean getForceRedstoneUpdate(BlockEvent.NeighborNotifyEvent internal) {
        
        return internal.getForceRedstoneUpdate();
    }
    
    /**
     * Gets a list of directions from the base block that updates will occur upon.
     */
    @ZenCodeType.Method
    @ZenCodeType.Getter("notifiedSides")
    public static List<MCDirection> getNotifiedSides(BlockEvent.NeighborNotifyEvent internal) {
        
        return internal.getNotifiedSides().stream().map(MCDirection::get).collect(Collectors.toList());
    }
    
}
