package crafttweaker.mc1120.events.handling;

import crafttweaker.api.block.*;
import crafttweaker.api.event.PlayerUseHoeEvent;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import crafttweaker.api.player.IPlayer;
import crafttweaker.api.world.*;
import net.minecraftforge.event.entity.player.UseHoeEvent;
import net.minecraftforge.fml.common.eventhandler.Event;

/**
 * @author Stan
 */
public class MCPlayerUseHoeEvent implements PlayerUseHoeEvent {
    
    private final UseHoeEvent event;
    
    public MCPlayerUseHoeEvent(UseHoeEvent event) {
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
    public IPlayer getPlayer() {
        return CraftTweakerMC.getIPlayer(event.getEntityPlayer());
    }
    
    @Override
    public IItemStack getItem() {
        return CraftTweakerMC.getIItemStack(event.getCurrent());
    }
    
    @Override
    public IWorld getBlocks() {
        return CraftTweakerMC.getIWorld(event.getWorld());
    }
    
    @Override
    public int getDimension() {
        return getBlocks().getDimension();
    }
    
    @Override
    public IBlock getBlock() {
        return getBlockState().getBlock();
    }
    
    @Override
    public IBlockState getBlockState() {
        return CraftTweakerMC.getBlockState(event.getWorld().getBlockState(event.getPos()));
    }
    
    @Override
    public IBlockPos getPosition() {
        return CraftTweakerMC.getIBlockPos(event.getPos());
    }
    
    @Override
    public boolean isCanceled() {
        return event.isCanceled();
    }
    
    @Override
    public void setCanceled(boolean canceled) {
        event.setCanceled(canceled);
    }
}
