package com.blamejared.crafttweaker.natives.event.entity.player;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.world.Container;
import net.minecraftforge.event.entity.player.PlayerEvent;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("forge/api/event/entity/player/ItemCraftedEvent")
@NativeTypeRegistration(value = PlayerEvent.ItemCraftedEvent.class, zenCodeName = "crafttweaker.api.event.player.ItemCraftedEvent")
public class ExpandItemCraftedEvent {
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("crafting")
    public static IItemStack getCrafting(PlayerEvent.ItemCraftedEvent internal) {
        
        return IItemStack.of(internal.getCrafting());
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("inventory")
    public static Container getCraftingMatrix(PlayerEvent.ItemCraftedEvent internal) {
        
        return internal.getInventory();
    }
    
}
