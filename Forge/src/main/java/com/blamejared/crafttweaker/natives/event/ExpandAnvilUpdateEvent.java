package com.blamejared.crafttweaker.natives.event;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.platform.Services;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.AnvilUpdateEvent;
import org.openzen.zencode.java.ZenCodeType;

/**
 * AnvilUpdateEvent is fired when the inputs (either input stack, or the name) of an anvil are changed.
 * You can listen to this event to add custom anvil recipes.
 *
 * @docEvent canceled vanilla behavior will not run, and the output will be set to `<item:minecraft:air>`.
 * @docEvent notCanceled but the output is not empty, it will set the output and not run vanilla behavior.
 */
@ZenRegister
@Document("forge/api/event/AnvilUpdateEvent")
@NativeTypeRegistration(value = AnvilUpdateEvent.class, zenCodeName = "crafttweaker.api.event.AnvilUpdateEvent")
public class ExpandAnvilUpdateEvent {
    
    @ZenCodeType.Getter("left")
    public static IItemStack getLeft(AnvilUpdateEvent internal) {
        
        return Services.PLATFORM.createMCItemStack(internal.getLeft());
    }
    
    @ZenCodeType.Getter("right")
    public static IItemStack getRight(AnvilUpdateEvent internal) {
        
        return Services.PLATFORM.createMCItemStack(internal.getRight());
    }
    
    @ZenCodeType.Getter("output")
    public static IItemStack getOutput(AnvilUpdateEvent internal) {
        
        return Services.PLATFORM.createMCItemStack(internal.getOutput());
    }
    
    @ZenCodeType.Setter("output")
    public static void setOutput(AnvilUpdateEvent internal, IItemStack stack) {
        
        internal.setOutput(stack.getInternal());
    }
    
    @ZenCodeType.Getter("levelCost")
    public static int getLevelCost(AnvilUpdateEvent internal) {
        
        return internal.getCost();
    }
    
    @ZenCodeType.Setter("levelCost")
    public static void setLevelCost(AnvilUpdateEvent internal, int levelCost) {
        
        internal.setCost(levelCost);
    }
    
    @ZenCodeType.Getter("materialCost")
    public static int getMaterialCost(AnvilUpdateEvent internal) {
        
        return internal.getMaterialCost();
    }
    
    @ZenCodeType.Setter("materialCost")
    public static void setMaterialCost(AnvilUpdateEvent internal, int materialCost) {
        
        internal.setMaterialCost(materialCost);
    }
    
    @ZenCodeType.Getter("player")
    @ZenCodeType.Nullable
    public static Player getPlayer(AnvilUpdateEvent internal) {
        
        return internal.getPlayer();
    }
    
    @ZenCodeType.Getter("name")
    @ZenCodeType.Nullable
    public static String getName(AnvilUpdateEvent internal) {
        
        return internal.getName();
    }
    
}
