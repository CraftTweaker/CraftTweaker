package minetweaker.api.entity;

import stanhebben.zenscript.annotations.*;

/**
 * Represents an xp orb.
 *
 * @author Stan Hebben
 */
@ZenClass("minetweaker.entity.IEntityXp")
public interface IEntityXp extends IEntity {

    /**
     * Gets the amount of xp in this orb.
     *
     * @return xp amount
     */
    @ZenGetter("xp")
    float getXp();
}
