
package crafttweaker.api.entity;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.player.IPlayer;
import stanhebben.zenscript.annotations.*;

@ZenClass("crafttweaker.entity.IEntityFishHook")
@ZenRegister
public interface IEntityFishHook extends IEntity {

    @ZenMethod
    @ZenGetter("state")
    String getState();

    @ZenMethod
    @ZenSetter("state")
    void setState(String state);

    @ZenMethod
    @ZenGetter("caughtEntity")
    IEntity getCaughtEntity();

    @ZenMethod
    @ZenSetter("caughtEntity")
    void setCaughtEntity(IEntity entity);

    @ZenMethod
    @ZenGetter("angler")
    IPlayer getAngler();

    @ZenMethod
    @ZenSetter("lureSpeed")
    void setLureSpeed(int lureSpeed);

    @ZenMethod
    @ZenSetter("luck")
    void setLuck(int luck);
}