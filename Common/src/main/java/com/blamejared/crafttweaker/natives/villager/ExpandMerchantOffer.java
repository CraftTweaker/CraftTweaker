package com.blamejared.crafttweaker.natives.villager;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.data.IData;
import com.blamejared.crafttweaker.api.data.converter.tag.TagToDataConverter;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.platform.Services;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeConstructor;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.trading.MerchantOffer;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("vanilla/api/villager/MerchantOffer")
@NativeTypeRegistration(value = MerchantOffer.class, zenCodeName = "crafttweaker.api.villager.MerchantOffer",
        constructors = {
                @NativeConstructor({
                        @NativeConstructor.ConstructorParameter(type = ItemStack.class, name = "baseCostA"),
                        @NativeConstructor.ConstructorParameter(type = ItemStack.class, name = "result"),
                        @NativeConstructor.ConstructorParameter(type = int.class, name = "maxUses"),
                        @NativeConstructor.ConstructorParameter(type = int.class, name = "xp"),
                        @NativeConstructor.ConstructorParameter(type = float.class, name = "priceMultiplier"),
                }),
                @NativeConstructor({
                        @NativeConstructor.ConstructorParameter(type = ItemStack.class, name = "baseCostA"),
                        @NativeConstructor.ConstructorParameter(type = ItemStack.class, name = "costB"),
                        @NativeConstructor.ConstructorParameter(type = ItemStack.class, name = "result"),
                        @NativeConstructor.ConstructorParameter(type = int.class, name = "maxUses"),
                        @NativeConstructor.ConstructorParameter(type = int.class, name = "xp"),
                        @NativeConstructor.ConstructorParameter(type = float.class, name = "priceMultiplier"),
                }),
                @NativeConstructor({
                        @NativeConstructor.ConstructorParameter(type = ItemStack.class, name = "baseCostA"),
                        @NativeConstructor.ConstructorParameter(type = ItemStack.class, name = "costB"),
                        @NativeConstructor.ConstructorParameter(type = ItemStack.class, name = "result"),
                        @NativeConstructor.ConstructorParameter(type = int.class, name = "uses"),
                        @NativeConstructor.ConstructorParameter(type = int.class, name = "maxUses"),
                        @NativeConstructor.ConstructorParameter(type = int.class, name = "xp"),
                        @NativeConstructor.ConstructorParameter(type = float.class, name = "priceMultiplier"),
                }),
                @NativeConstructor({
                        @NativeConstructor.ConstructorParameter(type = ItemStack.class, name = "baseCostA"),
                        @NativeConstructor.ConstructorParameter(type = ItemStack.class, name = "costB"),
                        @NativeConstructor.ConstructorParameter(type = ItemStack.class, name = "result"),
                        @NativeConstructor.ConstructorParameter(type = int.class, name = "uses"),
                        @NativeConstructor.ConstructorParameter(type = int.class, name = "maxUses"),
                        @NativeConstructor.ConstructorParameter(type = int.class, name = "xp"),
                        @NativeConstructor.ConstructorParameter(type = float.class, name = "priceMultiplier"),
                        @NativeConstructor.ConstructorParameter(type = int.class, name = "demand"),
                })
        })
public class ExpandMerchantOffer {
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("baseCostA")
    public static IItemStack getBaseCostA(MerchantOffer internal) {
        
        return Services.PLATFORM.createMCItemStack(internal.getBaseCostA());
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("costA")
    public static IItemStack getCostA(MerchantOffer internal) {
        
        return Services.PLATFORM.createMCItemStack(internal.getCostA());
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("costB")
    public static IItemStack getCostB(MerchantOffer internal) {
        
        return Services.PLATFORM.createMCItemStack(internal.getCostB());
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("result")
    public static IItemStack getResult(MerchantOffer internal) {
        
        return Services.PLATFORM.createMCItemStack(internal.getResult());
    }
    
    @ZenCodeType.Method
    public static void updateDemand(MerchantOffer internal) {
        
        internal.updateDemand();
    }
    
    @ZenCodeType.Method
    public static IItemStack assemble(MerchantOffer internal) {
        
        return Services.PLATFORM.createMCItemStack(internal.assemble());
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("uses")
    public static int getUses(MerchantOffer internal) {
        
        return internal.getUses();
    }
    
    @ZenCodeType.Method
    public static void resetUses(MerchantOffer internal) {
        
        internal.resetUses();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("maxUses")
    public static int getMaxUses(MerchantOffer internal) {
        
        return internal.getMaxUses();
    }
    
    @ZenCodeType.Method
    public static void increaseUses(MerchantOffer internal) {
        
        internal.increaseUses();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("demand")
    public static int getDemand(MerchantOffer internal) {
        
        return internal.getDemand();
    }
    
    @ZenCodeType.Method
    public static void addToSpecialPriceDiff(MerchantOffer internal, int specialPriceDiff) {
        
        internal.addToSpecialPriceDiff(specialPriceDiff);
    }
    
    @ZenCodeType.Method
    public static void resetSpecialPriceDiff(MerchantOffer internal) {
        
        internal.resetSpecialPriceDiff();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("specialPriceDiff")
    public static int getSpecialPriceDiff(MerchantOffer internal) {
        
        return internal.getSpecialPriceDiff();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Setter("specialPriceDiff")
    public static void setSpecialPriceDiff(MerchantOffer internal, int specialPriceDiff) {
        
        internal.setSpecialPriceDiff(specialPriceDiff);
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("priceMultiplier")
    public static float getPriceMultiplier(MerchantOffer internal) {
        
        return internal.getPriceMultiplier();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("xp")
    public static int getXp(MerchantOffer internal) {
        
        return internal.getXp();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("outOfStock")
    public static boolean isOutOfStock(MerchantOffer internal) {
        
        return internal.isOutOfStock();
    }
    
    @ZenCodeType.Method
    public static void setToOutOfStock(MerchantOffer internal) {
        
        internal.setToOutOfStock();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("needsRestock")
    public static boolean needsRestock(MerchantOffer internal) {
        
        return internal.needsRestock();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("shouldRewardExp")
    public static boolean shouldRewardExp(MerchantOffer internal) {
        
        return internal.shouldRewardExp();
    }
    
    @ZenCodeType.Method
    public static IData createTag(MerchantOffer internal) {
        
        return TagToDataConverter.convert(internal.createTag());
    }
    
    @ZenCodeType.Method
    public static boolean satisfiedBy(MerchantOffer internal, IItemStack a, IItemStack b) {
        
        return internal.satisfiedBy(a.getInternal(), b.getInternal());
    }
    
    @ZenCodeType.Method
    public static boolean take(MerchantOffer internal, IItemStack a, IItemStack b) {
        
        return internal.take(a.getInternal(), b.getInternal());
    }
    
}
