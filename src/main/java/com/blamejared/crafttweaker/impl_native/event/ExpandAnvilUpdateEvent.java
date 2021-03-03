package com.blamejared.crafttweaker.impl_native.event;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.impl.item.MCItemStack;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.event.AnvilUpdateEvent;
import org.openzen.zencode.java.ZenCodeType;

/**
 * AnvilUpdateEvent is fired when the inputs (either input stack, or the name) to an anvil are changed.
 * Listen it to add your custom anvil recipe.
 *
 * @docEvent canceled vanilla behavior will not run, and the output will be set to `<item:minecraft:air>`.
 * @docEvent notCanceled but the output is not empty, it will set the output and not run vanilla behavior.
 */
@ZenRegister
@Document("vanilla/api/event/MCAnvilUpdateEvent")
@NativeTypeRegistration(value = AnvilUpdateEvent.class, zenCodeName = "crafttweaker.api.event.MCAnvilUpdateEvent")
public class ExpandAnvilUpdateEvent {
    @ZenCodeType.Getter("left")
    public static IItemStack getLeft(AnvilUpdateEvent internal) {
        return new MCItemStack(internal.getLeft());
    }
    
    @ZenCodeType.Getter("right")
    public static IItemStack getRight(AnvilUpdateEvent internal) {
        return new MCItemStack(internal.getRight());
    }
    
    @ZenCodeType.Getter("output")
    public static IItemStack getOutput(AnvilUpdateEvent internal) {
        return new MCItemStack(internal.getOutput());
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
    public static PlayerEntity getPlayer(AnvilUpdateEvent internal) {
        return internal.getPlayer();
    }
}
