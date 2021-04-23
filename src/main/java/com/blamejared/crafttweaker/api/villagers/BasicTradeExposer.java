package com.blamejared.crafttweaker.api.villagers;

import com.blamejared.crafttweaker.api.util.StringUtils;
import net.minecraft.entity.merchant.villager.VillagerTrades;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.BasicTrade;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Field;
import java.util.function.Function;

/**
 * Class holding helper methods to expose fields in {@link net.minecraftforge.common.BasicTrade}
 */
public class BasicTradeExposer {
    
    private static final Function<String, MethodHandle> MAKE_HANDLE = s -> {
        
        try {
            final MethodHandles.Lookup lookup = MethodHandles.lookup();
            final Class<?> forgeInternalHandlerClass = BasicTrade.class;
            final Field field = forgeInternalHandlerClass.getDeclaredField(s);
            field.setAccessible(true);
            
            return lookup.unreflectGetter(field);
        } catch(ReflectiveOperationException e) {
            throw new RuntimeException("Unable to reflect into Basic Trade to get: " + StringUtils.quoteAndEscape(s));
        }
    };
    
    private static final MethodHandle PRICE_GETTER = MAKE_HANDLE.apply("price");
    private static final MethodHandle PRICE2_GETTER = MAKE_HANDLE.apply("price2");
    private static final MethodHandle FOR_SALE_GETTER = MAKE_HANDLE.apply("forSale");
    private static final MethodHandle MAX_TRADES_GETTER = MAKE_HANDLE.apply("maxTrades");
    private static final MethodHandle XP_GETTER = MAKE_HANDLE.apply("xp");
    private static final MethodHandle PRICE_MULT_GETTER = MAKE_HANDLE.apply("priceMult");
    
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
            try {
                return function.x((BasicTrade) trade);
            } catch(Throwable throwable) {
                throw new RuntimeException(throwable);
            }
        }
        throw new IllegalArgumentException(trade.getClass() + " is not of type BasicTrade!");
    }
    
    private interface TradeFunction<T> {
        T x(final BasicTrade trade) throws Throwable;
    }
}
