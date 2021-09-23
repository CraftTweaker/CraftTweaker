package com.blamejared.crafttweaker.impl_native.event.entity.player.interact;


import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.impl.item.MCItemStack;
import com.blamejared.crafttweaker.impl.util.MCDirection;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import org.openzen.zencode.java.ZenCodeType;

import java.util.Optional;


/**
 * This event is the superclass of all other PlayerInteract events.
 * Generally, you want to use the subtypes of this event.
 */
@ZenRegister
@Document("vanilla/api/event/entity/player/interact/MCPlayerInteractEvent")
@NativeTypeRegistration(value = PlayerInteractEvent.class, zenCodeName = "crafttweaker.api.event.entity.player.interact.MCPlayerInteractEvent")
public class ExpandPlayerInteractEvent {
    
    /**
     * If the interaction was on an entity, will be a BlockPos centered on the entity.
     * If the interaction was on a block, will be the position of that block.
     * Otherwise, will be a BlockPos centered on the player.
     */
    @ZenCodeType.Method
    @ZenCodeType.Getter("blockPos")
    public static BlockPos getBlockPos(PlayerInteractEvent internal) {
        
        return internal.getPos();
    }
    
    /**
     * The stack involved in this interaction.
     * May be empty, but will never be null.
     */
    @ZenCodeType.Method
    @ZenCodeType.Getter("itemStack")
    public static IItemStack getItemStack(PlayerInteractEvent internal) {
        
        return new MCItemStack(internal.getItemStack());
    }
    
    /**
     * The face involved in this interaction.
     * For all non-block interactions, this will return null
     */
    @ZenCodeType.Method
    @ZenCodeType.Nullable
    @ZenCodeType.Getter("face")
    public static MCDirection getFace(PlayerInteractEvent internal) {
        
        return Optional.ofNullable(internal.getFace()).map(MCDirection::get).orElse(null);
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("hand")
    public static Hand getHand(PlayerInteractEvent internal) {
        
        return internal.getHand();
    }
    
    /**
     * @return The EnumActionResult that will be returned to vanilla if the event is cancelled, instead of calling the relevant
     * method of the event. By default, this is PASS, meaning cancelled events will cause
     * the client to keep trying more interactions until something works.
     */
    @ZenCodeType.Method
    @ZenCodeType.Getter("cancellationResult")
    public static ActionResultType getCancellationResult(PlayerInteractEvent internal) {
        
        return internal.getCancellationResult();
    }
    
    /**
     * Set the EnumActionResult that will be returned to vanilla if the event is cancelled, instead of calling the relevant method of the event.
     *
     * Note that this only has an effect on RightClickBlockEvent, RightClickItemEvent, EntityInteractEvent.
     */
    @ZenCodeType.Method
    @ZenCodeType.Setter("cancellationResult")
    public static void setCancellationResult(PlayerInteractEvent internal, ActionResultType result) {
        
        internal.setCancellationResult(result);
    }
    
}
