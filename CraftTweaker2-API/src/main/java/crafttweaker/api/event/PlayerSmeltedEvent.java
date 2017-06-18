package crafttweaker.api.event;

import crafttweaker.api.item.IItemStack;
import crafttweaker.api.player.IPlayer;
import stanhebben.zenscript.annotations.ZenGetter;

/**
 * @author Stan
 */
public class PlayerSmeltedEvent {
    
    private final IPlayer player;
    private final IItemStack output;
    
    public PlayerSmeltedEvent(IPlayer player, IItemStack output) {
        this.player = player;
        this.output = output;
    }
    
    @ZenGetter("player")
    public IPlayer getPlayer() {
        return player;
    }
    
    @ZenGetter("output")
    public IItemStack getOutput() {
        return output;
    }
}
