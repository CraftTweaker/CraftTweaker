package crafttweaker.api.event;


import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.*;
import crafttweaker.api.player.IPlayer;
import stanhebben.zenscript.annotations.*;

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
