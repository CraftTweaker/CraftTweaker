package com.blamejared.crafttweaker.natives.event.anvil;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.event.NeoForgeEventCancellationCarrier;
import com.blamejared.crafttweaker.api.event.ZenEvent;
import com.blamejared.crafttweaker.api.event.bus.NeoForgeEventBusWire;
import com.blamejared.crafttweaker.api.event.bus.IEventBus;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.event.AnvilUpdateEvent;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@ZenEvent
@Document("neoforge/api/event/anvil/AnvilUpdateEvent")
@NativeTypeRegistration(value = AnvilUpdateEvent.class, zenCodeName = "crafttweaker.neoforge.api.event.anvil.AnvilUpdateEvent")
public class ExpandAnvilUpdateEvent {
    
    @ZenEvent.Bus
    public static final IEventBus<AnvilUpdateEvent> BUS = IEventBus.cancelable(
            AnvilUpdateEvent.class,
            NeoForgeEventBusWire.of(),
            NeoForgeEventCancellationCarrier.of()
    );
    
    @ZenCodeType.Getter("left")
    public static IItemStack getLeft(AnvilUpdateEvent internal) {
        
        return IItemStack.of(internal.getLeft());
    }
    
    @ZenCodeType.Getter("right")
    public static IItemStack getRight(AnvilUpdateEvent internal) {
        
        return IItemStack.of(internal.getRight());
    }
    
    @ZenCodeType.Nullable
    @ZenCodeType.Getter("name")
    public static String getName(AnvilUpdateEvent internal) {
        
        return internal.getName();
    }
    
    @ZenCodeType.Getter("output")
    public static IItemStack getOutput(AnvilUpdateEvent internal) {
        
        return IItemStack.of(internal.getOutput());
    }
    
    @ZenCodeType.Setter("output")
    public static void setOutput(AnvilUpdateEvent internal, IItemStack output) {
        
        internal.setOutput(output.getInternal());
    }
    
    @ZenCodeType.Getter("cost")
    public static int getCost(AnvilUpdateEvent internal) {
        
        return internal.getCost();
    }
    
    @ZenCodeType.Setter("cost")
    public static void setCost(AnvilUpdateEvent internal, int cost) {
        
        internal.setCost(cost);
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
    public static Player getPlayer(AnvilUpdateEvent internal) {
        
        return internal.getPlayer();
    }
    
}
