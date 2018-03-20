package crafttweaker.api.event;

import crafttweaker.annotations.ZenRegister;
import stanhebben.zenscript.annotations.*;

@ZenClass("crafttweaker.event.IEventCancelable")
@ZenRegister
public interface IEventCancelable {
    
    @ZenMethod
    @ZenGetter("canceled")
    boolean isCanceled();
    
    @ZenMethod
    default void cancel(){
        setCanceled(true);
    }
    
    @ZenSetter("canceled")
    void setCanceled(boolean canceled);
}
