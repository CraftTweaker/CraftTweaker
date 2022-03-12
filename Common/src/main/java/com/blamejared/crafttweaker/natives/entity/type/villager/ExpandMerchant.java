package com.blamejared.crafttweaker.natives.entity.type.villager;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.trading.Merchant;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.item.trading.MerchantOffers;
import org.jetbrains.annotations.Nullable;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("vanilla/api/entity/type/villager/Merchant")
@NativeTypeRegistration(value = Merchant.class, zenCodeName = "crafttweaker.api.entity.type.villager.Merchant")
public class ExpandMerchant {
    
    @ZenCodeType.Method
    @ZenCodeType.Setter("tradingPlayer")
    public static void setTradingPlayer(Merchant internal, @ZenCodeType.Nullable Player player) {
        
        internal.setTradingPlayer(player);
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("tradingPlayer")
    @ZenCodeType.Nullable
    public static Player getTradingPlayer(Merchant internal) {
        
        return internal.getTradingPlayer();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("offers")
    public static MerchantOffers getOffers(Merchant internal) {
        
        return internal.getOffers();
    }
    
    @ZenCodeType.Method
    public static void notifyTrade(Merchant internal, MerchantOffer offer) {
        
        internal.notifyTrade(offer);
    }
    
    @ZenCodeType.Method
    public static void notifyTradeUpdated(Merchant internal, ItemStack stack) {
        
        internal.notifyTradeUpdated(stack);
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("villagerXp")
    public static int getVillagerXp(Merchant internal) {
        
        return internal.getVillagerXp();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("showProgressBar")
    public static boolean showProgressBar(Merchant internal) {
        
        return internal.showProgressBar();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("notifyTradeSound")
    public static SoundEvent getNotifyTradeSound(Merchant internal) {
        
        return internal.getNotifyTradeSound();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("canRestock")
    public static boolean canRestock(Merchant internal) {
        
        return internal.canRestock();
    }
    
    @ZenCodeType.Method
    public static void openTradingScreen(Merchant internal, Player player, Component displayName, int level) {
        
        internal.openTradingScreen(player, displayName, level);
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("isClientSide")
    public static boolean isClientSide(Merchant internal) {
        
        return internal.isClientSide();
    }
    
}
