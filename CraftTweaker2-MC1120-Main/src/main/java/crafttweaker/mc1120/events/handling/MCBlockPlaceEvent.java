package crafttweaker.mc1120.events.handling;

import crafttweaker.api.block.IBlock;
import crafttweaker.api.block.IBlockState;
import crafttweaker.api.event.BlockPlaceEvent;
import crafttweaker.api.minecraft.CraftTweakerMC;
import crafttweaker.api.player.IPlayer;
import net.minecraftforge.event.world.BlockEvent;

@SuppressWarnings("deprecation")
public class MCBlockPlaceEvent extends MCBlockEvent implements BlockPlaceEvent {
    private BlockEvent.PlaceEvent event;

    public MCBlockPlaceEvent(BlockEvent.PlaceEvent event) {
        super(event);
        this.event = event;
    }

    @Override
    public IPlayer getPlayer() {
        return CraftTweakerMC.getIPlayer(event.getPlayer());
    }

    @Override
    public IBlockState getCurrent() {
        return CraftTweakerMC.getBlockState(event.getPlacedBlock());
    }

    @Override
    public IBlockState getPlacedAgainst() {
        return CraftTweakerMC.getBlockState(event.getPlacedAgainst());
    }

    @Override
    public IBlock getBlock() {
        return getBlockState().getBlock();
    }

    @Override
    public String getHand() {
        return String.valueOf(event.getHand());
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
