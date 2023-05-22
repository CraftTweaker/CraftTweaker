package com.blamejared.crafttweaker.api.event.type;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.event.ZenEvent;
import com.blamejared.crafttweaker.api.event.bus.FabricEventBusWire;
import com.blamejared.crafttweaker.api.event.bus.FabricWiredWrap;
import com.blamejared.crafttweaker.api.event.bus.IEventBus;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import org.openzen.zencode.java.ZenCodeType;

import java.util.List;

@Document("fabric/api/event/ItemTooltipEvent")
@ZenCodeType.Name("crafttweaker.api.event.ItemTooltipEvent")
@ZenEvent
@ZenRegister
public final class ItemTooltipEvent {
    
    @ZenEvent.Bus
    public static final IEventBus<ItemTooltipEvent> BUS = IEventBus.direct(
            ItemTooltipEvent.class,
            FabricEventBusWire.of(
                    ItemTooltipCallback.EVENT,
                    ItemTooltipCallback.class,
                    ItemTooltipEvent.class
            )
    );
    
    private final ItemStack stack;
    private final TooltipFlag tooltipFlag;
    private final List<Component> lines;
    
    private ItemTooltipEvent(final ItemStack stack, final TooltipFlag tooltipFlag, final List<Component> lines) {
        this.stack = stack;
        this.tooltipFlag = tooltipFlag;
        this.lines = lines;
    }
    
    @FabricWiredWrap
    public static ItemTooltipEvent of(final ItemStack stack, final TooltipFlag tooltipFlag, final List<Component> lines) {
        return new ItemTooltipEvent(stack, tooltipFlag, lines);
    }
    
    @ZenCodeType.Getter("stack")
    public ItemStack stack() {
        return this.stack;
    }
    
    @ZenCodeType.Getter("tooltipFlag")
    public TooltipFlag tooltipFlag() {
        return this.tooltipFlag;
    }
    
    @ZenCodeType.Getter("lines")
    public List<Component> lines() {
        return this.lines;
    }
}
