package com.blamejared.crafttweaker.impl_native.potion;

import com.blamejared.crafttweaker_annotations.annotations.NativeExpansion;
import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.impl.helper.CraftTweakerHelper;
import com.blamejared.crafttweaker_annotations.annotations.DocumentAsType;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import org.openzen.zencode.java.ZenCodeType;

import java.util.List;

@ZenRegister
@DocumentAsType
@NativeExpansion(Effect.class)
public class ExpandEffect {
    
    @ZenCodeType.Method
    public static EffectInstance newInstance(Effect internal, int duration, @ZenCodeType.OptionalInt int amplifier) {
        return new EffectInstance(internal, duration, amplifier);
    }
    
    @ZenCodeType.Method
    public static boolean isReady(Effect internal, int duration, int amplifier) {
        return internal.isReady(duration, amplifier);
    }
    
    @ZenCodeType.Getter("isInstant")
    public static boolean isInstant(Effect internal) {
        return internal.isInstant();
    }
    
    @ZenCodeType.Getter("name")
    public static String getName(Effect internal) {
        return internal.getName();
    }
    
    @ZenCodeType.Getter("displayName")
    public static String getDisplayName(Effect internal) {
        return internal.getDisplayName().getString();
    }
    
    @ZenCodeType.Getter("liquidColor")
    public static int getLiquidColor(Effect internal) {
        return internal.getLiquidColor();
    }
    
    @ZenCodeType.Getter("isBeneficial")
    public static boolean isBeneficial(Effect internal) {
        return internal.isBeneficial();
    }
    
    @ZenCodeType.Getter("curativeItems")
    public static List<IItemStack> getCurativeItems(Effect internal) {
        return CraftTweakerHelper.getIItemStacks(internal.getCurativeItems());
    }
    
    @ZenCodeType.Getter("commandString")
    public static String getCommandString(Effect internal) {
        return "<effect:" + internal.getRegistryName() + ">";
    }
}
