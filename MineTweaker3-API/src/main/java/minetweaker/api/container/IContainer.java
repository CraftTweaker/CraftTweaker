package minetweaker.api.container;

import minetweaker.api.item.IItemStack;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenGetter;
import stanhebben.zenscript.annotations.ZenMethod;

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
	public int getContainerSize();

	/**
	 * Gets the item stack at the given position. Returns null if there is no
	 * stack at that position.
	 * 
	 * @param i stack position
	 * @return stack contents, or null
	 */
	@ZenMethod
	public IItemStack getStack(int i);

	/**
	 * Sets the item stack at the given position.
	 * 
	 * @param i stack position
	 * @param stack contents, or null to set an empty stack
	 */
	@ZenMethod
	public void setStack(int i, IItemStack stack);
}
