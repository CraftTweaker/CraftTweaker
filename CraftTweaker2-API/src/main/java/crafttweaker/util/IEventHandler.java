package crafttweaker.util;

import crafttweaker.annotations.ZenRegister;
import stanhebben.zenscript.annotations.ZenClass;

/**
 * @author Stan
 */
@ZenClass("crafttweaker.events.IEventHandler")
@ZenRegister
public interface IEventHandler<T> {
    
    void handle(T event);
}
