package crafttweaker.api.event;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.block.IBlock;
import crafttweaker.api.block.IBlockState;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenGetter;

@ZenClass("crafttweaker.event.CropGrowPostEvent")
@ZenRegister
public interface CropGrowPostEvent extends IBlockEvent {
  @ZenGetter("originalBlockState")
  IBlockState getOriginalBlockState();

  @ZenGetter("originalBlock")
  default IBlock getOriginalBlock () {
    return getOriginalBlockState().getBlock();
  }
}
