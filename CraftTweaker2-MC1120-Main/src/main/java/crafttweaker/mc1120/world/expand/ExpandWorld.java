package crafttweaker.mc1120.world.expand;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.entity.IEntity;
import crafttweaker.api.minecraft.CraftTweakerMC;
import crafttweaker.api.player.IPlayer;
import crafttweaker.api.util.IAxisAlignedBB;
import crafttweaker.api.world.IBlockPos;
import crafttweaker.api.world.IFacing;
import crafttweaker.api.world.IWorld;
import net.minecraft.entity.Entity;
import net.minecraft.entity.effect.EntityLightningBolt;
import stanhebben.zenscript.annotations.*;

@ZenExpansion("crafttweaker.world.IWorld")
@ZenRegister
public class ExpandWorld {
    
    @ZenMethodStatic
    public static IWorld getFromID(int id) {
        return CraftTweakerMC.getWorldByID(id);
    }

    @ZenMethod
    public static boolean extinguishFire(IWorld world, IPlayer player, IBlockPos pos, IFacing side) {
        return CraftTweakerMC.getWorld(world).extinguishFire(CraftTweakerMC.getPlayer(player), CraftTweakerMC.getBlockPos(pos), CraftTweakerMC.getFacing(side));
    }

    @ZenMethod
    public static boolean isSpawnChunk(IWorld world, int x, int z) {
        return CraftTweakerMC.getWorld(world).isSpawnChunk(x, z) ;
    }

    @ZenMethod
    @ZenGetter("seaLevel")
    public static int getSeaLevel(IWorld world) {
        return CraftTweakerMC.getWorld(world).getSeaLevel();
    }

    @ZenMethod
    public static IEntity createLightningBolt(IWorld world, double x, double y, double z, @Optional boolean effectOnly) {
        return CraftTweakerMC.getIEntity(new EntityLightningBolt(CraftTweakerMC.getWorld(world), x, y, z, effectOnly));
    }

    @ZenMethod
    public static boolean addWeatherEffect(IWorld world, IEntity entity) {
        return CraftTweakerMC.getWorld(world).addWeatherEffect(CraftTweakerMC.getEntity(entity));
    }

    @ZenMethod
    public static IEntity summonLightningBolt(IWorld world, double x, double y, double z, @Optional boolean effectOnly) {
        EntityLightningBolt bolt = new EntityLightningBolt(CraftTweakerMC.getWorld(world), x, y, z, effectOnly);
        CraftTweakerMC.getWorld(world).addWeatherEffect(bolt);
        return CraftTweakerMC.getIEntity(bolt);
    }

    @ZenMethod
    public static IEntity[] getEntitiesWithinAABB(IWorld world, IAxisAlignedBB aabb) {
        return CraftTweakerMC.getWorld(world).getEntitiesWithinAABB(Entity.class, CraftTweakerMC.getAxisAlignedBB(aabb)).stream().map(CraftTweakerMC::getIEntity).toArray(IEntity[]::new);
    }

    @ZenMethod
    public static IEntity[] getEntitiesWithinAABBExcludingEntity(IWorld world, IAxisAlignedBB aabb, IEntity entity) {
        return CraftTweakerMC.getWorld(world).getEntitiesWithinAABBExcludingEntity(CraftTweakerMC.getEntity(entity), CraftTweakerMC.getAxisAlignedBB(aabb)).stream().map(CraftTweakerMC::getIEntity).toArray(IEntity[]::new);
    }

    @ZenMethod
    public static IEntity findNearestEntityWithinAABB(IWorld world, IAxisAlignedBB aabb, IEntity closestTo) {
        return CraftTweakerMC.getIEntity(CraftTweakerMC.getWorld(world).findNearestEntityWithinAABB(Entity.class, CraftTweakerMC.getAxisAlignedBB(aabb), CraftTweakerMC.getEntity(closestTo)));
    }
}