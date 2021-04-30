package com.blamejared.crafttweaker.impl_native.advancement;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.impl.item.MCItemStack;
import com.blamejared.crafttweaker.impl.util.text.MCTextComponent;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.advancements.DisplayInfo;
import net.minecraft.util.ResourceLocation;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("vanilla/api/advancement/DisplayInfo")
@NativeTypeRegistration(value = DisplayInfo.class, zenCodeName = "crafttweaker.api.advancement.DisplayInfo")
public class ExpandDisplayInfo {
    
    @ZenCodeType.Getter
    @ZenCodeType.Method
    public static MCTextComponent getTitle(DisplayInfo internal) {
        
        return new MCTextComponent(internal.getTitle());
    }
    
    @ZenCodeType.Getter
    @ZenCodeType.Method
    public static MCTextComponent getDescription(DisplayInfo internal) {
        
        return new MCTextComponent(internal.getDescription());
    }
    
    @ZenCodeType.Getter
    @ZenCodeType.Method
    public static IItemStack getIcon(DisplayInfo internal) {
        
        return new MCItemStack(internal.getIcon());
    }
    
    @ZenCodeType.Getter
    @ZenCodeType.Method
    public static ResourceLocation getBackground(DisplayInfo internal) {
        
        return internal.getBackground();
    }
    
    @ZenCodeType.Getter
    @ZenCodeType.Method
    public static MCFrameType getFrameType(DisplayInfo internal) {
    
        switch(internal.getFrame()){
            case CHALLENGE:
                return MCFrameType.CHALLENGE;
            case GOAL:
                return MCFrameType.GOAL;
            case TASK:
                return MCFrameType.TASK;
            default:
                throw new RuntimeException("Unknown FrameType: " + internal.getFrame());
        }
    }
    
    @ZenCodeType.Getter("showToast")
    @ZenCodeType.Method
    public static boolean shouldShowToast(DisplayInfo internal) {
        
        return internal.shouldShowToast();
    }
    
    @ZenCodeType.Getter("announceToChat")
    @ZenCodeType.Method
    public static boolean shouldAnnounceToChat(DisplayInfo internal) {
        
        return internal.shouldAnnounceToChat();
    }
    
    @ZenCodeType.Getter("hidden")
    @ZenCodeType.Method
    public static boolean isHidden(DisplayInfo internal) {
        
        return internal.isHidden();
    }
    
    @ZenCodeType.Getter
    @ZenCodeType.Method
    public static float getX(DisplayInfo internal) {
        
        return internal.getX();
    }
    
    @ZenCodeType.Getter
    @ZenCodeType.Method
    public static float getY(DisplayInfo internal) {
        
        return internal.getY();
    }
    
}
