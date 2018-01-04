package crafttweaker.api.event;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.block.IBlock;
import crafttweaker.api.player.IEntityPlayer;
import crafttweaker.api.world.*;
import stanhebben.zenscript.annotations.*;

/**
 * @author Stan
 */
@ZenClass("crafttweaker.event.PlayerInteractEvent")
@ZenRegister
public class PlayerInteractEvent implements IEventCancelable {
    
    private final IEntityPlayer player;
    private final IWorld world;
    private final int x;
    private final int y;
    private final int z;
    
    private boolean canceled;
    private boolean useBlock;
    private boolean useItem;
    
    public PlayerInteractEvent(IEntityPlayer player, IWorld blocks, int x, int y, int z) {
        this.player = player;
        this.world = blocks;
        this.x = x;
        this.y = y;
        this.z = z;
        
        canceled = false;
        useBlock = false;
        useItem = false;
    }
    
    @Override
    public void cancel() {
        canceled = true;
    }
    
    @ZenMethod
    public void useBlock() {
        useBlock = true;
    }
    
    @ZenMethod
    public void useItem() {
        useItem = true;
    }
    
    @Override
    public boolean isCanceled() {
        return canceled;
    }
    
    @ZenGetter("usingBlock")
    public boolean isUsingBlock() {
        return useBlock;
    }
    
    @ZenGetter("usingItem")
    public boolean isUsingItem() {
        return useItem;
    }
    
    @ZenGetter("player")
    public IEntityPlayer getPlayer() {
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
