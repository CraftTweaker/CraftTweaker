package crafttweaker.api.event;

import crafttweaker.annotations.ZenRegister;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenGetter;
import stanhebben.zenscript.annotations.ZenMethod;
import stanhebben.zenscript.annotations.ZenSetter;

@ZenClass("crafttweaker.event.IEventCancelable")
@ZenRegister
public interface IEventCancelable {

    @ZenMethod
    @ZenGetter("canceled")
    boolean isCanceled();

    @ZenMethod
    default void cancel() {
        setCanceled(true);
    }

    @ZenSetter("canceled")
    void setCanceled(boolean canceled);
}
