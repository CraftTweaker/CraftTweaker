package crafttweaker.mc1120.events.handling;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.event.PlayerSmeltedEvent;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.player.IPlayer;
import stanhebben.zenscript.annotations.*;

/**
 * @author Stan
 */
public class MCPlayerSmeltedEvent implements PlayerSmeltedEvent{
    
    private final IPlayer player;
    private final IItemStack output;
    
    public MCPlayerSmeltedEvent(IPlayer player, IItemStack output) {
        this.player = player;
        this.output = output;
    }
    
    @Override
    public IPlayer getPlayer() {
        return player;
    }
    
    @Override
    public IItemStack getOutput() {
        return output;
    }
}
