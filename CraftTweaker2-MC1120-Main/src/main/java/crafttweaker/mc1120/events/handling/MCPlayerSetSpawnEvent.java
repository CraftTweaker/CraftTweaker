package crafttweaker.mc1120.events.handling;

import crafttweaker.api.event.PlayerSetSpawnEvent;
import crafttweaker.api.minecraft.CraftTweakerMC;
import crafttweaker.api.player.IPlayer;
import crafttweaker.api.world.IBlockPos;

public class MCPlayerSetSpawnEvent implements PlayerSetSpawnEvent {

    private final net.minecraftforge.event.entity.player.PlayerSetSpawnEvent event;

    public MCPlayerSetSpawnEvent(net.minecraftforge.event.entity.player.PlayerSetSpawnEvent event) {
        this.event = event;
    }

    @Override
    public IBlockPos getNewSpawn() {
        return CraftTweakerMC.getIBlockPos(event.getNewSpawn());
    }

    @Override
    public boolean getIsForced() {
        return event.isForced();
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
