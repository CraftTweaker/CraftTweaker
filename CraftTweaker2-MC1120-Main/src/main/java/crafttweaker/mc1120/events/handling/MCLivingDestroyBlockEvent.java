package crafttweaker.mc1120.events.handling;

import crafttweaker.api.block.IBlockState;
import crafttweaker.api.entity.IEntityLivingBase;
import crafttweaker.api.event.LivingDestroyBlockEvent;
import crafttweaker.api.minecraft.CraftTweakerMC;
import crafttweaker.api.world.IBlockPos;

public class MCLivingDestroyBlockEvent implements LivingDestroyBlockEvent {
  private net.minecraftforge.event.entity.living.LivingDestroyBlockEvent event;

  public MCLivingDestroyBlockEvent(net.minecraftforge.event.entity.living.LivingDestroyBlockEvent event) {
    this.event = event;
  }

  @Override
  public IBlockState getState() {
    return CraftTweakerMC.getBlockState(event.getState());
  }

  @Override
  public boolean isCanceled() {
    return event.isCanceled();
  }

  @Override
  public void setCanceled(boolean canceled) {
    event.setCanceled(canceled);
  }

  @Override
  public IBlockPos getPosition() {
    return CraftTweakerMC.getIBlockPos(event.getPos());
  }

  @Override
  public IEntityLivingBase getEntityLivingBase() {
    return CraftTweakerMC.getIEntityLivingBase(event.getEntityLiving());
  }
}
