package crafttweaker.api.item;

import crafttweaker.annotations.ZenRegister;
import stanhebben.zenscript.annotations.ZenClass;

/**
 * Item conditions apply additional conditions on item stacks.
 * <p>
 * They may, for instance, require specific damage ranges or NBT tags. Custom
 * implementations of this functional interface may contain any kind of
 * condition.
 *
 * @author Stan Hebben
 */
@ZenClass("crafttweaker.item.IItemCondition")
@ZenRegister
public interface IItemCondition {

    /**
     * Returns true if the given stack matches the required conditions.
     *
     * @param stack item stack to check
     * @return true if the condition applies to this stack
     */
    boolean matches(IItemStack stack);

}
