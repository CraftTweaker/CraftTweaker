package crafttweaker.mc1120.events.handling;

import crafttweaker.api.event.PlayerVisibilityEvent;
import crafttweaker.api.minecraft.CraftTweakerMC;
import crafttweaker.api.player.IPlayer;
import net.minecraftforge.event.entity.player.PlayerEvent;

public class MCPlayerVisibilityEvent implements PlayerVisibilityEvent {
    private PlayerEvent.Visibility event;

    public MCPlayerVisibilityEvent(PlayerEvent.Visibility event) {
        this.event = event;
    }

    @Override
    public void modifyVisibility(double modifier) {
        event.modifyVisibility(modifier);
    }

    @Override
    public double getModifier() {
        return event.getVisibilityModifier();
    }

    @Override
    public IPlayer getPlayer() {
        return CraftTweakerMC.getIPlayer(event.getEntityPlayer());
    }
}
