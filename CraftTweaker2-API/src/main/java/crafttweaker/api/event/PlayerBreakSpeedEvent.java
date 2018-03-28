package crafttweaker.api.event;


import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.block.*;
import stanhebben.zenscript.annotations.*;

@ZenClass("crafttweaker.event.PlayerBreakSpeedEvent")
@ZenRegister
public interface PlayerBreakSpeedEvent extends IPlayerEvent, IEventPositionable, IEventCancelable {
    
    @ZenGetter("blockState")
    IBlockState getBlockState();
    
    @ZenGetter("block")
    default IBlock getBlock() {
        return getBlockState().getBlock();
    }
    
    @ZenGetter("originalSpeed")
    float getOriginalSpeed();
    
    @ZenGetter("newSpeed")
    float getNewSpeed();
    
    @ZenSetter("newSpeed")
    void setNewSpeed(float newSpeed);
    
    
}
