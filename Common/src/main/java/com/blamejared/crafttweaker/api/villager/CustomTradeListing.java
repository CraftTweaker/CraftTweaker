package com.blamejared.crafttweaker.api.villager;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.item.trading.MerchantOffer;

import javax.annotation.Nullable;
import java.util.Random;
import java.util.function.BiFunction;

public class CustomTradeListing implements VillagerTrades.ItemListing {
    
    private final BiFunction<Entity, Random, MerchantOffer> offerGenerator;
    
    public CustomTradeListing(BiFunction<Entity, Random, MerchantOffer> offerGenerator) {
        
        this.offerGenerator = offerGenerator;
    }
    
    @Nullable
    @Override
    public MerchantOffer getOffer(Entity entity, Random random) {
        
        return offerGenerator.apply(entity, random);
    }
    
}
