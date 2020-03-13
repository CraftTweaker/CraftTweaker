package crafttweaker.api.event;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.block.IBlock;
import crafttweaker.api.block.IBlockState;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenGetter;

@ZenClass("crafttweaker.event.CropGrowEventPost")
@ZenRegister
public interface CropGrowPostEvent extends IBlockEvent {
  @ZenGetter("originalBlockState")
  IBlockState getOriginalBlockState();

  @ZenGetter("getOriginalBlock")
  default IBlock getOriginalBlock () {
    return getOriginalBlockState().getBlock();
  }
}
