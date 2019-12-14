package com.blamejared.crafttweaker.impl.events;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.api.events.IEvent;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.impl.item.MCItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.Event;
import org.openzen.zencode.java.ZenCodeType;

import java.util.function.Consumer;

@ZenRegister
@ZenCodeType.Name("crafttweaker.api.events.CTEventManager")
public class CTEventManager {

    @ZenCodeType.Method
    public static <EVE extends IEvent<EVE, VA>, VA extends Event> void register(IEvent<EVE, VA> event) {
        MinecraftForge.EVENT_BUS.addListener(event.getConsumer());
    }

    @ZenRegister
    @ZenCodeType.Name("crafttweaker.api.events.CTooltipEvent")
    public static class CTTooltipEvent extends IEvent<CTTooltipEvent, ItemTooltipEvent> {

        @ZenCodeType.Constructor
        public CTTooltipEvent(Consumer<CTTooltipEvent> handler) {
            super(handler);
        }

        @Override
        public Consumer<ItemTooltipEvent> getConsumer() {
            return itemTooltipEvent -> getHandler().accept(this);
        }

        @ZenCodeType.Method
        public IItemStack getItemStack() {
            return new MCItemStack(getInternal().getItemStack());
        }
    }

}
