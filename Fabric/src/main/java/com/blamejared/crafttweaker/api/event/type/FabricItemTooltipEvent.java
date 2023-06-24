//TODO this is left as an example of a fabric event, unfortunately the ItemTooltipCallback class is fully client side, so it cannot be used on a server
//package com.blamejared.crafttweaker.api.event.type;
//
//import com.blamejared.crafttweaker.api.annotation.ZenRegister;
//import com.blamejared.crafttweaker.api.event.ZenEvent;
//import com.blamejared.crafttweaker.api.event.bus.CommonAdaptingEventBusWire;
//import com.blamejared.crafttweaker.api.event.bus.FabricEventBusWire;
//import com.blamejared.crafttweaker.api.event.bus.FabricWiredWrap;
//import com.blamejared.crafttweaker.api.event.bus.IEventBus;
//import com.blamejared.crafttweaker.api.item.IItemStack;
//import com.blamejared.crafttweaker_annotations.annotations.Document;
//import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback;
//import net.minecraft.network.chat.Component;
//import net.minecraft.world.item.ItemStack;
//import net.minecraft.world.item.TooltipFlag;
//import org.openzen.zencode.java.ZenCodeType;
//
//import java.util.List;
//
//@ZenRegister
//@ZenEvent
//@Document("fabric/api/event/item/ItemTooltipEvent")
//@ZenCodeType.Name("crafttweaker.forge.api.event.item.ItemTooltipEvent")
//public final class FabricItemTooltipEvent {
//
//    @ZenEvent.Bus
//    public static final IEventBus<FabricItemTooltipEvent> BUS = IEventBus.direct(
//            FabricItemTooltipEvent.class,
//            CommonAdaptingEventBusWire.of(
//                    FabricEventBusWire.of(ItemTooltipCallback.EVENT, ItemTooltipCallback.class, FabricItemTooltipEvent.class),
//                    ItemTooltipEvent.BUS,
//                    FabricItemTooltipEvent::toCommon
//            )
//    );
//
//    private final IItemStack stack;
//    private final TooltipFlag tooltipFlag;
//    private final List<Component> lines;
//
//    private FabricItemTooltipEvent(final IItemStack stack, final TooltipFlag tooltipFlag, final List<Component> lines) {
//        this.stack = stack;
//        this.tooltipFlag = tooltipFlag;
//        this.lines = lines;
//    }
//
//    @FabricWiredWrap
//    public static FabricItemTooltipEvent of(final ItemStack stack, final TooltipFlag tooltipFlag, final List<Component> lines) {
//        return new FabricItemTooltipEvent(IItemStack.of(stack), tooltipFlag, lines);
//    }
//
//    private static ItemTooltipEvent toCommon(final FabricItemTooltipEvent platform) {
//        return ItemTooltipEvent.of(platform.stack(), platform.tooltipFlag(), platform.lines());
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
//}
