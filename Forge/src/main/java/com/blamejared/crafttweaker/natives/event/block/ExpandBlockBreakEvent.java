package com.blamejared.crafttweaker.natives.event.block;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.level.BlockEvent;
import org.openzen.zencode.java.ZenCodeType;

/**
 * @docEvent canceled prevents the block from being broken.
 */
@ZenRegister
@Document("forge/api/event/block/BlockBreakEvent")
@NativeTypeRegistration(value = BlockEvent.BreakEvent.class, zenCodeName = "crafttweaker.api.event.block.BlockBreakEvent")
public class ExpandBlockBreakEvent {
    
    /**
     * Gets the player who broke the block. If no player is available, use a fake player
     */
    @ZenCodeType.Method
    @ZenCodeType.Getter("player")
    public static Player getPlayer(BlockEvent.BreakEvent internal) {
        
        return internal.getPlayer();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("expToDrop")
    public static int getExpToDrop(BlockEvent.BreakEvent internal) {
        
        return internal.getExpToDrop();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Setter("expToDrop")
    public static void setExpToDrop(BlockEvent.BreakEvent internal, int amount) {
        
        internal.setExpToDrop(amount);
    }
    
}
