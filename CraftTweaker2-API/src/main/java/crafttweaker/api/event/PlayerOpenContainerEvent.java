package crafttweaker.api.event;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.container.IContainer;
import crafttweaker.api.player.IPlayer;
import stanhebben.zenscript.annotations.*;

/**
 * @author Stan
 */
@ZenClass("crafttweaker.event.PlayerOpenContainerEvent")
@ZenRegister
public class PlayerOpenContainerEvent implements IEventCancelable {
    
    private final IPlayer player;
    private final IContainer container;
    private boolean canceled;
    
    public PlayerOpenContainerEvent(IPlayer player, IContainer container) {
        this.player = player;
        this.container = container;
        
        canceled = false;
    }
    
    @Override
    public void cancel() {
        canceled = true;
    }
    
    @Override
    public boolean isCanceled() {
        return canceled;
    }
    
    @ZenGetter("player")
    public IPlayer getPlayer() {
        return player;
    }
    
    @ZenGetter("container")
    public IContainer getContainer() {
        return container;
    }
}
