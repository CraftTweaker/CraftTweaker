package com.blamejared.crafttweaker.impl_native.advancement;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.impl.util.text.MCTextComponent;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import com.google.common.collect.Lists;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.DisplayInfo;
import net.minecraft.util.ResourceLocation;
import org.openzen.zencode.java.ZenCodeType;

import java.util.List;

@ZenRegister
@Document("vanilla/api/advancement/Advancement")
@NativeTypeRegistration(value = Advancement.class, zenCodeName = "crafttweaker.api.advancement.Advancement")
public class ExpandAdvancement {
    
    /*
   private final AdvancementRewards rewards;
   private final Map<String, Criterion> criteria;
     */
    @ZenCodeType.Getter
    @ZenCodeType.Method
    public static Advancement getParent(Advancement internal) {
        
        return internal.getParent();
    }
    
    @ZenCodeType.Getter
    @ZenCodeType.Method
    public static ResourceLocation getId(Advancement internal) {
        
        return internal.getId();
    }
    
    @ZenCodeType.Getter
    @ZenCodeType.Method
    public static String[][] getRequirements(Advancement internal) {
        
        return internal.getRequirements();
    }
    
    @ZenCodeType.Getter
    @ZenCodeType.Method
    public static List<Advancement> getChildren(Advancement internal) {
        
        return Lists.newArrayList(internal.getChildren());
    }
    
    @ZenCodeType.Getter
    @ZenCodeType.Method
    public static MCTextComponent getDisplayText(Advancement internal) {
        
        return new MCTextComponent(internal.getDisplayText());
    }
    
    @ZenCodeType.Getter
    @ZenCodeType.Method
    public static DisplayInfo getDisplay(Advancement internal) {
        
        return internal.getDisplay();
    }
    
}
