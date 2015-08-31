package minetweaker.api.item;

import stanhebben.zenscript.annotations.ZenClass;

/**
 * Item conditions apply additional conditions on item stacks.
 * 
 * They may, for instance, require specific damage ranges or NBT tags. Custom
 * implementations of this functional interface may contain any kind of
 * condition.
 * 
 * @author Stan Hebben
 */
@ZenClass("minetweaker.item.IItemCondition")
public interface IItemCondition {
	/**
	 * Returns true if the given stack matches the required conditions.
	 * 
	 * @param stack item stack to check
	 * @return true if the condition applies to this stack
	 */
	public boolean matches(IItemStack stack);
}
