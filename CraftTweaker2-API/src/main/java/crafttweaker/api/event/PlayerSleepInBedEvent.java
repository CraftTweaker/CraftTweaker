package crafttweaker.api.event;

import crafttweaker.annotations.ZenRegister;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenGetter;
import stanhebben.zenscript.annotations.ZenSetter;

/**
 * @author Stan
 */
@ZenClass("crafttweaker.event.PlayerSleepInBedEvent")
@ZenRegister
public interface PlayerSleepInBedEvent extends IPlayerEvent, IEventPositionable {

    @ZenGetter("result")
    public String getResult();
    
    @ZenSetter("result")
    public void setResult(String result);
}
