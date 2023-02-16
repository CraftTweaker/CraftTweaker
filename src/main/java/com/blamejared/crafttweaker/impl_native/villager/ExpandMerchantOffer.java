package com.blamejared.crafttweaker.impl_native.villager;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.api.data.IData;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.impl.data.MapData;
import com.blamejared.crafttweaker.impl.item.MCItemStack;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeConstructor;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.item.ItemStack;
import net.minecraft.item.MerchantOffer;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("vanilla/api/villager/MCMerchantOffer")
@NativeTypeRegistration(value = MerchantOffer.class, zenCodeName = "crafttweaker.api.villager.MCMerchantOffer",
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
    @ZenCodeType.Getter("buyingStackFirst")
    public static IItemStack getBuyingStackFirst(MerchantOffer internal) {
        
        return new MCItemStack(internal.getBuyingStackFirst());
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("discountedBuyingStackFirst")
    public static IItemStack getDiscountedBuyingStackFirst(MerchantOffer internal) {
        
        return new MCItemStack(internal.getDiscountedBuyingStackFirst());
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("buyingStackSecond")
    public static IItemStack getBuyingStackSecond(MerchantOffer internal) {
        
        return new MCItemStack(internal.getBuyingStackSecond());
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("sellingStack")
    public static IItemStack getSellingStack(MerchantOffer internal) {
        
        return new MCItemStack(internal.getSellingStack());
    }
    
    @ZenCodeType.Method
    public static void calculateDemand(MerchantOffer internal) {
        
        internal.calculateDemand();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("copyOfSellingStack")
    public static IItemStack getCopyOfSellingStack(MerchantOffer internal) {
        
        return new MCItemStack(internal.getCopyOfSellingStack());
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
    public static void increaseSpecialPrice(MerchantOffer internal, int specialPrice) {
        
        internal.increaseSpecialPrice(specialPrice);
    }
    
    @ZenCodeType.Method
    public static void resetSpecialPrice(MerchantOffer internal) {
        
        internal.resetSpecialPrice();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("specialPrice")
    public static int getSpecialPrice(MerchantOffer internal) {
        
        return internal.getSpecialPrice();
    }
    
    @ZenCodeType.Method
    public static void setSpecialPrice(MerchantOffer internal, int specialPrice) {
        
        internal.setSpecialPrice(specialPrice);
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("priceMultiplier")
    public static float getPriceMultiplier(MerchantOffer internal) {
        
        return internal.getPriceMultiplier();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("givenExp")
    public static int getGivenExp(MerchantOffer internal) {
        
        return internal.getGivenExp();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("hasNoUsesLeft")
    public static boolean hasNoUsesLeft(MerchantOffer internal) {
        
        return internal.hasNoUsesLeft();
    }
    
    @ZenCodeType.Method
    public static void makeUnavailable(MerchantOffer internal) {
        
        internal.makeUnavailable();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("hasBeenUsed")
    public static boolean hasBeenUsed(MerchantOffer internal) {
        
        return internal.hasBeenUsed();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("doesRewardExp")
    public static boolean getDoesRewardExp(MerchantOffer internal) {
        
        return internal.getDoesRewardExp();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("buyingStackFirst")
    public static IData write(MerchantOffer internal) {
        
        return new MapData(internal.write());
    }
    
    @ZenCodeType.Method
    public static boolean matches(MerchantOffer internal, IItemStack a, IItemStack b) {
        
        return internal.matches(a.getInternal(), b.getInternal());
    }
    
    @ZenCodeType.Method
    public static boolean doTransaction(MerchantOffer internal, IItemStack a, IItemStack b) {
        
        return internal.doTransaction(a.getInternal(), b.getInternal());
    }
    
}
