package crafttweaker.mc1120.events.handling;

import crafttweaker.api.event.PlayerLeftClickBlockEvent;
import crafttweaker.api.event.PlayerRightClickBlockEvent;
import crafttweaker.api.minecraft.CraftTweakerMC;
import crafttweaker.api.world.IVector3d;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.Event;

public class MCPlayerLeftClickBlockEvent extends MCPlayerInteractEvent implements PlayerLeftClickBlockEvent {

    private final PlayerInteractEvent.LeftClickBlock event;

    public MCPlayerLeftClickBlockEvent(PlayerInteractEvent.LeftClickBlock event) {
        super(event);
        this.event = event;
    }
    
    @Override
    public IVector3d getHitVector() {
        return CraftTweakerMC.getIVector3d(event.getHitVec());
    }
    
    @Override
    public String getUseBlock() {
        return event.getUseBlock().name();
    }
    
    @Override
    public void setUseBlock(String useBlock) {
        event.setUseBlock(Event.Result.valueOf(useBlock));
    }
    
    @Override
    public String getUseItem() {
        return event.getUseItem().name();
    }
    
    @Override
    public void setUseItem(String useItem) {
        event.setUseItem(Event.Result.valueOf(useItem));
    }
}
