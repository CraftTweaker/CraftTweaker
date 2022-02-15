package com.blamejared.crafttweaker.impl_native.event.entity.player;


import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.brewing.PlayerBrewedPotionEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import org.openzen.zencode.java.ZenCodeType;


/**
 * This event is called when a player picks up a potion from a brewing stand.
 * Since it is a {@link PlayerEvent}, you can already access the getter
 */
@ZenRegister
@Document("vanilla/api/event/entity/player/MCPlayerBrewedPotionEvent")
@NativeTypeRegistration(value = PlayerBrewedPotionEvent.class, zenCodeName = "crafttweaker.api.event.entity.player.MCPlayerBrewedPotionEvent")
public class ExpandPLayerBrewedPotionEvent {
    
    /**
     * Gets the stack that was just brewed.
     *
     * @return The stack that was just brewed.
     */
    @ZenCodeType.Method
    @ZenCodeType.Getter("stack")
    public static ItemStack getStack(PlayerBrewedPotionEvent internal) {
        
        return internal.getStack();
    }
    
}
