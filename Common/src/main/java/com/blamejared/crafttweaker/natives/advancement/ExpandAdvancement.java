package com.blamejared.crafttweaker.natives.advancement;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.DisplayInfo;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import org.openzen.zencode.java.ZenCodeType;

import java.util.Map;

@ZenRegister
@Document("vanilla/api/advancement/Advancement")
@NativeTypeRegistration(value = Advancement.class, zenCodeName = "crafttweaker.api.advancement.Advancement")
public class ExpandAdvancement {
    
    @ZenCodeType.Nullable
    @ZenCodeType.Method
    @ZenCodeType.Getter("parent")
    public static Advancement getParent(Advancement internal) {
        
        return internal.getParent();
    }
    
    @ZenCodeType.Nullable
    @ZenCodeType.Method
    @ZenCodeType.Getter("display")
    public static DisplayInfo getDisplay(Advancement internal) {
        
        return internal.getDisplay();
    }
    
    @ZenCodeType.Nullable
    @ZenCodeType.Method
    @ZenCodeType.Getter("rewards")
    public static AdvancementRewards getRewards(Advancement internal) {
        
        return internal.getRewards();
    }
    
    @ZenCodeType.Nullable
    @ZenCodeType.Method
    @ZenCodeType.Getter("children")
    public static Iterable<Advancement> getChildren(Advancement internal) {
        
        return internal.getChildren();
    }
    
    @ZenCodeType.Nullable
    @ZenCodeType.Method
    @ZenCodeType.Getter("criteria")
    public static Map<String, Criterion> getCriteria(Advancement internal) {
        
        return internal.getCriteria();
    }
    
    @ZenCodeType.Nullable
    @ZenCodeType.Method
    @ZenCodeType.Getter("maxCriteriaRequired")
    public static int getMaxCriteriaRequired(Advancement internal) {
        
        return internal.getMaxCriteraRequired();
    }
    
    @ZenCodeType.Nullable
    @ZenCodeType.Method
    @ZenCodeType.Getter("id")
    public static ResourceLocation getId(Advancement internal) {
        
        return internal.getId();
    }
    
    @ZenCodeType.Nullable
    @ZenCodeType.Method
    @ZenCodeType.Getter("requirements")
    public static String[][] getRequirements(Advancement internal) {
        
        return internal.getRequirements();
    }
    
    @ZenCodeType.Nullable
    @ZenCodeType.Method
    @ZenCodeType.Getter("chatComponent")
    public static Component getChatComponent(Advancement internal) {
        
        return internal.getChatComponent();
    }
    
}
