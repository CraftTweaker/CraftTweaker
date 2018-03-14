package crafttweaker.api.event;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.block.*;
import crafttweaker.api.player.IPlayer;
import crafttweaker.api.world.*;
import stanhebben.zenscript.annotations.*;

/**
 * @author Stan
 */
@ZenClass("crafttweaker.event.PlayerBonemealEvent")
@ZenRegister
public class PlayerBonemealEvent implements IEventCancelable {
    
    private final IPlayer player;
    private final IWorld world;
    private final IBlockPos blockPos;
    private boolean canceled;
    private boolean processed;
    
    public PlayerBonemealEvent(IPlayer player, IWorld world, IBlockPos blockPos) {
        this.player = player;
        this.world = world;
        this.blockPos = blockPos;
        
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
    public IPlayer getPlayer() {
        return player;
    }
    
    @ZenGetter("world")
    public IWorld getBlocks() {
        return world;
    }
    
    @ZenGetter("x")
    public int getX() {
        return blockPos.getX();
    }
    
    @ZenGetter("y")
    public int getY() {
        return blockPos.getY();
    }
    
    @ZenGetter("z")
    public int getZ() {
        return blockPos.getZ();
    }
    
    @ZenGetter("block")
    public IBlock getBlock() {
        return world.getBlock(blockPos);
    }
    
    @ZenGetter("blockState")
    public IBlockState getBlockState(){
        return world.getBlockState(blockPos);
    }
    
    @ZenGetter("blockPos")
    public IBlockPos getBlockPos() {
        return blockPos;
    }
    
    @ZenGetter("dimension")
    public int getDimension() {
        return world.getDimension();
    }
}
