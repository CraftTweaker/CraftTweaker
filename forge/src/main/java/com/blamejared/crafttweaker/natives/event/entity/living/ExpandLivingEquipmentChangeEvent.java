package com.blamejared.crafttweaker.natives.event.entity.living;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.event.ZenEvent;
import com.blamejared.crafttweaker.api.event.bus.ForgeEventBusWire;
import com.blamejared.crafttweaker.api.event.bus.IEventBus;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingEquipmentChangeEvent;
import org.jetbrains.annotations.NotNull;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@ZenEvent
@Document("forge/api/event/entity/living/LivingEquipmentChangeEvent")
@NativeTypeRegistration(value = LivingEquipmentChangeEvent.class, zenCodeName = "crafttweaker.forge.api.event.entity.living.LivingEquipmentChangeEvent")
public class ExpandLivingEquipmentChangeEvent {
    
    @ZenEvent.Bus
    public static final IEventBus<LivingEquipmentChangeEvent> BUS = IEventBus.direct(
            LivingEquipmentChangeEvent.class,
            ForgeEventBusWire.of()
    );
    
    @ZenCodeType.Getter("slot")
    public static EquipmentSlot getSlot(LivingEquipmentChangeEvent internal) {
        
        return internal.getSlot();
    }
    
    @ZenCodeType.Getter("from")
    public static IItemStack getFrom(LivingEquipmentChangeEvent internal) {
        
        return IItemStack.of(internal.getFrom());
    }
    
    @ZenCodeType.Getter("to")
    public static IItemStack getTo(LivingEquipmentChangeEvent internal) {
        
        return IItemStack.of(internal.getTo());
    }
    
}
