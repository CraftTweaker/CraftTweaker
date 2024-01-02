package com.blamejared.crafttweaker.natives.event.enchantment;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.event.ZenEvent;
import com.blamejared.crafttweaker.api.event.bus.NeoForgeEventBusWire;
import com.blamejared.crafttweaker.api.event.bus.IEventBus;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.event.enchanting.EnchantmentLevelSetEvent;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@ZenEvent
@Document("neoforge/api/event/enchantment/EnchantmentLevelSetEvent")
@NativeTypeRegistration(value = EnchantmentLevelSetEvent.class, zenCodeName = "crafttweaker.neoforge.api.event.enchantment.EnchantmentLevelSetEvent")
public class ExpandEnchantmentLevelSetEvent {
    
    @ZenEvent.Bus
    public static final IEventBus<EnchantmentLevelSetEvent> BUS = IEventBus.direct(
            EnchantmentLevelSetEvent.class,
            NeoForgeEventBusWire.of()
    );
    
    @ZenCodeType.Getter("level")
    public static Level getLevel(EnchantmentLevelSetEvent internal) {
        
        return internal.getLevel();
    }
    
    @ZenCodeType.Getter("pos")
    public static BlockPos getPos(EnchantmentLevelSetEvent internal) {
        
        return internal.getPos();
    }
    
    @ZenCodeType.Getter("enchantRow")
    public static int getEnchantRow(EnchantmentLevelSetEvent internal) {
        
        return internal.getEnchantRow();
    }
    
    @ZenCodeType.Getter("power")
    public static int getPower(EnchantmentLevelSetEvent internal) {
        
        return internal.getPower();
    }
    
    @ZenCodeType.Getter("item")
    public static IItemStack getItem(EnchantmentLevelSetEvent internal) {
        
        return IItemStack.of(internal.getItem());
    }
    
    @ZenCodeType.Getter("originalLevel")
    public static int getOriginalLevel(EnchantmentLevelSetEvent internal) {
        
        return internal.getOriginalLevel();
    }
    
    @ZenCodeType.Getter("enchantLevel")
    public static int getEnchantLevel(EnchantmentLevelSetEvent internal) {
        
        return internal.getEnchantLevel();
    }
    
    @ZenCodeType.Setter("enchantLevel")
    public static void setEnchantLevel(EnchantmentLevelSetEvent internal, int level) {
        
        internal.setEnchantLevel(level);
    }
    
}
