package crafttweaker.mc1120.events.handling;

import crafttweaker.api.entity.IEntity;
import crafttweaker.api.event.ExplosionDetonateEvent;
import crafttweaker.api.minecraft.CraftTweakerMC;
import crafttweaker.api.world.IBlockPos;
import java.util.List;
import java.util.stream.Collectors;
import net.minecraftforge.event.world.ExplosionEvent;

public class MCExplosionDetonateEvent extends MCExplosionEvent implements ExplosionDetonateEvent {
    private ExplosionEvent.Detonate event;
    private List<IEntity> entities = null;
    private List<IBlockPos> blocks = null;

    public MCExplosionDetonateEvent(ExplosionEvent.Detonate event) {
        super(event);
        this.event = event;
    }

    @Override
    public List<IEntity> getAffectedEntities() {
        if (entities == null) {
            entities = event.getAffectedEntities().stream()
                                .map(CraftTweakerMC::getIEntity)
                                .collect(Collectors.toList());
        }
        return entities;
    }

    @Override
    public List<IBlockPos> getAffectedPositions() {
        if (blocks == null) {
            blocks = event.getAffectedBlocks().stream()
                                .map(CraftTweakerMC::getIBlockPos)
                                .collect(Collectors.toList());
        }
        return blocks;
    }
}
