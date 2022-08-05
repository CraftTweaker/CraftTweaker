package com.blamejared.crafttweaker.api.villager;

import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.trading.MerchantOffer;
import javax.annotation.Nullable;

import java.util.Random;

public class BasicTradeListing implements VillagerTrades.ItemListing, IBasicItemListing {
    
    protected final ItemStack price;
    protected final ItemStack price2;
    protected final ItemStack forSale;
    protected final int maxTrades;
    protected final int xp;
    protected final float priceMult;
    
    public BasicTradeListing(ItemStack price, ItemStack price2, ItemStack forSale, int maxTrades, int xp, float priceMult) {
        
        this.price = price;
        this.price2 = price2;
        this.forSale = forSale;
        this.maxTrades = maxTrades;
        this.xp = xp;
        this.priceMult = priceMult;
    }
    
    public BasicTradeListing(ItemStack price, ItemStack forSale, int maxTrades, int xp, float priceMult) {
        
        this(price, ItemStack.EMPTY, forSale, maxTrades, xp, priceMult);
    }
    
    public BasicTradeListing(int emeralds, ItemStack forSale, int maxTrades, int xp, float mult) {
        
        this(new ItemStack(Items.EMERALD, emeralds), forSale, maxTrades, xp, mult);
    }
    
    public BasicTradeListing(int emeralds, ItemStack forSale, int maxTrades, int xp) {
        
        this(new ItemStack(Items.EMERALD, emeralds), forSale, maxTrades, xp, 1);
    }
    
    @Nullable
    @Override
    public MerchantOffer getOffer(Entity trader, RandomSource rand) {
        
        return new MerchantOffer(price, price2, forSale, maxTrades, xp, priceMult);
    }
    
    public ItemStack getPrice() {
        
        return price;
    }
    
    public ItemStack getPrice2() {
        
        return price2;
    }
    
    public ItemStack getForSale() {
        
        return forSale;
    }
    
    public int getMaxTrades() {
        
        return maxTrades;
    }
    
    public int getXp() {
        
        return xp;
    }
    
    public float getPriceMult() {
        
        return priceMult;
    }
    
}
