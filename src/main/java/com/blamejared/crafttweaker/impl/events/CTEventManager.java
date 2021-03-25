package com.blamejared.crafttweaker.impl.events;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.actions.IUndoableAction;
import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.EventPriority;
import org.openzen.zencode.java.ZenCodeType;

import java.util.function.Consumer;

/**
 * The event Manager is your go-to point if you want to register custom event handlers.
 *
 * You can register EventHandlers for everything that derives from {@link Event}.
 * Make sure to tell ZC of the type you are using, so that you can access the event's properties.
 */
@ZenRegister
@Document("vanilla/api/event/CTEventManager")
@ZenCodeType.Name("crafttweaker.api.events.CTEventManager")
public class CTEventManager {
    
    /**
     * Registers a new Event listener.
     * @param typeOfT Internally used to determine the Event, invisible to scripts.
     * @param consumer The event handler as consumer
     * @param <T> The type of the event
     *
     * @docParam <T> crafttweaker.api.event.entity.player.MCAnvilRepairEvent
     * @docParam consumer (event) => {
     *     var player = event.player;
     *     var result = event.itemResult;
     *     println("Player '" + player.name + "' crafted " + result.commandString);
     * }
     * @docParam <T> crafttweaker.api.event.MCEvent
     * @docParam consumer (event) => {
     *     //Don't actually register a consumer for every event
     *     println("Some Event was captured");
     * }
     */
    @ZenCodeType.Method
    public static <T extends Event> void register(Class<T> typeOfT, Consumer<T> consumer) {
        CraftTweakerAPI.apply(new IUndoableAction() {
            private final EventHandlerWrapper<T> eventHandler = new EventHandlerWrapper<T>(consumer);
    
            @Override
            public void undo() {
        
                MinecraftForge.EVENT_BUS.unregister(eventHandler);
            }
    
            @Override
            public String describeUndo() {
        
                return "Unregistering event listener for " + typeOfT.getSimpleName() + ".";
            }
            
            @Override
            public void apply() {
                //Let's go completely safe and use the type
                MinecraftForge.EVENT_BUS.addListener(EventPriority.NORMAL, false, typeOfT, eventHandler);
            }
            
            @Override
            public String describe() {
                return "Registering event listener for " + typeOfT.getSimpleName() + ".";
            }
        });
    }
    
}
