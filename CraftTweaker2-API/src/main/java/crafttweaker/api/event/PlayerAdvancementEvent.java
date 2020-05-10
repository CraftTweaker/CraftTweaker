package crafttweaker.api.event;

import crafttweaker.annotations.ZenRegister;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenGetter;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("crafttweaker.event.PlayerAdvancementEvent")
@ZenRegister
public interface PlayerAdvancementEvent extends IPlayerEvent {

    @ZenMethod
    @ZenGetter("id")
    String getId();
}
