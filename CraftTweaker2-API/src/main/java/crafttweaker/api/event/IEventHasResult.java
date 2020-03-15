package crafttweaker.api.event;

import crafttweaker.annotations.ZenRegister;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenGetter;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("crafttweaker.event.IEventHasResult")
@ZenRegister
public interface IEventHasResult {
    @ZenMethod
    @ZenGetter("isDenied")
    boolean isDenied();

    @ZenMethod
    @ZenGetter("isDefault")
    boolean isDefault();

    @ZenMethod
    @ZenGetter("isAllowed")
    boolean isAllowed();

    @ZenMethod("deny")
    void setDenied();

    @ZenMethod("default")
    void setDefault();

    @ZenMethod("allow")
    void setAllowed();
}
