package crafttweaker.api.event;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.world.IBlockPos;
import stanhebben.zenscript.annotations.*;

/**
 * @author Stan
 */
@ZenClass("crafttweaker.event.PlayerSleepInBedEvent")
@ZenRegister
public interface PlayerSleepInBedEvent extends IPlayerEvent, IEventPositionable {}
