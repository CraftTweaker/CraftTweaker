package crafttweaker.api.event;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.tileentity.IMobSpawnerBaseLogic;
import crafttweaker.api.world.IWorld;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenGetter;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("crafttweaker.event.EntityLivingSpawnEvent")
@ZenRegister
public interface EntityLivingSpawnEvent extends ILivingEvent {

    @ZenGetter("world")
    IWorld getWorld();

    @ZenGetter("x")
    float getX();

    @ZenGetter("y")
    float getY();

    @ZenGetter("z")
    float getZ();

    @ZenMethod
    void allow();

    @ZenMethod
    void deny();

    @ZenMethod
    void pass();

    @ZenClass("crafttweaker.event.EntityLivingExtendedSpawnEvent")
    @ZenRegister
    interface EntityLivingExtendedSpawnEvent extends EntityLivingSpawnEvent {

        @ZenGetter("spawner")
        IMobSpawnerBaseLogic getSpawner();
    }
}
