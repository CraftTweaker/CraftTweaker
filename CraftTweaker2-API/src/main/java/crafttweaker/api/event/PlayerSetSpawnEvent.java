package crafttweaker.api.event;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.world.IBlockPos;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenGetter;

@ZenClass("crafttweaker.event.PlayerSetSpawnEvent")
@ZenRegister
public interface PlayerSetSpawnEvent extends IPlayerEvent, IEventCancelable {

    @ZenGetter("newSpawn")
    IBlockPos getNewSpawn();

    @ZenGetter("isForced")
    boolean getIsForced();

}
