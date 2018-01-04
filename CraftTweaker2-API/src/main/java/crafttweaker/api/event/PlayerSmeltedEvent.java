package crafttweaker.api.event;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.player.IEntityPlayer;
import stanhebben.zenscript.annotations.*;

/**
 * @author Stan
 */
@ZenRegister
@ZenClass("crafttweaker.event.PlayerSmeltedEvent")
public class PlayerSmeltedEvent {
    
    private final IEntityPlayer player;
    private final IItemStack output;
    
    public PlayerSmeltedEvent(IEntityPlayer player, IItemStack output) {
        this.player = player;
        this.output = output;
    }
    
    @ZenGetter("player")
    public IEntityPlayer getPlayer() {
        return player;
    }
    
    @ZenGetter("output")
    public IItemStack getOutput() {
        return output;
    }
}
