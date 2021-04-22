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
    private static final MethodHandle PRICE_MULt_GETTER = MAKE_HANDLE.apply("priceMult");
    
    
    public static ItemStack getPrice(VillagerTrades.ITrade trade) {
        
        return (ItemStack) invokeExact(PRICE_GETTER, trade);
    }
    
    public static ItemStack getPrice2(VillagerTrades.ITrade trade) {
        
        
        return (ItemStack) invokeExact(PRICE2_GETTER, trade);
    }
    
    public static ItemStack getForSale(VillagerTrades.ITrade trade) {
        
        return (ItemStack) invokeExact(FOR_SALE_GETTER, trade);
    }
    
    public static int getMaxTrades(VillagerTrades.ITrade trade) {
        
        return (int) invokeExact(MAX_TRADES_GETTER, trade);
    }
    
    public static int getXP(VillagerTrades.ITrade trade) {
        
        return (int) invokeExact(XP_GETTER, trade);
    }
    
    public static float getPriceMult(VillagerTrades.ITrade trade) {
        
        return (float) invokeExact(PRICE_MULt_GETTER, trade);
    }
    
    
    private static Object invokeExact(MethodHandle handle, VillagerTrades.ITrade trade) {
        
        if(trade instanceof BasicTrade) {
            try {
                return handle.invoke((BasicTrade)trade);
            } catch(Throwable throwable) {
                throw new RuntimeException(throwable);
            }
        }
        throw new IllegalArgumentException(trade.getClass() + " is not of type BasicTrade!");
    }
    
}
