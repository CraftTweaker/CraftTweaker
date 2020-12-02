package crafttweaker.mc1120.entity.expand;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.entity.IEntity;
import crafttweaker.api.minecraft.CraftTweakerMC;
import crafttweaker.api.util.IAxisAlignedBB;
import net.minecraft.entity.Entity;
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
    public String getUUID(IEntity internal) {
        return getInternal(internal).getCachedUniqueIdString();
    }

    @ZenGetter("boundingBox")
    public IAxisAlignedBB getBoundingBox(IEntity internal) {
        return CraftTweakerMC.getIAxisAlignedBB(getInternal(internal).getEntityBoundingBox());
    }

    @ZenSetter("boundingBox")
    public void setBoundingBox(IEntity internal, IAxisAlignedBB aabb) {
        getInternal(internal).setEntityBoundingBox(CraftTweakerMC.getAxisAlignedBB(aabb));
    }

    @ZenGetter("stepHeight")
    public float getStepHeight(IEntity internal) {
        return getInternal(internal).stepHeight;
    }

    @ZenSetter("stepHeight")
    public void setStepHeight(IEntity internal, float stepHeight) {
        getInternal(internal).getCachedUniqueIdString();
    }

    @ZenMethod
    public void removeFromWorld(IEntity internal) {
        Entity entity = getInternal(internal);
        entity.world.removeEntity(entity);
    }
}