//TODO this is left as an example of a common event, unfortunately the ItemTooltipCallback class in fabric is fully client side, so it cannot be used on a server
//package com.blamejared.crafttweaker.api.event.type;
//
//import com.blamejared.crafttweaker.api.annotation.ZenRegister;
//import com.blamejared.crafttweaker.api.event.ZenEvent;
//import com.blamejared.crafttweaker.api.event.bus.CommonWirelessEventBusWire;
//import com.blamejared.crafttweaker.api.event.bus.IEventBus;
//import com.blamejared.crafttweaker.api.item.IItemStack;
//import com.blamejared.crafttweaker_annotations.annotations.Document;
//import net.minecraft.network.chat.Component;
//import net.minecraft.world.item.TooltipFlag;
//import org.openzen.zencode.java.ZenCodeType;
//
//import java.util.List;
//
//@ZenRegister
//@ZenEvent
//@Document("vanilla/api/event/item/ItemTooltipEvent")
//@ZenCodeType.Name("crafttweaker.api.event.item.ItemTooltipEvent")
//public final class ItemTooltipEvent {
//    @ZenEvent.Bus
//    public static final IEventBus<ItemTooltipEvent> BUS = IEventBus.direct(ItemTooltipEvent.class, CommonWirelessEventBusWire.of());
//
//    private final IItemStack stack;
//    private final TooltipFlag tooltipFlag;
//    private final List<Component> lines;
//
//    private ItemTooltipEvent(final IItemStack stack, final TooltipFlag tooltipFlag, final List<Component> lines) {
//        this.stack = stack;
//        this.tooltipFlag = tooltipFlag;
//        this.lines = lines;
//    }
//
//    public static ItemTooltipEvent of(final IItemStack stack, final TooltipFlag tooltipFlag, final List<Component> lines) {
//        return new ItemTooltipEvent(stack, tooltipFlag, lines);
//    }
//
//    @ZenCodeType.Getter("stack")
//    public IItemStack stack() {
//        return this.stack;
//    }
//
//    @ZenCodeType.Getter("tooltipFlag")
//    public TooltipFlag tooltipFlag() {
//        return this.tooltipFlag;
//    }
//
//    @ZenCodeType.Getter("lines")
//    public List<Component> lines() {
//        return this.lines;
//    }
//
//}
