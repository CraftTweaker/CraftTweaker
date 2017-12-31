package crafttweaker.mc1120.actions;

import crafttweaker.IAction;
import net.minecraft.item.ItemStack;

/**
 * @author Jared
 */
public class ActionSetStackSize implements IAction {

    private final ItemStack stack;
    private final int size;

    public ActionSetStackSize(ItemStack stack, int size) {
        this.stack = stack;
        this.size = size;
    }

    private static void set(ItemStack stack, int size) {
        if (!stack.isEmpty())
            stack.getItem().setMaxStackSize(size);
    }

    @Override
    public void apply() {
        set(stack, size);
    }

    @Override
    public String describe() {
        return "Setting max stack size of  " + stack.getDisplayName() + " to " + size;
    }

}
