package crafttweaker.api.container;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IItemStack;
import stanhebben.zenscript.annotations.*;

/**
 * Container interface.
 *
 * @author Stan Hebben
 */
@ZenClass("crafttweaker.container.IContainer")
@IterableSimple("crafttweaker.item.IItemStack")
@ZenRegister
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
    
    @ZenMethod
    @ZenCaster
    String asString();
    
    Object getInternal();
}
