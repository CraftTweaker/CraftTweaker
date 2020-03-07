package crafttweaker.mc1120.events.handling;

import crafttweaker.api.entity.IEntity;
import crafttweaker.api.event.ExplosionDetonateEvent;
import crafttweaker.api.minecraft.CraftTweakerMC;
import crafttweaker.api.world.IBlockPos;
import net.minecraftforge.event.world.ExplosionEvent;

public class MCExplosionDetonateEvent extends MCExplosionEvent implements ExplosionDetonateEvent {
  private ExplosionEvent.Detonate event;
  private IEntity[] entities = null;
  private IBlockPos[] blocks = null;

  public MCExplosionDetonateEvent(ExplosionEvent.Detonate event) {
    super(event);
    this.event = event;
  }

  @Override
  public IEntity[] getAffectedEntities() {
    if (entities == null) {
      entities = event.getAffectedEntities().stream().map(CraftTweakerMC::getIEntity).toArray(IEntity[]::new);
    }
    return entities;
  }

  @Override
  public IBlockPos[] getAffectedPositions() {
    if (blocks == null) {
      blocks = event.getAffectedBlocks().stream().map(CraftTweakerMC::getIBlockPos).toArray(IBlockPos[]::new);
    }
    return blocks;
  }
}
