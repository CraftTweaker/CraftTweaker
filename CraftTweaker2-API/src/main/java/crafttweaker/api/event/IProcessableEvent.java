package crafttweaker.api.event;

import crafttweaker.annotations.ZenRegister;
import stanhebben.zenscript.annotations.*;

@ZenRegister
@ZenClass("crafttweaker.event.IEventProcessable")
public interface IProcessableEvent {
    
    @ZenMethod
    void process();
    
    
    @ZenGetter("processed")
    boolean isProcessed();
}
