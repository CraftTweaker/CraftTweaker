package com.blamejared.crafttweaker.api.event;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.action.event.ActionRegisterEvent;
import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
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
@Document("forge/api/event/CTEventManager")
@ZenCodeType.Name("crafttweaker.api.events.CTEventManager")
public class CTEventManager {
    
    /**
     * Registers a new Event listener.
     *
     * @param typeOfT  Internally used to determine the Event, invisible to scripts.
     * @param consumer The event handler as consumer
     * @param <T>      The type of the event
     *
     * @docParam <T> crafttweaker.api.event.entity.player.MCAnvilRepairEvent
     * @docParam consumer (event) => {
     * var player = event.player;
     * var result = event.itemResult;
     * println("Player '" + player.name + "' crafted " + result.commandString);
     * }
     * @docParam <T> crafttweaker.api.event.MCEvent
     * @docParam consumer (event) => {
     * //Don't actually register a consumer for every event
     * println("Some Event was captured");
     * }
     */
    @ZenCodeType.Method
    public static <T extends Event> void register(Class<T> typeOfT, Consumer<T> consumer) {
        
        register(typeOfT, false,EventPriority.NORMAL, consumer);
    }
    
    /**
     * Registers a new Event listener.
     *
     * @param typeOfT  Internally used to determine the Event, invisible to scripts.
     *                      * @param listenToCancelled should the event listen to cancelled events
     * @param consumer The event handler as consumer
     * @param <T>      The type of the event
     *
     * @docParam <T> crafttweaker.api.event.entity.player.MCAnvilRepairEvent
     * @docParam listenToCancelled true
     * @docParam consumer (event) => {
     * var player = event.player;
     * var result = event.itemResult;
     * println("Player '" + player.name + "' crafted " + result.commandString);
     * }
     * @docParam <T> crafttweaker.api.event.MCEvent
     * @docParam consumer (event) => {
     * //Don't actually register a consumer for every event
     * println("Some Event was captured");
     * }
     */
    @ZenCodeType.Method
    public static <T extends Event> void register(Class<T> typeOfT, boolean listenToCancelled, Consumer<T> consumer) {
        
        register(typeOfT, listenToCancelled,EventPriority.NORMAL, consumer);
    }
    
    /**
     * Registers a new Event listener with a specific priority.
     *
     * @param typeOfT  Internally used to determine the Event, invisible to scripts.
     * @param priority priority for this listener
     * @param consumer The event handler as consumer
     * @param <T>      The type of the event
     *
     * @docParam <T> crafttweaker.api.event.entity.player.MCAnvilRepairEvent
     * @docParam priority EventPriority.HIGHEST
     * @docParam consumer (event) => {
     * var player = event.player;
     * var result = event.itemResult;
     * println("Player '" + player.name + "' crafted " + result.commandString);
     * }
     */
    @ZenCodeType.Method
    public static <T extends Event> void register(Class<T> typeOfT, EventPriority priority, Consumer<T> consumer) {
        
        CraftTweakerAPI.apply(new ActionRegisterEvent<>(typeOfT, consumer, priority));
    }
    
    /**
     * Registers a new Event listener with a specific priority.
     *
     * @param typeOfT  Internally used to determine the Event, invisible to scripts.
     * @param listenToCancelled should the event listen to cancelled events
     * @param priority priority for this listener
     * @param consumer The event handler as consumer
     * @param <T>      The type of the event
     *
     * @docParam <T> crafttweaker.api.event.entity.player.MCAnvilRepairEvent
     * @docParam listenToCancelled true
     * @docParam priority EventPriority.HIGHEST
     * @docParam consumer (event) => {
     * var player = event.player;
     * var result = event.itemResult;
     * println("Player '" + player.name + "' crafted " + result.commandString);
     * }
     */
    @ZenCodeType.Method
    public static <T extends Event> void register(Class<T> typeOfT, boolean listenToCancelled, EventPriority priority, Consumer<T> consumer) {
        
        CraftTweakerAPI.apply(new ActionRegisterEvent<>(typeOfT, listenToCancelled, consumer, priority));
    }
    
}
