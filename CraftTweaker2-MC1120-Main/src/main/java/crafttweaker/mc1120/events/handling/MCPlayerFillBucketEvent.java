package crafttweaker.mc1120.events.handling;

import crafttweaker.api.block.IBlock;
import crafttweaker.api.block.IBlockState;
import crafttweaker.api.event.PlayerFillBucketEvent;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import crafttweaker.api.player.IPlayer;
import crafttweaker.api.world.IBlockPos;
import crafttweaker.api.world.IRayTraceResult;
import crafttweaker.api.world.IWorld;
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
    public IWorld getWorld() {
        return CraftTweakerMC.getIWorld(event.getWorld());
    }

    @Override
    public IBlock getBlock() {
        IBlockState blockState = getBlockState();
        return blockState == null ? null : blockState.getBlock();
    }

    @Override
    public IBlockState getBlockState() {
        RayTraceResult target = event.getTarget();
        return target == null ? null : CraftTweakerMC.getBlockState(event.getWorld().getBlockState(target.getBlockPos()));
    }

    @Override
    public int getDimension() {
        return getWorld().getDimension();
    }

    @Override
    public IBlockPos getPosition() {
        RayTraceResult target = event.getTarget();
        return CraftTweakerMC.getIBlockPos(target == null ? event.getEntityPlayer().getPosition() : target.getBlockPos());
    }

    @Override
    public IRayTraceResult getRayTraceResult() {
        return CraftTweakerMC.getIRayTraceResult(event.getTarget());
    }

    @Override
    public boolean isCanceled() {
        return event.isCanceled();
    }

    @Override
    public void setCanceled(boolean canceled) {
        event.setCanceled(canceled);
    }

    @Override
    public void process() {
        event.setResult(Event.Result.ALLOW);
    }

    @Override
    public boolean isProcessed() {
        return event.getResult() == Event.Result.ALLOW;
    }

    @Override
    public IPlayer getPlayer() {
        return CraftTweakerMC.getIPlayer(event.getEntityPlayer());
    }
}
