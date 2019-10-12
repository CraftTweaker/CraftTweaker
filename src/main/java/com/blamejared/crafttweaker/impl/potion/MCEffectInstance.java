package com.blamejared.crafttweaker.impl.potion;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.impl.helper.CraftTweakerHelper;
import net.minecraft.potion.EffectInstance;
import org.openzen.zencode.java.ZenCodeType;

import java.util.Arrays;
import java.util.List;

@ZenRegister
@ZenCodeType.Name("crafttweaker.api.potion.MCPotionEffectInstance")
public class MCEffectInstance {
    
    private final EffectInstance internal;
    
    public MCEffectInstance(EffectInstance internal) {
        this.internal = internal;
    }
    
    @ZenCodeType.Method
    public boolean combine(MCEffectInstance effect) {
        return internal.combine(effect.getInternal());
    }
    
    @ZenCodeType.Getter("potion")
    public MCEffect getPotion() {
        return new MCEffect(internal.getPotion());
    }
    
    @ZenCodeType.Getter("duration")
    public int getDuration() {
        return internal.getDuration();
    }
    
    @ZenCodeType.Getter("amplifier")
    public int getAmplifier() {
        return internal.getAmplifier();
    }
    
    @ZenCodeType.Getter("ambient")
    public boolean isAmbient() {
        return internal.isAmbient();
    }
    
    @ZenCodeType.Getter("showParticles")
    public boolean doesShowParticles() {
        return internal.doesShowParticles();
    }
    
    @ZenCodeType.Getter("showIcon")
    public boolean isShowIcon() {
        return internal.isShowIcon();
    }
    
    @ZenCodeType.Getter("effectName")
    public String getEffectName() {
        return internal.getEffectName();
    }
    
    @ZenCodeType.Method
    public List<IItemStack> getCurativeItems() {
        return CraftTweakerHelper.getIItemStacks(internal.getCurativeItems());
    }
    
    @ZenCodeType.Method
    public void setCurativeItems(IItemStack[] items) {
        internal.setCurativeItems(Arrays.asList(CraftTweakerHelper.getItemStacks(items)));
    }
    
    @ZenCodeType.Method
    public boolean isCurativeItem(IItemStack stack) {
        return internal.isCurativeItem(stack.getInternal());
    }
    
    @ZenCodeType.Method
    public void addCurativeItem(IItemStack stack) {
        internal.addCurativeItem(stack.getInternal());
    }
    
    public EffectInstance getInternal() {
        return internal;
    }
}
