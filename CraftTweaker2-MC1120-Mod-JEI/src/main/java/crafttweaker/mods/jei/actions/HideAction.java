package crafttweaker.mods.jei.actions;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.IAction;
import crafttweaker.api.item.IItemStack;
import crafttweaker.mods.jei.JEIAddonPlugin;
import net.minecraft.item.ItemStack;

import static crafttweaker.api.minecraft.CraftTweakerMC.getItemStack;

public class HideAction implements IAction {

    private final IItemStack stack;

    public HideAction(IItemStack stack) {
        this.stack = stack;
    }


    @Override
    public void apply() {
        if (stack == null) {
            CraftTweakerAPI.logError("Cannot hide null item!");
            return;
        }


        ItemStack IStack = getItemStack(stack);
        JEIAddonPlugin.itemRegistry.removeIngredientsAtRuntime(ItemStack.class, JEIAddonPlugin.getSubTypes(IStack));

    }

    @Override
    public String describe() {
        return "Hiding item in JEI: " + stack;
    }

}
