package crafttweaker.mc1120.events.handling;

import crafttweaker.api.event.PlayerSleepInBedEvent;
import crafttweaker.api.minecraft.CraftTweakerMC;
import crafttweaker.api.player.IPlayer;
import crafttweaker.api.world.IBlockPos;
import net.minecraft.entity.player.EntityPlayer;

public class MCPlayerSleepInBedEvent implements PlayerSleepInBedEvent {


    private final net.minecraftforge.event.entity.player.PlayerSleepInBedEvent event;

    public MCPlayerSleepInBedEvent(net.minecraftforge.event.entity.player.PlayerSleepInBedEvent event) {
        this.event = event;
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
    public String getResult() {
        return event.getResultStatus().name();
    }
    
    @Override
    public void setResult(String result) {
        event.setResult(EntityPlayer.SleepResult.valueOf(result));
    }
}
