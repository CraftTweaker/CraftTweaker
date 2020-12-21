package com.blamejared.crafttweaker.impl_native.potion;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.impl.helper.CraftTweakerHelper;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import org.openzen.zencode.java.ZenCodeType;

import java.util.Arrays;
import java.util.List;

@ZenRegister
@NativeTypeRegistration(value = EffectInstance.class, zenCodeName = "crafttweaker.api.potion.MCPotionEffectInstance")
public class ExpandEffectInstance {
    
    @ZenCodeType.Method
    public static boolean combine(EffectInstance internal, EffectInstance effect) {
        return internal.combine(effect);
    }
    
    @ZenCodeType.Getter("potion")
    public static Effect getPotion(EffectInstance internal) {
        return internal.getPotion();
    }
    
    @ZenCodeType.Getter("duration")
    public static int getDuration(EffectInstance internal) {
        return internal.getDuration();
    }
    
    @ZenCodeType.Getter("amplifier")
    public static int getAmplifier(EffectInstance internal) {
        return internal.getAmplifier();
    }
    
    @ZenCodeType.Getter("ambient")
    public static boolean isAmbient(EffectInstance internal) {
        return internal.isAmbient();
    }
    
    @ZenCodeType.Getter("showParticles")
    public static boolean doesShowParticles(EffectInstance internal) {
        return internal.doesShowParticles();
    }
    
    @ZenCodeType.Getter("showIcon")
    public static boolean isShowIcon(EffectInstance internal) {
        return internal.isShowIcon();
    }
    
    @ZenCodeType.Getter("effectName")
    public static String getEffectName(EffectInstance internal) {
        return internal.getEffectName();
    }
    
    @ZenCodeType.Method
    public static List<IItemStack> getCurativeItems(EffectInstance internal) {
        return CraftTweakerHelper.getIItemStacks(internal.getCurativeItems());
    }
    
    @ZenCodeType.Method
    public static void setCurativeItems(EffectInstance internal, IItemStack[] items) {
        internal.setCurativeItems(Arrays.asList(CraftTweakerHelper.getItemStacks(items)));
    }
    
    @ZenCodeType.Method
    public static boolean isCurativeItem(EffectInstance internal, IItemStack stack) {
        return internal.isCurativeItem(stack.getInternal());
    }
    
    @ZenCodeType.Method
    public static void addCurativeItem(EffectInstance internal, IItemStack stack) {
        internal.addCurativeItem(stack.getInternal());
    }
    
}
