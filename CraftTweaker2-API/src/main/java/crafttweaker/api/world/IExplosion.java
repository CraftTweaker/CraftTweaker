package crafttweaker.api.world;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.entity.IEntityLivingBase;
import crafttweaker.api.player.IPlayer;
import crafttweaker.api.util.Position3f;
import java.util.List;
import java.util.Map;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenGetter;
import stanhebben.zenscript.annotations.ZenSetter;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("crafttweaker.world.IExplosion")
@ZenRegister
public interface IExplosion {

    @ZenGetter("world")
    IWorld getWorld();

    @ZenGetter("placedBy")
    IEntityLivingBase getExplosivePlacedBy();

    @ZenGetter("position")
    Position3f getPosition();

    @ZenGetter("affectedBlockPositions")
    List<IBlockPos> getAffectedBlockPositions();

    @ZenSetter("affectedBlockPositions")
    void setAffectedBlockPositions(List<IBlockPos> affectedBlockPositions);

    @ZenMethod
    void clearAffectedBlockPositions();

    @ZenGetter("playerKnockbackMap")
    Map<IPlayer, IVector3d> getPlayerKnockbackMap();

    @ZenMethod
    boolean onExplosionStart(IWorld world);

    @ZenMethod
    void doExplosionA();

    @ZenMethod
    void doExplosionB(boolean spawnParticles);

    Object getInternal();

}