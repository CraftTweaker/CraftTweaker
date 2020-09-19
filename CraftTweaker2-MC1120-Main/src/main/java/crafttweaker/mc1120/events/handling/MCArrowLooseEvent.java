package crafttweaker.mc1120.events.handling;

import crafttweaker.api.event.ArrowLooseEvent;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import crafttweaker.api.player.IPlayer;
import crafttweaker.api.world.IWorld;
import stanhebben.zenscript.annotations.ZenGetter;

public class MCArrowLooseEvent implements ArrowLooseEvent {
    private final net.minecraftforge.event.entity.player.ArrowLooseEvent event;

    public MCArrowLooseEvent(net.minecraftforge.event.entity.player.ArrowLooseEvent event) {
        this.event = event;
    }

    @Override
    public IItemStack getBow() {
        return CraftTweakerMC.getIItemStack(event.getBow());
    }
    
	@Override
	public void setCharge(int charge) {
		event.setCharge(charge);
	}
	
	@Override
	public int getCharge() {
		return event.getCharge();
	}

	@Override
	public IWorld getWorld() {
		return CraftTweakerMC.getIWorld(event.getWorld());
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