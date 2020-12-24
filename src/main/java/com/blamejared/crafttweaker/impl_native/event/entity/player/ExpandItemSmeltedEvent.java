package com.blamejared.crafttweaker.impl_native.event.entity.player;


import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.impl.item.MCItemStack;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraftforge.event.entity.player.PlayerEvent;
import org.openzen.zencode.java.ZenCodeType;


@ZenRegister
@Document("vanilla/api/event/entity/player/MCItemSmeltedEvent")
@NativeTypeRegistration(value = PlayerEvent.ItemSmeltedEvent.class, zenCodeName = "crafttweaker.api.event.entity.player.MCItemSmeltedEvent")
public class ExpandItemSmeltedEvent {
    
    /**
     * Gets the smelted item (the output stack)
     */
    @ZenCodeType.Method
    @ZenCodeType.Getter("smelted")
    public static IItemStack getSmelted(PlayerEvent.ItemSmeltedEvent internal) {
        return new MCItemStack(internal.getSmelting());
    }
}
