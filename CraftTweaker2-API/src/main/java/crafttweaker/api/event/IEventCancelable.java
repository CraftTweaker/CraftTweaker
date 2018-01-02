package crafttweaker.api.event;

import stanhebben.zenscript.annotations.*;

@ZenClass("crafttweaker.event.IEventCancelable")
public interface IEventCancelable {
    
    @ZenMethod
    @ZenGetter("canceled")
    boolean isCanceled();
    
    @ZenMethod
    void cancel();
}
