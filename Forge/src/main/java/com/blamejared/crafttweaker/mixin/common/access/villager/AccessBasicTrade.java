package com.blamejared.crafttweaker.mixin.common.access.villager;

import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.BasicTrade;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value = BasicTrade.class, remap = false)
public interface AccessBasicTrade {
    
    @Accessor
    ItemStack getPrice();
    
    @Accessor
    ItemStack getPrice2();
    
    @Accessor
    ItemStack getForSale();
    
    @Accessor
    int getMaxTrades();
    
    @Accessor
    int getXp();
    
    @Accessor
    float getPriceMult();
    
}
