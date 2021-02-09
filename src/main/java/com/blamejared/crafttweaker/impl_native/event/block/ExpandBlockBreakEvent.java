package com.blamejared.crafttweaker.impl_native.event.block;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.event.world.BlockEvent;
import org.openzen.zencode.java.ZenCodeType;

/**
 * @author youyihj
 */
@ZenRegister
@Document("vanilla/api/event/block/MCBlockBreakEvent")
@NativeTypeRegistration(value = BlockEvent.BreakEvent.class, zenCodeName = "crafttweaker.api.event.block.MCBlockBreakEvent")
public class ExpandBlockBreakEvent {
    
    /**
     * Gets player who broke the block. If no player is available, use a fake player
     */
    @ZenCodeType.Getter("player")
    @ZenCodeType.Method
    public static PlayerEntity getPlayer(BlockEvent.BreakEvent internal) {
        return internal.getPlayer();
    }
    
    @ZenCodeType.Getter("expToDrop")
    @ZenCodeType.Method
    public static int getExpToDrop(BlockEvent.BreakEvent internal) {
        return internal.getExpToDrop();
    }
    
    @ZenCodeType.Setter("expToDrop")
    @ZenCodeType.Method
    public static void setExpToDrop(BlockEvent.BreakEvent internal, int amount) {
        internal.setExpToDrop(amount);
    }
}
