package crafttweaker.api.entity;

import crafttweaker.annotations.ZenRegister;
import stanhebben.zenscript.annotations.*;

/**
 * Represents an xp orb.
 *
 * @author Stan Hebben
 */
@ZenClass("crafttweaker.entity.IEntityXp")
@ZenRegister
public interface IEntityXp extends IEntity {
    
    /**
     * Gets the amount of xp in this orb.
     *
     * @return xp amount
     */
    @ZenGetter("xp")
    float getXp();
    
    /**
     * Sets the amount of xp in this orb.
     * @param xp amount
     */
    @ZenSetter("xp")
    void setXp(int xp);
}
