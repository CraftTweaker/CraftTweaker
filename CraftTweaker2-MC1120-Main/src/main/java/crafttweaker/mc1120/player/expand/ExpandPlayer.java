package crafttweaker.mc1120.player.expand;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.container.IContainer;
import crafttweaker.api.entity.IEntityFishHook;
import crafttweaker.api.minecraft.CraftTweakerMC;
import crafttweaker.api.player.IPlayer;
import crafttweaker.api.text.ITextComponent;
import crafttweaker.api.world.IBlockPos;
import net.minecraft.entity.player.EntityPlayer;
import stanhebben.zenscript.annotations.Optional;
import stanhebben.zenscript.annotations.ZenExpansion;
import stanhebben.zenscript.annotations.ZenGetter;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenExpansion("crafttweaker.player.IPlayer")
@ZenRegister
public class ExpandPlayer {
    private static EntityPlayer getInternal(IPlayer expanded) {
        return CraftTweakerMC.getPlayer(expanded);
    }

    @ZenGetter("bedLocation")
    @ZenMethod
    public static IBlockPos getBedLocation(IPlayer player) {
        return CraftTweakerMC.getIBlockPos(getInternal(player).bedLocation);
    }

    @ZenGetter("fishHook")
    @ZenMethod
    public static IEntityFishHook getFishHook(IPlayer player) {
        return CraftTweakerMC.getIEntityFishHook(getInternal(player).fishEntity);
    }

    @ZenGetter("inventoryContainer")
    @ZenMethod
    public static IContainer getInventoryContainer(IPlayer player) {
        return CraftTweakerMC.getIContainer(getInternal(player).inventoryContainer);
    }

    @ZenMethod
    public static void sendRichTextStatusMessage(IPlayer player, ITextComponent textComponent, @Optional(valueBoolean = true) boolean hotbar) {
        getInternal(player).sendStatusMessage(CraftTweakerMC.getITextComponent(textComponent), hotbar);
    }

    @ZenGetter
    @ZenMethod
    public static boolean isSleeping(IPlayer player) {
        return getInternal(player).isPlayerSleeping();
    }

    @ZenGetter
    @ZenMethod
    public static boolean isFullyAsleep(IPlayer player) {
        return getInternal(player).isPlayerFullyAsleep();
    }

    @ZenGetter("sleepTimer")
    @ZenMethod
    public static int getSleepTimer(IPlayer player) {
        return getInternal(player).getSleepTimer();
    }

    @ZenGetter("spectator")
    @ZenMethod
    public static boolean isSpectator(IPlayer player) {
        return getInternal(player).isSpectator();
    }
}