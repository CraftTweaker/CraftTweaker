package crafttweaker.mc1120.events.handling;

import crafttweaker.api.event.ArrowNockEvent;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import crafttweaker.api.player.IPlayer;
import net.minecraftforge.fml.common.eventhandler.Event;

public class MCArrowNockEvent implements ArrowNockEvent {
    private final net.minecraftforge.event.entity.player.ArrowNockEvent event;

    public MCArrowNockEvent(net.minecraftforge.event.entity.player.ArrowNockEvent event) {
        this.event = event;
    }

    @Override
    public IItemStack getBow() {
        return CraftTweakerMC.getIItemStack(event.getBow());
    }

    @Override
    public String getHand() {
        return event.getHand().name();
    }

	@Override
	public IPlayer getPlayer() {
		return CraftTweakerMC.getIPlayer(event.getEntityPlayer());
	}

    @Override
    public String getResult() {
        return String.valueOf(event.getResult());
    }

    @Override
    public void setDenied() {
        event.setResult(Event.Result.DENY);
    }

    @Override
    public void setDefault() {
        event.setResult(Event.Result.DEFAULT);
    }

    @Override
    public void setAllowed() {
        event.setResult(Event.Result.ALLOW);
    }
}