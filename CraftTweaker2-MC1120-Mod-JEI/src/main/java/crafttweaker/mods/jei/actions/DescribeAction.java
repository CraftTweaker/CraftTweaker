package crafttweaker.mods.jei.actions;

import crafttweaker.IAction;
import crafttweaker.api.item.IIngredient;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.liquid.ILiquidStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import crafttweaker.mods.jei.JEIAddonPlugin;
import mezz.jei.api.ingredients.VanillaTypes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class DescribeAction implements IAction {
    private final List<IItemStack> itemStacks = new ArrayList<>();
    private final List<ILiquidStack> fluidStacks = new ArrayList<>();
    private final String[] description;
    private final String name;

    @Deprecated
    public DescribeAction(IItemStack stack, String[] description) {
        this(Collections.singletonList(stack), description, stack.toString());
    }
    /**
     * Used to add a JEI Description page to the given ingredients
     * @param ingredients The ingredients to be given descriptions
     * @param description The Description: one or more lines per item, auto wrap if lines are too long
     * @param name Solely for the User output, should be a representation of stack
     */
    public DescribeAction(List<? extends IIngredient> ingredients, String[] description, String name) {
        ingredients.forEach(it -> {
            List<ILiquidStack> liquids = it.getLiquids();
            if (liquids.isEmpty()) {
                itemStacks.addAll(it.getItems());
            } else {
                fluidStacks.addAll(liquids);
            }
        });
        this.description = description;
        this.name = name;
    }

    @Override
    public void apply() {
        if (!itemStacks.isEmpty()) {
            JEIAddonPlugin.modRegistry.addIngredientInfo(itemStacks.stream().map(CraftTweakerMC::getItemStack).collect(Collectors.toList()), VanillaTypes.ITEM, description);
        }

        if (!fluidStacks.isEmpty()) {
            JEIAddonPlugin.modRegistry.addIngredientInfo(fluidStacks.stream().map(CraftTweakerMC::getLiquidStack).collect(Collectors.toList()), VanillaTypes.FLUID, description);
        }
    }

    @Override
    public String describe() {
        return "Adding description in JEI for: " + name;
    }
}