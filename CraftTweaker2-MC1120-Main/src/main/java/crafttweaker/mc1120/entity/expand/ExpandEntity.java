package crafttweaker.mc1120.entity.expand;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.entity.IEntity;
import crafttweaker.api.game.ITeam;
import crafttweaker.api.minecraft.CraftTweakerMC;
import crafttweaker.api.util.IAxisAlignedBB;
import crafttweaker.api.world.IBlockPos;
import crafttweaker.api.world.IFacing;
import crafttweaker.api.world.IVector3d;
import net.minecraft.entity.Entity;
import net.minecraft.scoreboard.Scoreboard;
import stanhebben.zenscript.annotations.ZenExpansion;
import stanhebben.zenscript.annotations.ZenGetter;
import stanhebben.zenscript.annotations.ZenMethod;
import stanhebben.zenscript.annotations.ZenSetter;

@ZenExpansion("crafttweaker.entity.IEntity")
@ZenRegister
public class ExpandEntity {
    private static Entity getInternal(IEntity expanded) {
        return CraftTweakerMC.getEntity(expanded);
    }

    @ZenGetter("uuid")
    @ZenMethod
    public static String getUUID(IEntity internal) {
        return getInternal(internal).getCachedUniqueIdString();
    }

    @ZenGetter("boundingBox")
    @ZenMethod
    public static IAxisAlignedBB getBoundingBox(IEntity internal) {
        return CraftTweakerMC.getIAxisAlignedBB(getInternal(internal).getEntityBoundingBox());
    }

    @ZenSetter("boundingBox")
    @ZenMethod
    public static void setBoundingBox(IEntity internal, IAxisAlignedBB aabb) {
        getInternal(internal).setEntityBoundingBox(CraftTweakerMC.getAxisAlignedBB(aabb));
    }

    @ZenGetter("stepHeight")
    @ZenMethod
    public static float getStepHeight(IEntity internal) {
        return getInternal(internal).stepHeight;
    }

    @ZenSetter("stepHeight")
    @ZenMethod
    public static void setStepHeight(IEntity internal, float stepHeight) {
        getInternal(internal).stepHeight = stepHeight;
    }
    
    @ZenSetter("team")
    @ZenMethod
    public static void setTeam(IEntity internal, ITeam team) {
        Scoreboard sb = getInternal(internal).world.getScoreboard();
        sb.removePlayerFromTeams(getUUID(internal));
        
        String teamName = CraftTweakerMC.getTeam(team).getName();
        if (sb.getTeam(teamName) == null)
            sb.createTeam(teamName);

        sb.addPlayerToTeam(getUUID(internal), teamName);
    }

    @ZenMethod
    public static void removeFromWorld(IEntity internal) {
        Entity entity = getInternal(internal);
        entity.world.removeEntity(entity);
    }

    @ZenMethod
    public static void doWaterSplashEffect(IEntity internal) {
        getInternal(internal).doWaterSplashEffect();
    }

    @ZenGetter("updateBlocked")
    @ZenMethod
    public static boolean getUpdateBlocked(IEntity internal) {
        return getInternal(internal).updateBlocked;
    }

    @ZenSetter("updateBlocked")
    @ZenMethod
    public static void setUpdateBlocked(IEntity internal, boolean updateBlocked) {
        getInternal(internal).updateBlocked = updateBlocked;
    }

    @ZenGetter("inPortal")
    @ZenMethod
    public static boolean getInPortal(IEntity internal) {
        return getInternal(internal).inPortal;
    }

    @ZenSetter("inPortal")
    @ZenMethod
    public static void setInPortal(IEntity internal, boolean inPortal) {
        getInternal(internal).inPortal = inPortal;
    }

    @ZenGetter("portalCounter")
    @ZenMethod
    public static int getPortalCounter(IEntity internal) {
        return getInternal(internal).portalCounter;
    }

    @ZenSetter("portalCounter")
    @ZenMethod
    public static void setPortalCounter(IEntity internal, int portalCounter) {
        getInternal(internal).portalCounter = portalCounter;
    }

    @ZenGetter("lastPortalVec")
    @ZenMethod
    public static IVector3d getLastPortalVec(IEntity internal) {
        return CraftTweakerMC.getIVector3d(getInternal(internal).lastPortalVec);
    }

    @ZenSetter("lastPortalVec")
    @ZenMethod
    public static void setLastPortalVec(IEntity internal, IVector3d lastPortalVec) {
        getInternal(internal).lastPortalVec = CraftTweakerMC.getVec3d(lastPortalVec);
    }

    @ZenGetter("lastPortalPos")
    @ZenMethod
    public static IBlockPos getLastPortalPos(IEntity internal) {
        return CraftTweakerMC.getIBlockPos(getInternal(internal).lastPortalPos);
    }

    @ZenSetter("lastPortalPos")
    @ZenMethod
    public static void setLastPortalPos(IEntity internal, IBlockPos lastPortalPos) {
        getInternal(internal).lastPortalPos = CraftTweakerMC.getBlockPos(lastPortalPos);
    }

    @ZenGetter("lastPortalDirection")
    @ZenMethod
    public static IFacing getLastPortalDirection(IEntity internal) {
        return CraftTweakerMC.getIFacing(getInternal(internal).teleportDirection);
    }

    @ZenSetter("lastPortalDirection")
    @ZenMethod
    public static void setLastPortalDirection(IEntity internal, IFacing teleportDirection) {
        getInternal(internal).teleportDirection = CraftTweakerMC.getFacing(teleportDirection);
    }

    @ZenGetter("horizontalFacing")
    @ZenMethod
    public static IFacing getHorizontalFacing(IEntity internal) {
        return CraftTweakerMC.getIFacing(getInternal(internal).getHorizontalFacing());
    }
}
