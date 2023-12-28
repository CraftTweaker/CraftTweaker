package com.blamejared.crafttweaker.natives.advancement;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.util.GenericUtil;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRequirements;
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
    
    @ZenCodeType.Getter("isRoot")
    public static boolean isRoot(Advancement internal) {
        
        return internal.isRoot();
    }
    
    @ZenCodeType.Nullable
    @ZenCodeType.Getter("parent")
    public static ResourceLocation parent(Advancement internal) {
        
        return internal.parent().orElse(null);
    }
    
    @ZenCodeType.Nullable
    @ZenCodeType.Getter("display")
    public static DisplayInfo display(Advancement internal) {
        
        return internal.display().orElse(null);
    }
    
    @ZenCodeType.Getter("rewards")
    public static AdvancementRewards rewards(Advancement internal) {
        
        return internal.rewards();
    }
    
    @ZenCodeType.Getter("criteria")
    public static Map<String, Criterion> criteria(Advancement internal) {
        
        return GenericUtil.uncheck(internal.criteria());
    }
    
    @ZenCodeType.Getter("requirements")
    public static AdvancementRequirements requirements(Advancement internal) {
        
        return internal.requirements();
    }
    
    @ZenCodeType.Getter("sendsTelemetryEvent")
    public static boolean sendsTelemetryEvent(Advancement internal) {
        
        return internal.sendsTelemetryEvent();
    }
    
    @ZenCodeType.Nullable
    @ZenCodeType.Getter("name")
    public static Component name(Advancement internal) {
        
        return internal.name().orElse(null);
    }
    
}
