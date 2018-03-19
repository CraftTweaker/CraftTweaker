package crafttweaker.mc1120.events.handling;

import crafttweaker.api.block.*;
import crafttweaker.api.entity.IEntity;
import crafttweaker.api.event.PlayerFillBucketEvent;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import crafttweaker.api.player.IPlayer;
import crafttweaker.api.world.*;
import net.minecraft.util.math.RayTraceResult;
import net.minecraftforge.event.entity.player.FillBucketEvent;
import net.minecraftforge.fml.common.eventhandler.Event;

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
        event.setResult(Event.Result.ALLOW);
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
        RayTraceResult target = event.getTarget();
        return CraftTweakerMC.getIBlockPos(target == null ? event.getEntityPlayer().getPosition() : target.getBlockPos());
    }
    
    @Override
    public IEntity getHitEntity() {
        RayTraceResult target = event.getTarget();
        return target == null ? null : CraftTweakerMC.getIEntity(target.entityHit);
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
