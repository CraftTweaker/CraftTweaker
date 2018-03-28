package crafttweaker.mc1120.events.handling;

import crafttweaker.api.block.IBlockState;
import crafttweaker.api.event.PlayerBreakSpeedEvent;
import crafttweaker.api.minecraft.CraftTweakerMC;
import crafttweaker.api.player.IPlayer;
import crafttweaker.api.world.IBlockPos;
import net.minecraftforge.event.entity.player.PlayerEvent;

public class MCPlayerBreakSpeedEvent implements PlayerBreakSpeedEvent {
    
    private final PlayerEvent.BreakSpeed event;
    
    public MCPlayerBreakSpeedEvent(PlayerEvent.BreakSpeed event) {
        this.event = event;
    }
    
    @Override
    public IBlockState getBlockState() {
        return CraftTweakerMC.getBlockState(event.getState());
    }
    
    @Override
    public float getOriginalSpeed() {
        return event.getOriginalSpeed();
    }
    
    @Override
    public float getNewSpeed() {
        return event.getNewSpeed();
    }
    
    @Override
    public void setNewSpeed(float newSpeed) {
        event.setNewSpeed(newSpeed);
    }
    
    @Override
    public IBlockPos getPosition() {
        return CraftTweakerMC.getIBlockPos(event.getPos());
    }
    
    @Override
    public IPlayer getPlayer() {
        return CraftTweakerMC.getIPlayer(event.getEntityPlayer());
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
