package crafttweakerutils.world;

import crafttweaker.api.entity.IEntityLivingBase;
import crafttweaker.api.world.IBlockPos;
import crafttweaker.api.world.IVector3d;
import crafttweaker.mc1120.entity.MCEntityLivingBase;
import crafttweaker.mc1120.player.MCPlayer;
import crafttweaker.mc1120.world.MCBlockPos;
import crafttweaker.mc1120.world.MCVector3d;
import net.minecraft.world.Explosion;

import java.util.AbstractMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MCExplosion implements IExplosion {

    private Explosion explosion;

    public MCExplosion(Explosion explosion) {
        this.explosion = explosion;
    }

    @Override
    public IEntityLivingBase getExplosivePlacedBy() {
        return new MCEntityLivingBase(explosion.getExplosivePlacedBy());
    }

    @Override
    public IPosition3f getPosition() {
        return new MCVector3d((float) explosion.getPosition().x,
                              (float) explosion.getPosition().y,
                              (float) explosion.getPosition().z);
    }

    @Override
    public List<IBlockPos> getAffectedBlockPositions() {
        return explosion.getAffectedBlockPositions().stream()
                    .map(MCBlockPos::new)
                    .collect(Collectors.toList());
    }

    @Override
    public void clearAffectedBlockPositions() {
        explosion.clearAffectedBlockPositions();
    }

    @Override
    public Map<IPlayer, IVector3d> getPlayerKnockbackMap() {
        return explosion.getPlayerKnockbackMap().entrySet()
                    .stream()
                    .map(e -> new AbstractMap.SimpleEntry<>(
                        new MCPlayer(e.getKey()),
                        new MCVector3d(e.getValue())
                    ))
                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    @Override
    public Object getInternal() {
        return explosion;
    }
}