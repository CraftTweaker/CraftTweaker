package crafttweaker.mods.jei.actions;

import crafttweaker.IAction;
import crafttweaker.api.item.IItemStack;
import crafttweaker.mods.jei.JEIAddonPlugin;
import net.minecraft.item.ItemStack;

import java.util.Collections;

import static crafttweaker.api.minecraft.CraftTweakerMC.getItemStack;

public class AddItemAction implements IAction {

    private final IItemStack stack;

    public AddItemAction(IItemStack stack) {
        this.stack = stack;
    }

    @Override
    public void apply() {
        JEIAddonPlugin.itemRegistry.addIngredientsAtRuntime(ItemStack.class, Collections.singletonList(getItemStack(stack)));
    }

    @Override
    public String describe() {
        return String.format("Adding %s to JEI", stack);
    }

}
