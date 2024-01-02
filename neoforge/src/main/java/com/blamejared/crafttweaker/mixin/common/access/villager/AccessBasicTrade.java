package com.blamejared.crafttweaker.mixin.common.access.villager;

import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.common.BasicItemListing;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value = BasicItemListing.class, remap = false)
public interface AccessBasicTrade {
    
    @Accessor("price")
    ItemStack crafttweaker$getPrice();
    
    @Accessor("price2")
    ItemStack crafttweaker$getPrice2();
    
    @Accessor("forSale")
    ItemStack crafttweaker$getForSale();
    
    @Accessor("maxTrades")
    int crafttweaker$getMaxTrades();
    
    @Accessor("xp")
    int crafttweaker$getXp();
    
    @Accessor("priceMult")
    float crafttweaker$getPriceMult();
    
}
