package com.blamejared.crafttweaker.impl.events;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.api.events.IEvent;
import com.blamejared.crafttweaker.api.events.IEventHandler;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.impl.item.MCItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import org.openzen.zencode.java.ZenCodeType;

import java.util.function.Consumer;

@ZenRegister
@ZenCodeType.Name("crafttweaker.api.events.CTEventManager")
public class CTEventManager {
    
    @ZenCodeType.Method
    public static void register(IEvent event) {
        MinecraftForge.EVENT_BUS.addListener(event.getConsumer());
    }
    
    public static class CTTooltipEvent extends IEvent<CTTooltipEvent, ItemTooltipEvent> {
    
        public CTTooltipEvent(IEventHandler<CTTooltipEvent> handler) {
            super(handler);
        }
    
        @Override
        public Consumer<ItemTooltipEvent> getConsumer() {
            return itemTooltipEvent -> getHandler().handle(this);
        }
    
        @ZenCodeType.Method
        public IItemStack getItemStack() {
            return new MCItemStack(getInternal().getItemStack());
        }
    }
    
}
