package com.blamejared.crafttweaker.impl.events;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.actions.IUndoableAction;
import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.EventPriority;
import org.openzen.zencode.java.ZenCodeType;

import java.util.function.Consumer;

@ZenRegister
@ZenCodeType.Name("crafttweaker.api.events.CTEventManager")
public class CTEventManager {
    
    @ZenCodeType.Method
    public static <T extends Event> void register(Class<T> typeOfT, Consumer<T> consumer) {
        CraftTweakerAPI.apply(new IUndoableAction() {
            @Override
            public void undo() {
                MinecraftForge.EVENT_BUS.unregister(consumer);
            }
            
            @Override
            public String describeUndo() {
                return "Unregistering event listener for " + typeOfT.getSimpleName() + ".";
            }
            
            @Override
            public void apply() {
                //Let's go completely safe and use the type
                MinecraftForge.EVENT_BUS.addListener(EventPriority.NORMAL, false, typeOfT, consumer);
            }
            
            @Override
            public String describe() {
                return "Registering event listener for " + typeOfT.getSimpleName() + ".";
            }
        });
    }
    
}
