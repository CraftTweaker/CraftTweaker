package crafttweaker.mc1120.player.expand;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.container.IContainer;
import crafttweaker.api.entity.IEntityFishHook;
import crafttweaker.api.minecraft.CraftTweakerMC;
import crafttweaker.api.player.IPlayer;
import crafttweaker.api.world.IBlockPos;
import net.minecraft.entity.player.EntityPlayer;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenGetter;

@ZenClass("crafttweaker.player.IPlayer")
@ZenRegister
public class ExpandPlayer {
    private static EntityPlayer getInternal(IPlayer expanded) {
        return CraftTweakerMC.getPlayer(expanded);
    }

    @ZenGetter("bedLocation")
    public IBlockPos getBedLocation(IPlayer player) {
        return CraftTweakerMC.getIBlockPos(getInternal(player).bedLocation);
    }

    @ZenGetter("fishHook")
    public IEntityFishHook getFishHook(IPlayer player) {
        return CraftTweakerMC.getIEntityFishHook(getInternal(player).fishEntity);
    }

    @ZenGetter("inventoryContainer")
    public IContainer getInventoryContainer(IPlayer player) {
        return CraftTweakerMC.getIContainer(getInternal(player).inventoryContainer);
    }

    @ZenGetter
    public boolean isSleeping(IPlayer player) {
        return getInternal(player).isPlayerSleeping();
    }

    @ZenGetter
    public boolean isFullyAsleep(IPlayer player) {
        return getInternal(player).isPlayerFullyAsleep();
    }

    @ZenGetter("sleepTimer")
    public int getSleepTimer(IPlayer player) {
        return getInternal(player).getSleepTimer();
    }
}