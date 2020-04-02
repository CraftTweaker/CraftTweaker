package crafttweaker.mc1120.events.handling;

import crafttweaker.api.event.PlayerRightClickItemEvent;
import net.minecraft.util.EnumActionResult;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;

public class MCPlayerRightClickItemEvent extends MCPlayerInteractEvent implements PlayerRightClickItemEvent {
    private PlayerInteractEvent.RightClickItem event;

    public MCPlayerRightClickItemEvent(PlayerInteractEvent.RightClickItem event) {
        super(event);
        this.event = event;
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
    public String getCancellationResult() {
        return event.getCancellationResult().name();
    }

    @Override
    public void setCancellationResult(String value) {
        event.setCancellationResult(EnumActionResult.valueOf(value));
    }
}
