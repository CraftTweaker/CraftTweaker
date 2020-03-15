package crafttweaker.mc1120.events.handling;

import crafttweaker.api.event.BlockBreakEvent;
import crafttweaker.api.minecraft.CraftTweakerMC;
import crafttweaker.api.player.IPlayer;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.event.world.BlockEvent;

public class MCBlockBreakEvent extends MCBlockEvent implements BlockBreakEvent {

    private final BlockEvent.BreakEvent event;

    public MCBlockBreakEvent(BlockEvent.BreakEvent event) {
        super(event);
        this.event = event;
    }

    @Override
    public boolean getIsPlayer() {
        return event.getPlayer() instanceof EntityPlayerMP || event.getPlayer() instanceof EntityPlayerSP;
    }

    @Override
    public IPlayer getPlayer() {
        return CraftTweakerMC.getIPlayer(event.getPlayer());
    }

    @Override
    public int getExperience() {
        return event.getExpToDrop();
    }

    @Override
    public void setExperience(int experience) {
        event.setExpToDrop(experience);
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
