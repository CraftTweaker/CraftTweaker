package crafttweaker.mc1120.actions;

import crafttweaker.*;
import crafttweaker.api.item.IIngredient;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;

public class ActionAddFurnaceRecipe implements IAction {
    
    private final IIngredient ingredient;
    private final ItemStack[] input;
    private final ItemStack output;
    private final double xp;
    
    public ActionAddFurnaceRecipe(IIngredient ingredient, ItemStack[] input, ItemStack output, double xp) {
        this.ingredient = ingredient;
        this.input = input;
        this.output = output;
        this.xp = xp;
    }
    
    @Override
    public void apply() {
        for(ItemStack inputStack : input) {
            FurnaceRecipes.instance().addSmeltingRecipe(inputStack, output, (float) xp);
        }
    }
    
    @Override
    public String describe() {
        return "Adding furnace recipe for " + ingredient;
    }
}