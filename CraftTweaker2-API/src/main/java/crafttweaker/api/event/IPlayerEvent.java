package crafttweaker.api.event;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.entity.IEntityLivingBase;
import crafttweaker.api.player.IPlayer;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenGetter;

@ZenRegister
@ZenClass("crafttweaker.event.IPlayerEvent")
public interface IPlayerEvent extends ILivingEvent {

    @ZenGetter("player")
    IPlayer getPlayer();

    @Override
    default IEntityLivingBase getEntityLivingBase() {
        return getPlayer();
    }
}
