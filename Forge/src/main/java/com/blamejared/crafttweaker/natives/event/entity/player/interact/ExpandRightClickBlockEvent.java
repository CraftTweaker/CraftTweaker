package com.blamejared.crafttweaker.natives.event.entity.player.interact;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.core.Direction;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.Event;
import org.openzen.zencode.java.ZenCodeType;

/**
 * This event is fired on both sides whenever the player right clicks while targeting a block.
 * This event controls which of Item.onItemUseFirst, Block.onBlockActivated, and Item.onItemUse will be called.
 *
 * @docEvent canceled will cause none of the above three to be called.
 */
@ZenRegister
@Document("vanilla/api/event/entity/player/interact/RightClickBlockEvent")
@NativeTypeRegistration(value = PlayerInteractEvent.RightClickBlock.class, zenCodeName = "crafttweaker.api.event.entity.player.interact.RightClickBlockEvent")
public class ExpandRightClickBlockEvent {
    
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("useBlock")
    public static Event.Result getUseBlock(PlayerInteractEvent.RightClickBlock internal) {
        
        return internal.getUseBlock();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("useItem")
    public static Event.Result getUseItem(PlayerInteractEvent.RightClickBlock internal) {
        
        return internal.getUseItem();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("hitVec")
    public static BlockHitResult getHitVec(PlayerInteractEvent.RightClickBlock internal) {
        
        return internal.getHitVec();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Setter("useBlock")
    public static void setUseBlock(PlayerInteractEvent.RightClickBlock internal, Event.Result triggerBlock) {
        
        internal.setUseBlock(triggerBlock);
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Setter("useItem")
    public static void setUseItem(PlayerInteractEvent.RightClickBlock internal, Event.Result triggerItem) {
        
        internal.setUseItem(triggerItem);
    }
    
    /**
     * The face of the block that was clicked
     * Unlike in {@link PlayerInteractEvent} this is known not to be null
     */
    @ZenCodeType.Method
    @ZenCodeType.Getter("face")
    public static Direction getFace(PlayerInteractEvent internal) {
        
        return internal.getFace();
    }
    
}
