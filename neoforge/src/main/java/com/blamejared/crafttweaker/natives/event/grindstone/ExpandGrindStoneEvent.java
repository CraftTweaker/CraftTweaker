package com.blamejared.crafttweaker.natives.event.grindstone;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.event.NeoForgeEventCancellationCarrier;
import com.blamejared.crafttweaker.api.event.ZenEvent;
import com.blamejared.crafttweaker.api.event.bus.NeoForgeEventBusWire;
import com.blamejared.crafttweaker.api.event.bus.IEventBus;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.neoforged.neoforge.event.GrindstoneEvent;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("neoforge/api/event/grindstone/GrindstoneEvent")
@NativeTypeRegistration(value = GrindstoneEvent.class, zenCodeName = "crafttweaker.neoforge.api.event.grindstone.GrindstoneEvent")
public class ExpandGrindStoneEvent {
    
    @ZenCodeType.Getter("topItem")
    public static IItemStack getTopItem(GrindstoneEvent internal) {
        
        return IItemStack.of(internal.getTopItem());
    }
    
    @ZenCodeType.Getter("bottomItem")
    public static IItemStack getBottomItem(GrindstoneEvent internal) {
        
        return IItemStack.of(internal.getBottomItem());
    }
    
    @ZenCodeType.Getter("xp")
    public static int getXp(GrindstoneEvent internal) {
        
        return internal.getXp();
    }
    
    @ZenCodeType.Setter("xp")
    public static void setXp(GrindstoneEvent internal, int xp) {
        
        internal.setXp(xp);
    }
    
    @ZenRegister
    @ZenEvent
    @Document("neoforge/api/event/grindstone/GrindstoneOnPlaceItemEvent")
    @NativeTypeRegistration(value = GrindstoneEvent.OnPlaceItem.class, zenCodeName = "crafttweaker.neoforge.api.event.grindstone.GrindstoneOnPlaceItemEvent")
    public static class ExpandGrindstoneOnPlaceItemEvent {
        
        @ZenEvent.Bus
        public static final IEventBus<GrindstoneEvent.OnPlaceItem> BUS = IEventBus.cancelable(
                GrindstoneEvent.OnPlaceItem.class,
                NeoForgeEventBusWire.of(),
                NeoForgeEventCancellationCarrier.of()
        );
        
        @ZenCodeType.Getter("output")
        public static IItemStack getOutput(GrindstoneEvent.OnPlaceItem internal) {
            
            return IItemStack.of(internal.getOutput());
        }
        
        @ZenCodeType.Setter("output")
        public static void setOutput(GrindstoneEvent.OnPlaceItem internal, IItemStack output) {
            
            internal.setOutput(output.getInternal());
        }
        
    }
    
    @ZenRegister
    @ZenEvent
    @Document("neoforge/api/event/grindstone/GrindstoneOnTakeItemEvent")
    @NativeTypeRegistration(value = GrindstoneEvent.OnTakeItem.class, zenCodeName = "crafttweaker.neoforge.api.event.grindstone.GrindstoneOnTakeItemEvent")
    public static class ExpandGrindstoneOnTakeItemEvent {
        
        @ZenEvent.Bus
        public static final IEventBus<GrindstoneEvent.OnTakeItem> BUS = IEventBus.cancelable(
                GrindstoneEvent.OnTakeItem.class,
                NeoForgeEventBusWire.of(),
                NeoForgeEventCancellationCarrier.of()
        );
        
        @ZenCodeType.Getter("newTopItem")
        public static IItemStack getNewTopItem(GrindstoneEvent.OnTakeItem internal) {
            
            return IItemStack.of(internal.getNewTopItem());
        }
        
        @ZenCodeType.Getter("newBottomItem")
        public static IItemStack getNewBottomItem(GrindstoneEvent.OnTakeItem internal) {
            
            return IItemStack.of(internal.getNewBottomItem());
        }
        
        @ZenCodeType.Setter("newTopItem")
        public static void setNewTopItem(GrindstoneEvent.OnTakeItem internal, IItemStack newTop) {
            
            internal.setNewTopItem(newTop.getInternal());
        }
        
        @ZenCodeType.Setter("newBottomItem")
        public static void setNewBottomItem(GrindstoneEvent.OnTakeItem internal, IItemStack newBottom) {
            
            internal.setNewBottomItem(newBottom.getInternal());
        }
        
        @ZenCodeType.Getter("xp")
        public static int getXp(GrindstoneEvent.OnTakeItem internal) {
            
            return internal.getXp();
        }
        
    }
    
}
