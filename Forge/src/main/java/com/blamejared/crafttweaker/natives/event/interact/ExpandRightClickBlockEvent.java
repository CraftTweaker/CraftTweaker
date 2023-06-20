package com.blamejared.crafttweaker.natives.event.interact;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.event.ForgeEventCancellationCarrier;
import com.blamejared.crafttweaker.api.event.ZenEvent;
import com.blamejared.crafttweaker.api.event.bus.ForgeEventBusWire;
import com.blamejared.crafttweaker.api.event.bus.IEventBus;
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
@ZenEvent
@Document("forge/api/event/interact/RightClickBlockEvent")
@NativeTypeRegistration(value = PlayerInteractEvent.RightClickBlock.class, zenCodeName = "crafttweaker.api.event.interact.RightClickBlockEvent")
public class ExpandRightClickBlockEvent {
    
    @ZenEvent.Bus
    public static final IEventBus<PlayerInteractEvent.RightClickBlock> BUS = IEventBus.cancelable(
            PlayerInteractEvent.RightClickBlock.class,
            ForgeEventBusWire.of(),
            ForgeEventCancellationCarrier.of()
    );
    
    
    @ZenCodeType.Getter("useBlock")
    public static Event.Result getUseBlock(PlayerInteractEvent.RightClickBlock internal) {
        
        return internal.getUseBlock();
    }
    
    @ZenCodeType.Getter("useItem")
    public static Event.Result getUseItem(PlayerInteractEvent.RightClickBlock internal) {
        
        return internal.getUseItem();
    }
    
    @ZenCodeType.Getter("hitVec")
    public static BlockHitResult getHitVec(PlayerInteractEvent.RightClickBlock internal) {
        
        return internal.getHitVec();
    }
    
    @ZenCodeType.Setter("useBlock")
    public static void setUseBlock(PlayerInteractEvent.RightClickBlock internal, Event.Result triggerBlock) {
        
        internal.setUseBlock(triggerBlock);
    }
    
    @ZenCodeType.Setter("useItem")
    public static void setUseItem(PlayerInteractEvent.RightClickBlock internal, Event.Result triggerItem) {
        
        internal.setUseItem(triggerItem);
    }
    
    /**
     * The face of the block that was clicked
     * Unlike in {@link PlayerInteractEvent} this is known not to be null
     */
    @ZenCodeType.Getter("face")
    public static Direction getFace(PlayerInteractEvent internal) {
        
        return internal.getFace();
    }
    
}
