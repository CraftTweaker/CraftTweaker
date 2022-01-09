package com.blamejared.crafttweaker.natives.event.entity.player;


import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.platform.Services;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.world.InteractionHand;
import net.minecraftforge.event.entity.player.PlayerDestroyItemEvent;
import org.openzen.zencode.java.ZenCodeType;

/**
 * This event is fired when a player destroys a tool or item. This happens
 * after the tool has been destroyed and can not be used to prevent the
 * destruction directly.
 *
 * @docParam this event
 */
@ZenRegister
@Document("forge/api/event/entity/player/PlayerDestroyItemEvent")
@NativeTypeRegistration(value = PlayerDestroyItemEvent.class, zenCodeName = "crafttweaker.api.event.entity.player.PlayerDestroyItemEvent")
public class ExpandPlayerDestroyItemEvent {
    
    /**
     * Gets a snapshot of the item from before it broke. Modifying this item
     * will have no effect and it should be treated as unmodifiable.
     *
     * @return The original item from before it was broken.
     */
    @ZenCodeType.Method
    @ZenCodeType.Getter("original")
    public static IItemStack getOriginal(PlayerDestroyItemEvent internal) {
        
        return Services.PLATFORM.createMCItemStack(internal.getOriginal());
    }
    
    @ZenCodeType.Nullable
    @ZenCodeType.Method
    @ZenCodeType.Getter("hand")
    public static InteractionHand getHand(PlayerDestroyItemEvent internal) {
        
        return internal.getHand();
    }
    
}