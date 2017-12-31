package crafttweaker.api.entity;

import crafttweaker.api.item.IItemStack;
import stanhebben.zenscript.annotations.ZenGetter;

/**
 * Represents an item entity. Item entities are entities representing an item
 * stack on the ground (or flying through the air).
 *
 * @author Stan Hebben
 */
public interface IEntityItem extends IEntity {

    /**
     * Gets the entity item.
     *
     * @return entity item
     */
    @ZenGetter("item")
    IItemStack getItem();
}
