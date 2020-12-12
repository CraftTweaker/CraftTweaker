package com.blamejared.crafttweaker.impl.events;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.actions.IUndoableAction;
import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.api.events.IEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.Event;
import org.openzen.zencode.java.ZenCodeType;

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

}
