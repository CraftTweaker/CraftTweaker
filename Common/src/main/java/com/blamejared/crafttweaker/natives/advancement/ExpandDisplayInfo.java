package com.blamejared.crafttweaker.natives.advancement;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.advancements.DisplayInfo;
import net.minecraft.advancements.FrameType;
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
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("title")
    public static Component getTitle(DisplayInfo internal) {
        
        return internal.getTitle();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("description")
    public static Component getDescription(DisplayInfo internal) {
        
        return internal.getDescription();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("icon")
    public static ItemStack getIcon(DisplayInfo internal) {
        
        return internal.getIcon();
    }
    
    @ZenCodeType.Nullable
    @ZenCodeType.Method
    @ZenCodeType.Getter("background")
    public static ResourceLocation getBackground(DisplayInfo internal) {
        
        return internal.getBackground();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("frame")
    public static FrameType getFrame(DisplayInfo internal) {
        
        return internal.getFrame();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("x")
    public static float getX(DisplayInfo internal) {
        
        return internal.getX();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("y")
    public static float getY(DisplayInfo internal) {
        
        return internal.getY();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("shouldShowToast")
    public static boolean shouldShowToast(DisplayInfo internal) {
        
        return internal.shouldShowToast();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("shouldAnnounceChat")
    public static boolean shouldAnnounceChat(DisplayInfo internal) {
        
        return internal.shouldAnnounceChat();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("isHidden")
    public static boolean isHidden(DisplayInfo internal) {
        
        return internal.isHidden();
    }
    
}
