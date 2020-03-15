package crafttweaker.mc1120.events.handling;

import crafttweaker.api.event.PlayerRightClickEmptyEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;

public class MCPlayerRightClickEmptyEvent extends MCPlayerInteractEvent implements PlayerRightClickEmptyEvent {
    private PlayerInteractEvent.RightClickEmpty event;

    public MCPlayerRightClickEmptyEvent(PlayerInteractEvent.RightClickEmpty event) {
        super(event);
        this.event = event;
    }
}
