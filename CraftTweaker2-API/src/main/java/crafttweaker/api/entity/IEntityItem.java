package crafttweaker.api.entity;

import crafttweaker.api.item.IItemStack;
import stanhebben.zenscript.annotations.*;

/**
 * Represents an item entity. Item entities are entities representing an item
 * stack on the ground (or flying through the air).
 *
 * @author Stan Hebben
 */

@ZenClass("crafttweaker.entity.IEntityItem")
public interface IEntityItem extends IEntity {
    
    /**
     * Gets the entity item.
     *
     * @return entity item
     */
    @ZenGetter("item")
    IItemStack getItem();
}
