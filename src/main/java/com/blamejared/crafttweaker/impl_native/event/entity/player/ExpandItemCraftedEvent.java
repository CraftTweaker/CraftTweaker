package com.blamejared.crafttweaker.impl_native.event.entity.player;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.impl.item.MCItemStack;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.inventory.IInventory;
import net.minecraftforge.event.entity.player.PlayerEvent;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("vanilla/api/event/entity/player/MCItemCraftedEvent")
@NativeTypeRegistration(value = PlayerEvent.ItemCraftedEvent.class, zenCodeName = "crafttweaker.api.event.player.MCItemCraftedEvent")
public class ExpandItemCraftedEvent {
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("crafting")
    public static IItemStack getCrafting(PlayerEvent.ItemCraftedEvent internal) {
        return new MCItemStack(internal.getCrafting());
    }

    @ZenCodeType.Method
    @ZenCodeType.Getter("craftMatrix")
    public static IInventory getCraftingMatrix(PlayerEvent.ItemCraftedEvent internal) {
        return internal.getInventory();
    }
}
