package crafttweaker.mc1120.events.handling;

import crafttweaker.api.block.IBlockState;
import crafttweaker.api.event.CropGrowEventPost;
import crafttweaker.api.event.CropGrowEventPre;
import crafttweaker.api.minecraft.CraftTweakerMC;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.eventhandler.Event;

public class MCCropGrowEventPost extends MCBlockEvent implements CropGrowEventPost {
  private BlockEvent.CropGrowEvent.Post event;

  public MCCropGrowEventPost(BlockEvent.CropGrowEvent.Post event) {
    super(event);
    this.event = event;
  }

  @Override
  public IBlockState getOriginalBlockState() {
    return CraftTweakerMC.getBlockState(event.getOriginalState());
  }
}
