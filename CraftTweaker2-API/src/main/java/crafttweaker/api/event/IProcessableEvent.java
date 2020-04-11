package crafttweaker.api.event;

import crafttweaker.annotations.ZenRegister;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenGetter;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenRegister
@ZenClass("crafttweaker.event.IEventProcessable")
public interface IProcessableEvent {

    @ZenMethod
    void process();


    @ZenGetter("processed")
    boolean isProcessed();
}
