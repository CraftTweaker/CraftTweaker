package minetweaker.api.container;

import minetweaker.api.item.IItemStack;
import stanhebben.zenscript.annotations.*;

/**
 * Container interface.
 *
 * @author Stan Hebben
 */
@ZenClass("minetweaker.container.IContainer")
public interface IContainer extends Iterable<IItemStack> {

    /**
     * Gets the container size.
     *
     * @return container size
     */
    @ZenGetter("containerSize")
    int getContainerSize();

    /**
     * Gets the item stack at the given position. Returns null if there is no
     * stack at that position.
     *
     * @param i stack position
     *
     * @return stack contents, or null
     */
    @ZenMethod
    IItemStack getStack(int i);

    /**
     * Sets the item stack at the given position.
     *
     * @param i     stack position
     * @param stack contents, or null to set an empty stack
     */
    @ZenMethod
    void setStack(int i, IItemStack stack);
}
