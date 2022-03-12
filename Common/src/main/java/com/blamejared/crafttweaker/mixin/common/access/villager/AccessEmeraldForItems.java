package com.blamejared.crafttweaker.mixin.common.access.villager;

import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.item.Item;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(VillagerTrades.EmeraldForItems.class)
public interface AccessEmeraldForItems {
    
    @Accessor("item")
    Item crafttweaker$getItem();
    
}
