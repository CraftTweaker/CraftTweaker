package crafttweaker.api.event;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.player.IPlayer;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenGetter;
import stanhebben.zenscript.annotations.ZenSetter;

@ZenClass("crafttweaker.event.BlockBreakEvent")
@ZenRegister
public interface BlockBreakEvent extends IBlockEvent, IEventCancelable {

    @ZenGetter("isPlayer")
    boolean getIsPlayer();

    @ZenGetter("player")
    IPlayer getPlayer();

    @ZenGetter("experience")
    int getExperience();

    @ZenSetter("experience")
    void setExperience(int experience);
}
