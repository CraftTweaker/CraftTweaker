package com.blamejared.crafttweaker.impl.events;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.actions.IUndoableAction;
import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.api.events.IEvent;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.impl.item.MCItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.Event;
import org.openzen.zencode.java.ZenCodeType;

import java.util.List;
import java.util.function.Consumer;

@ZenRegister
@ZenCodeType.Name("crafttweaker.api.events.CTEventManager")
public class CTEventManager {

    @ZenCodeType.Method
    public static void register(IEvent<?, ?> event) {
        final Consumer<? extends Event> consumer = event.getConsumer();
        CraftTweakerAPI.apply(new IUndoableAction() {
            @Override
            public void undo() {
                MinecraftForge.EVENT_BUS.unregister(consumer);
            }

            @Override
            public String describeUndo() {
                return "Unregistering event listener for " + event.getName() + ".";
            }

            @Override
            public void apply() {
                MinecraftForge.EVENT_BUS.addListener(consumer);
            }

            @Override
            public String describe() {
                return "Registering event listener for " + event.getName() + ".";
            }
        });
    }

    @ZenRegister
    @ZenCodeType.Name("crafttweaker.api.events.CTooltipEvent")
    public static class CTTooltipEvent extends IEvent<CTTooltipEvent, ItemTooltipEvent> {

        @ZenCodeType.Constructor
        public CTTooltipEvent(Consumer<CTTooltipEvent> handler) {
            super(handler);
        }

        public CTTooltipEvent(ItemTooltipEvent itemTooltipEvent) {
            super(itemTooltipEvent);
        }

        @Override
        public Consumer<ItemTooltipEvent> getConsumer() {
            return itemTooltipEvent -> getHandler().accept(new CTTooltipEvent(itemTooltipEvent));
        }

        @Override
        public String getName() {
            return "CTTooltipEvent";
        }

        @ZenCodeType.Method
        public IItemStack getItemStack() {
            return new MCItemStack(getInternal().getItemStack());
        }
    }

}
