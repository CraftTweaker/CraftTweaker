package com.blamejared.crafttweaker.api.villagers;

import com.blamejared.crafttweaker.api.util.MethodHandleHelper;
import net.minecraft.entity.merchant.villager.VillagerTrades;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.BasicTrade;

import java.lang.invoke.MethodHandle;

/**
 * Class holding helper methods to expose fields in {@link net.minecraftforge.common.BasicTrade}
 */
public class BasicTradeExposer {
    private static final MethodHandle PRICE_GETTER = MethodHandleHelper.linkGetter(BasicTrade.class, "price");
    private static final MethodHandle PRICE2_GETTER = MethodHandleHelper.linkGetter(BasicTrade.class, "price2");
    private static final MethodHandle FOR_SALE_GETTER = MethodHandleHelper.linkGetter(BasicTrade.class, "forSale");
    private static final MethodHandle MAX_TRADES_GETTER = MethodHandleHelper.linkGetter(BasicTrade.class, "maxTrades");
    private static final MethodHandle XP_GETTER = MethodHandleHelper.linkGetter(BasicTrade.class, "xp");
    private static final MethodHandle PRICE_MULT_GETTER = MethodHandleHelper.linkGetter(BasicTrade.class, "priceMult");
    
    public static ItemStack getPrice(VillagerTrades.ITrade trade) {
        return invoke(trade, it -> (ItemStack) PRICE_GETTER.invokeExact(it));
    }
    
    public static ItemStack getPrice2(VillagerTrades.ITrade trade) {
        return invoke(trade, it -> (ItemStack) PRICE2_GETTER.invokeExact(it));
    }
    
    public static ItemStack getForSale(VillagerTrades.ITrade trade) {
        return invoke(trade, it -> (ItemStack) FOR_SALE_GETTER.invokeExact(it));
    }
    
    public static int getMaxTrades(VillagerTrades.ITrade trade) {
        return invoke(trade, it -> (int) MAX_TRADES_GETTER.invokeExact(it));
    }
    
    public static int getXP(VillagerTrades.ITrade trade) {
        return invoke(trade, it -> (int) XP_GETTER.invokeExact(it));
    }
    
    public static float getPriceMult(VillagerTrades.ITrade trade) {
        return invoke(trade, it -> (float) PRICE_MULT_GETTER.invokeExact(it));
    }
    
    private static <T> T invoke(final VillagerTrades.ITrade trade, final TradeFunction<T> function) {
        if(trade instanceof BasicTrade) {
            return MethodHandleHelper.invoke(() -> function.x((BasicTrade) trade));
        }
        throw new IllegalArgumentException(trade.getClass() + " is not of type BasicTrade!");
    }
    
    private interface TradeFunction<T> {
        T x(final BasicTrade trade) throws Throwable;
    }
}
