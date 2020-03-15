package crafttweaker.mc1120.events.handling;

import crafttweaker.api.block.IBlockState;
import crafttweaker.api.event.CropGrowPostEvent;
import crafttweaker.api.minecraft.CraftTweakerMC;
import net.minecraftforge.event.world.BlockEvent;

public class MCCropGrowPostEvent extends MCBlockEvent implements CropGrowPostEvent {
    private BlockEvent.CropGrowEvent.Post event;

    public MCCropGrowPostEvent(BlockEvent.CropGrowEvent.Post event) {
        super(event);
        this.event = event;
    }

    @Override
    public IBlockState getOriginalBlockState() {
        return CraftTweakerMC.getBlockState(event.getOriginalState());
    }
}
