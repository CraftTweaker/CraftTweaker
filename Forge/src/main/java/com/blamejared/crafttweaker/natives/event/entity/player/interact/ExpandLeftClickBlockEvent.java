package com.blamejared.crafttweaker.natives.event.entity.player.interact;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.Event;
import org.openzen.zencode.java.ZenCodeType;

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
@Document("forge/api/event/entity/player/interact/LeftClickBlockEvent")
@NativeTypeRegistration(value = PlayerInteractEvent.LeftClickBlock.class, zenCodeName = "crafttweaker.api.event.entity.player.interact.LeftClickBlockEvent")
public class ExpandLeftClickBlockEvent {
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("useBlock")
    public static Event.Result getUseBlock(PlayerInteractEvent.LeftClickBlock internal) {
        
        return internal.getUseBlock();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("useItem")
    public static Event.Result getUseItem(PlayerInteractEvent.LeftClickBlock internal) {
        
        return internal.getUseItem();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Setter("useBlock")
    public static void setUseBlock(PlayerInteractEvent.LeftClickBlock internal, Event.Result triggerBlock) {
        
        internal.setUseBlock(triggerBlock);
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Setter("useItem")
    public static void setUseItem(PlayerInteractEvent.LeftClickBlock internal, Event.Result triggerItem) {
        
        internal.setUseItem(triggerItem);
    }
    
}
