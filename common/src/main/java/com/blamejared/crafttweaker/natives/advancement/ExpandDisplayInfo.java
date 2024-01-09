package com.blamejared.crafttweaker.natives.advancement;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.advancements.AdvancementType;
import net.minecraft.advancements.DisplayInfo;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("vanilla/api/advancement/DisplayInfo")
@NativeTypeRegistration(value = DisplayInfo.class, zenCodeName = "crafttweaker.api.advancement.DisplayInfo")
public class ExpandDisplayInfo {
    
    @ZenCodeType.Method
    public static void setLocation(DisplayInfo internal, float x, float y) {
        
        internal.setLocation(x, y);
    }
    
    @ZenCodeType.Getter("title")
    public static Component getTitle(DisplayInfo internal) {
        
        return internal.getTitle();
    }
    
    @ZenCodeType.Getter("description")
    public static Component getDescription(DisplayInfo internal) {
        
        return internal.getDescription();
    }
    
    @ZenCodeType.Getter("icon")
    public static ItemStack getIcon(DisplayInfo internal) {
        
        return internal.getIcon();
    }
    
    @ZenCodeType.Nullable
    @ZenCodeType.Getter("background")
    public static ResourceLocation getBackground(DisplayInfo internal) {
        
        return internal.getBackground().orElse(null);
    }
    
    @ZenCodeType.Getter("type")
    public static AdvancementType getFrame(DisplayInfo internal) {
        
        return internal.getType();
    }
    
    @ZenCodeType.Getter("x")
    public static float getX(DisplayInfo internal) {
        
        return internal.getX();
    }
    
    @ZenCodeType.Getter("y")
    public static float getY(DisplayInfo internal) {
        
        return internal.getY();
    }
    
    @ZenCodeType.Getter("shouldShowToast")
    public static boolean shouldShowToast(DisplayInfo internal) {
        
        return internal.shouldShowToast();
    }
    
    @ZenCodeType.Getter("shouldAnnounceChat")
    public static boolean shouldAnnounceChat(DisplayInfo internal) {
        
        return internal.shouldAnnounceChat();
    }
    
    @ZenCodeType.Getter("isHidden")
    public static boolean isHidden(DisplayInfo internal) {
        
        return internal.isHidden();
    }
    
}
