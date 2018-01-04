package crafttweaker.api.event;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.block.IBlock;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.player.IPlayer;
import crafttweaker.api.world.*;
import stanhebben.zenscript.annotations.*;

/**
 * @author Stan
 */
@ZenClass("crafttweaker.event.PlayerFillBucketEvent")
@ZenRegister
public class PlayerFillBucketEvent implements IEventCancelable {
    
    private final IPlayer player;
    private final IWorld world;
    private final int x;
    private final int y;
    private final int z;
    private boolean canceled;
    private IItemStack result;
    
    public PlayerFillBucketEvent(IPlayer player, IWorld world, int x, int y, int z) {
        this.player = player;
        this.world = world;
        this.x = x;
        this.y = y;
        this.z = z;
        
        canceled = false;
        result = null;
    }
    
    @Override
    public void cancel() {
        canceled = true;
    }
    
    @Override
    public boolean isCanceled() {
        return canceled;
    }
    
    @ZenGetter("result")
    public IItemStack getResult() {
        return result;
    }
    
    /**
     * Sets the resulting item. Automatically processes the event, too.
     *
     * @param result resulting item
     */
    @ZenSetter("result")
    public void setResult(IItemStack result) {
        this.result = result;
    }
    
    @ZenGetter("player")
    public IPlayer getPlayer() {
        return player;
    }
    
    @ZenGetter("world")
    public IWorld getWorld() {
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
    
    @ZenGetter("block")
    public IBlock getBlock() {
        return world.getBlock(x, y, z);
    }
    
    @ZenGetter("dimension")
    public int getDimension() {
        return world.getDimension();
    }
}
