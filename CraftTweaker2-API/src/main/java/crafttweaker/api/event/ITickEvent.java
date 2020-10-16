package crafttweaker.api.event;

import crafttweaker.annotations.ZenRegister;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenGetter;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("crafttweaker.event.ITickEvent")
@ZenRegister
public interface ITickEvent {

    @ZenMethod
    @ZenGetter("phase")
    default String getPhase() {
        return null;
    }

    @ZenMethod
    @ZenGetter("side")
    default String getSide() {
        return null;
    }
    
    @ZenMethod
    @ZenGetter("isClient")
    default boolean isClient() {
        return false;
    }
    
    @ZenMethod
    @ZenGetter("isServer")
    default boolean isServer() {
        return false;
    }
}