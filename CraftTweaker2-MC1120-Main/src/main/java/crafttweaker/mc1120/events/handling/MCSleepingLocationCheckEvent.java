package crafttweaker.mc1120.events.handling;

import crafttweaker.api.event.SleepingLocationCheckEvent;
import crafttweaker.api.minecraft.CraftTweakerMC;
import crafttweaker.api.player.IPlayer;
import crafttweaker.api.world.IBlockPos;
import net.minecraftforge.fml.common.eventhandler.Event;

public class MCSleepingLocationCheckEvent implements SleepingLocationCheckEvent {
  private net.minecraftforge.event.entity.player.SleepingLocationCheckEvent event;

  public MCSleepingLocationCheckEvent(net.minecraftforge.event.entity.player.SleepingLocationCheckEvent event) {
    this.event = event;
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
    return CraftTweakerMC.getIBlockPos(event.getSleepingLocation());
  }

  @Override
  public IPlayer getPlayer() {
    return CraftTweakerMC.getIPlayer(event.getEntityPlayer());
  }
}
