package crafttweaker.mc1120.events.handling;

import crafttweaker.api.event.SaplingGrowTreeEvent;
import crafttweaker.api.minecraft.CraftTweakerMC;
import crafttweaker.api.world.IBlockPos;
import crafttweaker.api.world.IWorld;
import net.minecraftforge.fml.common.eventhandler.Event;

public class MCSaplingGrowTreeEvent implements SaplingGrowTreeEvent {
  private net.minecraftforge.event.terraingen.SaplingGrowTreeEvent event;

  public MCSaplingGrowTreeEvent(net.minecraftforge.event.terraingen.SaplingGrowTreeEvent event) {
    this.event = event;
  }

  @Override
  public IWorld getWorld() {
    return CraftTweakerMC.getIWorld(event.getWorld());
  }

  @Override
  public boolean isDenied() {
    return event.getResult() == Event.Result.DENY;
  }

  @Override
  public boolean isDefault() {
    return event.getResult() == Event.Result.DEFAULT;
  }

  @Override
  public boolean isAllowed() {
    return event.getResult() == Event.Result.ALLOW;
  }

  @Override
  public void setDenied() {
    event.setResult(Event.Result.DENY);
  }

  @Override
  public void setDefault() {
    event.setResult(Event.Result.DEFAULT);
  }

  @Override
  public void setAllowed() {
    event.setResult(Event.Result.ALLOW);
  }

  @Override
  public IBlockPos getPosition() {
    return CraftTweakerMC.getIBlockPos(event.getPos());
  }
}
