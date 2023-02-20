//TODO move this to the com.blamejared.crafttweaker.api.villager.trade.type package next breaking change
package com.blamejared.crafttweaker.api.villager;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.trading.MerchantOffer;
import org.openzen.zencode.java.ZenCodeType;

import javax.annotation.Nullable;

@ZenRegister
@ZenCodeType.Name("crafttweaker.api.villager.trade.type.BasicTradeListing")
@Document("vanilla/api/villager/trade/type/BasicTradeListing")
public class BasicTradeListing implements VillagerTrades.ItemListing, IBasicItemListing {
    
    protected final ItemStack price;
    protected final ItemStack price2;
    protected final ItemStack forSale;
    protected final int maxTrades;
    protected final int xp;
    protected final float priceMult;
    
    @ZenCodeType.Constructor
    public BasicTradeListing(ItemStack price, ItemStack price2, ItemStack forSale, int maxTrades, int xp, float priceMult) {
        
        this.price = price;
        this.price2 = price2;
        this.forSale = forSale;
        this.maxTrades = maxTrades;
        this.xp = xp;
        this.priceMult = priceMult;
    }
    
    @ZenCodeType.Constructor
    public BasicTradeListing(ItemStack price, ItemStack forSale, int maxTrades, int xp, float priceMult) {
        
        this(price, ItemStack.EMPTY, forSale, maxTrades, xp, priceMult);
    }
    
    @ZenCodeType.Constructor
    public BasicTradeListing(int emeralds, ItemStack forSale, int maxTrades, int xp, float mult) {
        
        this(new ItemStack(Items.EMERALD, emeralds), forSale, maxTrades, xp, mult);
    }
    
    @ZenCodeType.Constructor
    public BasicTradeListing(int emeralds, ItemStack forSale, int maxTrades, int xp) {
        
        this(new ItemStack(Items.EMERALD, emeralds), forSale, maxTrades, xp, 1);
    }
    
    @Nullable
    @Override
    public MerchantOffer getOffer(Entity trader, RandomSource rand) {
        
        return new MerchantOffer(price, price2, forSale, maxTrades, xp, priceMult);
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("price")
    public ItemStack getPrice() {
        
        return price;
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("price2")
    public ItemStack getPrice2() {
        
        return price2;
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("forSale")
    public ItemStack getForSale() {
        
        return forSale;
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("maxTrades")
    public int getMaxTrades() {
        
        return maxTrades;
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("xp")
    public int getXp() {
        
        return xp;
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("priceMult")
    public float getPriceMult() {
        
        return priceMult;
    }
    
}
