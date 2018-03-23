package crafttweaker.api.event;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.entity.*;
import crafttweaker.api.player.IPlayer;
import stanhebben.zenscript.annotations.*;

@ZenRegister
@ZenClass("crafttweaker.event.IPlayerEvent")
public interface IPlayerEvent extends ILivingEvent{
    
    @ZenGetter("player")
    IPlayer getPlayer();
    
    @Override
    default IEntityLivingBase getEntityLivingBase(){return getPlayer();}
}
