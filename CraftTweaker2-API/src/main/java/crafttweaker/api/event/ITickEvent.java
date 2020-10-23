package crafttweaker.api.event;

import crafttweaker.annotations.ZenRegister;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenGetter;

@ZenClass("crafttweaker.event.ITickEvent")
@ZenRegister
public interface ITickEvent {

    @ZenGetter("side")
    String getSide();

    @ZenGetter("phase")
    String getPhase();
    
}