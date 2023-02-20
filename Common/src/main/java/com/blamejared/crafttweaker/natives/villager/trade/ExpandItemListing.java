package com.blamejared.crafttweaker.natives.villager.trade;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.item.trading.MerchantOffer;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("vanilla/api/villager/trade/ItemListing")
@NativeTypeRegistration(value = VillagerTrades.ItemListing.class, zenCodeName = "crafttweaker.api.villager.trade.ItemListing")
public class ExpandItemListing {
    
    @ZenCodeType.Method
    public static MerchantOffer getOffer(VillagerTrades.ItemListing internal, Entity traderEntity, RandomSource random) {
        
        return internal.getOffer(traderEntity, random);
    }
    
}
