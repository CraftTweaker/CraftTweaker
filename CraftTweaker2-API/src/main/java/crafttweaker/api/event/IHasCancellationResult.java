package crafttweaker.api.event;

import crafttweaker.annotations.ZenRegister;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenGetter;
import stanhebben.zenscript.annotations.ZenSetter;

@ZenClass("crafttweaker.event.IHasCancellationResult")
@ZenRegister
public interface IHasCancellationResult {
    @ZenGetter("cancellationResult")
    String getCancellationResult ();

    @ZenSetter("cancellationResult")
    void setCancellationResult (String value);
}
