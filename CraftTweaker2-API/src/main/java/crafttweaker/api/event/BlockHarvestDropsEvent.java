package crafttweaker.api.event;


import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.WeightedItemStack;
import crafttweaker.api.player.IPlayer;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenGetter;
import stanhebben.zenscript.annotations.ZenMethod;
import stanhebben.zenscript.annotations.ZenSetter;

import java.util.List;

@ZenClass("crafttweaker.event.BlockHarvestDropsEvent")
@ZenRegister
public interface BlockHarvestDropsEvent extends IBlockEvent {

    @ZenGetter("dropChance")
    float getDropChance();

    @ZenSetter("dropChance")
    void setDropChance(float dropChance);

    @ZenGetter("fortuneLevel")
    int getFortuneLevel();

    @ZenGetter("drops")
    List<WeightedItemStack> getDrops();

    @ZenSetter("drops")
    void setDrops(List<WeightedItemStack> drops);

    @ZenMethod
    void addItem(WeightedItemStack itemStack);

    @ZenGetter("silkTouch")
    boolean isSilkTouch();

    @ZenGetter("isPlayer")
    boolean isPlayer();

    @ZenGetter("player")
    IPlayer getPlayer();

}
