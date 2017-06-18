package crafttweaker.mc1120.actions;

import crafttweaker.*;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

/**
 * @author Jared
 */
public class ActionSetBlockHardness implements IAction {

    private final ItemStack stack;
    private final float hardness;
    
    public ActionSetBlockHardness(ItemStack stack, float hardness) {
        this.stack = stack;
        this.hardness = hardness;
    }

    private static void set(ItemStack stack, float hardness) {
        if(isBlock(stack)) {
            Block block = Block.getBlockFromItem(stack.getItem());
            block.setHardness(hardness);
        } else {
            CraftTweakerAPI.logError("Item is not a block");
        }
    }

    public static boolean isBlock(ItemStack stack) {
        if(stack.isEmpty()) {
            return false;
        }
        ResourceLocation name = Block.REGISTRY.getNameForObject(Block.getBlockFromItem(stack.getItem()));
        return !name.toString().equals("minecraft:air") && Block.REGISTRY.containsKey(name);

    }

    @Override
    public void apply() {
        if(isBlock(stack)) {
            set(stack, hardness);
        }
    }


    @Override
    public String describe() {
        if(isBlock(stack)) {
            return "Setting hardness of " + stack.getDisplayName() + " to " + hardness;
        }
        return "Unable to set hardness of " + stack.getDisplayName() + " because it is an Item";
    }
}
