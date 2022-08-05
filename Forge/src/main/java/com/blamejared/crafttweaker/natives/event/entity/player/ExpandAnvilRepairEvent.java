package com.blamejared.crafttweaker.natives.event.entity.player;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.platform.Services;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraftforge.event.entity.player.AnvilRepairEvent;
import org.openzen.zencode.java.ZenCodeType;

import javax.annotation.Nonnull;

@ZenRegister
@Document("forge/api/event/entity/player/AnvilRepairEvent")
@NativeTypeRegistration(value = AnvilRepairEvent.class, zenCodeName = "crafttweaker.api.event.entity.player.AnvilRepairEvent")
public class ExpandAnvilRepairEvent {
    
    @Nonnull
    @ZenCodeType.Method
    @ZenCodeType.Getter("output")
    public static IItemStack getOutput(AnvilRepairEvent internal) {
        
        return Services.PLATFORM.createMCItemStack(internal.getOutput());
    }
    
    @Nonnull
    @ZenCodeType.Method
    @ZenCodeType.Getter("left")
    public static IItemStack getLeft(AnvilRepairEvent internal) {
        
        return Services.PLATFORM.createMCItemStack(internal.getLeft());
    }
    
    @Nonnull
    @ZenCodeType.Method
    @ZenCodeType.Getter("right")
    public static IItemStack getRight(AnvilRepairEvent internal) {
        
        return Services.PLATFORM.createMCItemStack(internal.getRight());
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("breakChance")
    public static float getBreakChance(AnvilRepairEvent internal) {
        
        return internal.getBreakChance();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Setter("breakChance")
    public static void setBreakChance(AnvilRepairEvent internal, float breakChance) {
        
        internal.setBreakChance(breakChance);
    }
    
}
