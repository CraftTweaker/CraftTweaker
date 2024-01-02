package com.blamejared.crafttweaker.natives.event.item;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.event.NeoForgeEventCancellationCarrier;
import com.blamejared.crafttweaker.api.event.ZenEvent;
import com.blamejared.crafttweaker.api.event.bus.NeoForgeEventBusWire;
import com.blamejared.crafttweaker.api.event.bus.IEventBus;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.world.entity.projectile.FishingHook;
import net.neoforged.neoforge.event.entity.player.ItemFishedEvent;
import org.openzen.zencode.java.ZenCodeType;

import java.util.List;
import java.util.stream.Collectors;

@ZenRegister
@ZenEvent
@Document("neoforge/api/event/item/ItemFishedEvent")
@NativeTypeRegistration(value = ItemFishedEvent.class, zenCodeName = "crafttweaker.neoforge.api.event.item.ItemFishedEvent")
public class ExpandItemFishedEvent {
    
    @ZenEvent.Bus
    public static final IEventBus<ItemFishedEvent> BUS = IEventBus.cancelable(
            ItemFishedEvent.class,
            NeoForgeEventBusWire.of(),
            NeoForgeEventCancellationCarrier.of()
    );
    
    @ZenCodeType.Getter("rodDamage")
    public static int getRodDamage(ItemFishedEvent internal) {
        
        return internal.getRodDamage();
    }
    
    @ZenCodeType.Setter("rodDamage")
    public static void setRodDamage(ItemFishedEvent internal, int rodDamage) {
        
        internal.damageRodBy(rodDamage);
    }
    
    @ZenCodeType.Getter("drops")
    public static List<IItemStack> getDrops(ItemFishedEvent internal) {
        
        return internal.getDrops().stream().map(IItemStack::of).collect(Collectors.toList());
    }
    
    @ZenCodeType.Setter("drops")
    public static void setDrops(ItemFishedEvent internal, List<IItemStack> drops) {
        
        internal.getDrops().clear();
        internal.getDrops().addAll(drops.stream().map(IItemStack::getInternal).toList());
    }
    
    @ZenCodeType.Getter("hookEntity")
    public static FishingHook getHookEntity(ItemFishedEvent internal) {
        
        return internal.getHookEntity();
    }
    
}
