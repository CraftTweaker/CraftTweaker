/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package minetweaker.mc1710.actions;

import minetweaker.IUndoableAction;
import minetweaker.MineTweakerAPI;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;

/**
 *
 * @author Jared
 */
public class SetBlockHardnessAction implements IUndoableAction {

    private final ItemStack stack;
    private final float hardness;
    private final float oldHardness;

    public SetBlockHardnessAction(ItemStack stack, float hardness) {
        this.stack = stack;
        this.hardness = hardness;
        this.oldHardness = Block.getBlockFromItem(stack.getItem()).getBlockHardness(null, 0, 0, 0);
    }

    @Override
    public void apply() {
        if (isBlock(stack)) {
            set(stack, hardness);
        }
    }

    @Override
    public boolean canUndo() {
        return true;
    }

    @Override
    public void undo() {
        if (isBlock(stack))
            set(stack, oldHardness);
    }

    @Override
    public String describe() {
        if (isBlock(stack)) {
            return "Setting hardness of " + stack.getDisplayName() + " to " + hardness;
        }
        return "Unable to set hardness of " + stack.getDisplayName() + " because it is an Item";
    }

    @Override
    public String describeUndo() {
        if (isBlock(stack)) {
            return "Reverting hardness of " + stack.getDisplayName() + " to " + oldHardness;
        }
        return "Unable to revert hardness of " + stack.getDisplayName() + " because it is an Item";
    }

    private static void set(ItemStack stack, float hardness) {
        if (isBlock(stack)) {
            Block block = Block.getBlockFromItem(stack.getItem());
            block.setHardness(hardness);
        } else {
            MineTweakerAPI.logError("Item is not a block");
        }
    }

    public static boolean isBlock(ItemStack stack) {
        String name = Block.blockRegistry.getNameForObject(Block.getBlockFromItem(stack.getItem()));
        return !name.equalsIgnoreCase("minecraft:air") && Block.blockRegistry.containsKey(name);

    }

    @Override
    public Object getOverrideKey() {
        return null;
    }
}
