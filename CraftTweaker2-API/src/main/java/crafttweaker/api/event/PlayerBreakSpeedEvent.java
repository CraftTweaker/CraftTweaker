package crafttweaker.api.event;


import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.block.IBlock;
import crafttweaker.api.block.IBlockState;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenGetter;
import stanhebben.zenscript.annotations.ZenSetter;

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
