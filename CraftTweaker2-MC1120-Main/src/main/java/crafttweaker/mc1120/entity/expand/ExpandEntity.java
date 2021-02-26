package crafttweaker.mc1120.entity.expand;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.entity.IEntity;
import crafttweaker.api.game.ITeam;
import crafttweaker.api.minecraft.CraftTweakerMC;
import crafttweaker.api.util.IAxisAlignedBB;
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
}
