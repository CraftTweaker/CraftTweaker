package crafttweaker.mc1120.events.handling;

import crafttweaker.api.block.IBlock;
import crafttweaker.api.block.IBlockState;
import crafttweaker.api.event.PlayerBonemealEvent;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import crafttweaker.api.player.IPlayer;
import crafttweaker.api.world.IBlockPos;
import crafttweaker.api.world.IWorld;
import net.minecraftforge.event.entity.player.BonemealEvent;
import net.minecraftforge.fml.common.eventhandler.Event;

/**
 * @author Stan
 */
public class MCPlayerBonemealEvent implements PlayerBonemealEvent {

    private final BonemealEvent event;

    public MCPlayerBonemealEvent(BonemealEvent event) {
        this.event = event;
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
    public IWorld getWorld() {
        return CraftTweakerMC.getIWorld(event.getWorld());
    }

    @Override
    public IBlock getBlock() {
        return getBlockState().getBlock();
    }

    @Override
    public IBlockState getBlockState() {
        return CraftTweakerMC.getBlockState(event.getBlock());
    }

    @Override
    public IBlockPos getPosition() {
        return CraftTweakerMC.getIBlockPos(event.getPos());
    }

    @Override
    public int getDimension() {
        return getWorld().getDimension();
    }

    @Override
    public IItemStack getItem() {
        return CraftTweakerMC.getIItemStack(event.getStack());
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
    public IPlayer getPlayer() {
        return CraftTweakerMC.getIPlayer(event.getEntityPlayer());
    }
}
