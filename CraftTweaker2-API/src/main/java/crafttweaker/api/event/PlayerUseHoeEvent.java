package crafttweaker.api.event;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.block.IBlock;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.player.IEntityPlayer;
import crafttweaker.api.world.*;
import stanhebben.zenscript.annotations.*;

/**
 * @author Stan
 */
@ZenClass("crafttweaker.event.PlayerUseHoeEvent")
@ZenRegister
public class PlayerUseHoeEvent implements IEventCancelable {
    
    private final IEntityPlayer player;
    private final IItemStack item;
    private final IWorld world;
    private final int x;
    private final int y;
    private final int z;
    private boolean canceled;
    private boolean processed;
    
    public PlayerUseHoeEvent(IEntityPlayer player, IItemStack item, IWorld blocks, int x, int y, int z) {
        this.player = player;
        this.item = item;
        this.world = blocks;
        this.x = x;
        this.y = y;
        this.z = z;
        
        canceled = false;
        processed = false;
    }
    
    @Override
    public void cancel() {
        canceled = true;
    }
    
    @ZenMethod
    public void process() {
        processed = true;
    }
    
    @Override
    public boolean isCanceled() {
        return canceled;
    }
    
    @ZenGetter("processed")
    public boolean isProcessed() {
        return processed;
    }
    
    @ZenGetter("player")
    public IEntityPlayer getPlayer() {
        return player;
    }
    
    @ZenGetter("item")
    public IItemStack getItem() {
        return item;
    }
    
    @ZenGetter("world")
    public IWorld getBlocks() {
        return world;
    }
    
    @ZenGetter("x")
    public int getX() {
        return x;
    }
    
    @ZenGetter("y")
    public int getY() {
        return y;
    }
    
    @ZenGetter("z")
    public int getZ() {
        return z;
    }
    
    @ZenGetter("dimension")
    public int getDimension() {
        return world.getDimension();
    }
    
    @ZenGetter("block")
    public IBlock getBlock() {
        return world.getBlock(x, y, z);
    }
}
