package crafttweaker.mc1120.events.handling;

import crafttweaker.api.block.*;
import crafttweaker.api.event.PlayerFillBucketEvent;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import crafttweaker.api.player.IPlayer;
import crafttweaker.api.world.*;
import crafttweaker.mc1120.CraftTweaker;
import net.minecraft.util.math.RayTraceResult;
import net.minecraftforge.event.entity.player.FillBucketEvent;
import stanhebben.zenscript.annotations.*;

/**
 * @author Stan
 */
public class MCPlayerFillBucketEvent implements PlayerFillBucketEvent {
    
    private final FillBucketEvent event;
    
    public MCPlayerFillBucketEvent(FillBucketEvent ev) {
        this.event = ev;
    }
    
    @Override
    public IItemStack getResult() {
        return CraftTweakerMC.getIItemStack(event.getFilledBucket());
    }
    
    /**
     * Sets the resulting item. Automatically processes the event, too.
     *
     * @param result resulting item
     */
    @Override
    public void setResult(IItemStack result) {
        event.setFilledBucket(CraftTweakerMC.getItemStack(result));
    }
    
    @Override
    public IItemStack getEmptyBucket() {
        return CraftTweakerMC.getIItemStack(event.getEmptyBucket());
    }
    
    @Override
    public IPlayer getPlayer() {
        return CraftTweakerMC.getIPlayer(event.getEntityPlayer());
    }
    
    @Override
    public IWorld getWorld() {
        return CraftTweakerMC.getIWorld(event.getWorld());
    }
    
    @Override
    public int getX() {
        return getPos().getX();
    }
    
    @Override
    public int getY() {
        return getPos().getY();
    }
    
    @Override
    public int getZ() {
        return getPos().getZ();
    }
    
    @Override
    public IBlock getBlock() {
        IBlockState blockState = getBlockState();
        return blockState == null ? null : blockState.getBlock();
    }
    
    public IBlockState getBlockState() {
        RayTraceResult target = event.getTarget();
        return target == null ? null : CraftTweakerMC.getBlockState(event.getWorld().getBlockState(target.getBlockPos()));
    }
    
    @Override
    public int getDimension() {
        return getWorld().getDimension();
    }
    
    @Override
    public IBlockPos getPos() {
        return CraftTweakerMC.getIBlockPos(event.getEntityPlayer().getPosition());
    }
    
    @Override
    public boolean isCanceled() {
        return event.isCanceled();
    }
    
    @Override
    public void cancel() {
        setCanceled(true);
    }
    
    @Override
    public void setCanceled(boolean canceled) {
        event.setCanceled(canceled);
    }
}
