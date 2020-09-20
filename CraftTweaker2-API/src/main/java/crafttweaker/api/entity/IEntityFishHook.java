
package crafttweaker.api.entity;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.player.IPlayer;
import stanhebben.zenscript.annotations.*;

@ZenClass("crafttweaker.entity.IEntityFishHook")
@ZenRegister
public interface IEntityFishHook extends IEntity {
    
    @ZenGetter("caughtEntity")
    IEntity caughtEntity();
    
    @ZenGetter("angler")
    IPlayer getAngler();

    @ZenSetter("lureSpeed")
    void setLureSpeed(int lureSpeed);
    
    @ZenSetter("luck")
    void setLuck(int luck);
}