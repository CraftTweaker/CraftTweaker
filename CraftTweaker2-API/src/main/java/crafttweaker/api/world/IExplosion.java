package crafttweaker.api.world;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.entity.IEntityLivingBase;
import crafttweaker.api.player.IPlayer;
import crafttweaker.api.world.IBlockPos;
import crafttweaker.api.world.IVector3d;
import crafttweaker.api.util.IPosition3f;
import java.util.List;
import java.util.Map;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenGetter;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("crafttweaker.world.IExplosion")
@ZenRegister
public interface IExplosion {

    @ZenGetter("placedBy")
    IEntityLivingBase getExplosivePlacedBy();

    @ZenGetter("position")
    IPosition3f getPosition();

    @ZenGetter("affectedBlockPositions")
    List<IBlockPos> getAffectedBlockPositions();

    @ZenMethod
    void clearAffectedBlockPositions();

    @ZenGetter("playerKnockbackMap")
    Map<IPlayer, IVector3d> getPlayerKnockbackMap();

    Object getInternal();

}