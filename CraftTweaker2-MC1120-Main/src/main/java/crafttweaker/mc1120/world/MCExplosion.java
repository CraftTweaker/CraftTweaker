package crafttweaker.mc1120.world;

import crafttweaker.api.entity.IEntityLivingBase;
import crafttweaker.api.player.IPlayer;
import crafttweaker.api.util.Position3f;
import crafttweaker.api.minecraft.CraftTweakerMC;
import crafttweaker.api.world.IBlockPos;
import crafttweaker.api.world.IExplosion;
import crafttweaker.api.world.IVector3d;
import crafttweaker.api.world.IWorld;
import crafttweaker.mc1120.entity.MCEntityLivingBase;
import crafttweaker.mc1120.player.MCPlayer;
import crafttweaker.mc1120.util.MCPosition3f;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Explosion;
import net.minecraftforge.event.ForgeEventFactory;

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
    public IWorld getWorld() {
        return CraftTweakerMC.getIWorld(explosion.world);
    }

    @Override
    public IEntityLivingBase getExplosivePlacedBy() {
        return new MCEntityLivingBase(explosion.getExplosivePlacedBy());
    }

    @Override
    public Position3f getPosition() {
        return new MCPosition3f((float) explosion.getPosition().x,
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
    public void setAffectedBlockPositions(List<IBlockPos> affectedBlockPositions) {
        explosion.affectedBlockPositions = affectedBlockPositions.stream()
                                                .filter(x -> x instanceof BlockPos)
                                                .map(x -> (BlockPos) x.getInternal())
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
    public boolean onExplosionStart(IWorld world) {
        return ForgeEventFactory.onExplosionStart(CraftTweakerMC.getWorld(world), explosion);
    }

    @Override
    public void doExplosionA() {
        explosion.doExplosionA();
    }

    @Override
    public void doExplosionB(boolean spawnParticles) {
        explosion.doExplosionB(spawnParticles);
    }

    @Override
    public Object getInternal() {
        return explosion;
    }
}