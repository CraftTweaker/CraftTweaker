package com.blamejared.crafttweaker.natives.event.entity.player;


import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.platform.Services;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraftforge.event.entity.player.PlayerEvent;
import org.openzen.zencode.java.ZenCodeType;


@ZenRegister
@Document("vanilla/api/event/entity/player/ItemSmeltedEvent")
@NativeTypeRegistration(value = PlayerEvent.ItemSmeltedEvent.class, zenCodeName = "crafttweaker.api.event.entity.player.ItemSmeltedEvent")
public class ExpandItemSmeltedEvent {
    
    /**
     * Gets the smelted item (the output stack)
     */
    @ZenCodeType.Method
    @ZenCodeType.Getter("smelted")
    public static IItemStack getSmelted(PlayerEvent.ItemSmeltedEvent internal) {
        
        return Services.PLATFORM.createMCItemStack(internal.getSmelting());
    }
    
}
