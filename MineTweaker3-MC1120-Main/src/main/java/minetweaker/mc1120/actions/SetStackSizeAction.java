package minetweaker.mc1120.actions;

import minetweaker.IUndoableAction;
import net.minecraft.item.ItemStack;

/**
 * @author Jared
 */
public class SetStackSizeAction implements IUndoableAction {
    
    private final ItemStack stack;
    private final int size;
    private final int oldSize;
    
    public SetStackSizeAction(ItemStack stack, int size) {
        this.stack = stack;
        this.size = size;
        this.oldSize = stack.getMaxStackSize();
    }
    
    private static void set(ItemStack stack, int size) {
        if(!stack.isEmpty())
            stack.getItem().setMaxStackSize(size);
    }
    
    @Override
    public void apply() {
        set(stack, size);
    }
    
    @Override
    public boolean canUndo() {
        return true;
    }
    
    @Override
    public void undo() {
        set(stack, oldSize);
    }
    
    @Override
    public String describe() {
        return "Setting max stack size of  " + stack.getDisplayName() + " to " + size;
    }
    
    @Override
    public String describeUndo() {
        return "Reverting max stack size of " + stack.getDisplayName() + " to " + oldSize;
    }
    
    @Override
    public Object getOverrideKey() {
        return null;
    }
}
