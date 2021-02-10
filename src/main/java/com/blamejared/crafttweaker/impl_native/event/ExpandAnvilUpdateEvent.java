package com.blamejared.crafttweaker.impl_native.event;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.impl.item.MCItemStack;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.EventCancelable;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.event.AnvilUpdateEvent;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("vanilla/api/event/MCAnvilUpdateEvent")
@EventCancelable(
        canceledDescription = "vanilla behavior will not run, and the output will be set to ItemStack.EMPTY.",
        notCanceledDescription = "but the output is not empty, it will set the output and not run vanilla behavior."
)
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
