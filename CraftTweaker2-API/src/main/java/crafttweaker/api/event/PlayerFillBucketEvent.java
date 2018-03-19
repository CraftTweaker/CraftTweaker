package crafttweaker.api.event;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.block.IBlock;
import crafttweaker.api.entity.IEntity;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.world.*;
import stanhebben.zenscript.annotations.*;

/**
 * @author Stan
 */
@ZenClass("crafttweaker.event.PlayerFillBucketEvent")
@ZenRegister
public interface PlayerFillBucketEvent extends IEventCancelable, PlayerEvent {
    
    @ZenGetter("result")
    IItemStack getResult();
    
    /**
     * Sets the resulting item. Automatically processes the event, too.
     *
     * @param result resulting item
     */
    @ZenSetter("result")
    void setResult(IItemStack result);
    
    @ZenGetter("emptyBucket")
    IItemStack getEmptyBucket();
    
    @ZenGetter("world")
    IWorld getWorld();
    
    @ZenGetter("x")
    int getX();
    
    @ZenGetter("y")
    int getY();
    
    @ZenGetter("z")
    int getZ();
    
    @ZenGetter("block")
    IBlock getBlock();
    
    @ZenGetter("dimension")
    int getDimension();
    
    @ZenGetter("position")
    IBlockPos getPos();
    
    @ZenGetter("hitEntity")
    IEntity getHitEntity();
}
