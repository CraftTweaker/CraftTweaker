package crafttweaker.mc1120.brewing;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.IAction;
import crafttweaker.api.item.IIngredient;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import crafttweaker.api.oredict.IOreDictEntry;
import crafttweaker.api.recipes.IBrewingManager;
import net.minecraftforge.common.brewing.BrewingRecipe;
import net.minecraftforge.common.brewing.BrewingRecipeRegistry;
import net.minecraftforge.common.brewing.IBrewingRecipe;
import java.util.Arrays;

public class MCBrewing implements IBrewingManager{	
	public MCBrewing() {
	}

	@Override
	public void addHiddenBrew(IIngredient input, IIngredient ingredient, IItemStack output) {
		CraftTweakerAPI.apply(new ActionAddBrewingRecipe(input, ingredient, output));		
	}
	
	@Override
	public void addBrew(IItemStack input, IOreDictEntry ingredient, IItemStack output, boolean useInputNBT) {
		CraftTweakerAPI.apply(new ActionAddBrewingRecipe(input, ingredient, output, useInputNBT));
	}
	
	@Override
	public void addBrew(IItemStack input, IItemStack ingredient, IItemStack output, boolean useInputNBT) {
		CraftTweakerAPI.apply(new ActionAddBrewingRecipe(input, ingredient, output, useInputNBT));
	}
	
	@Override
	public void addBrew(IItemStack input, IItemStack[] ingredients, IItemStack output, boolean useInputNBT) {
		CraftTweakerAPI.apply(new ActionAddBrewingRecipe(input, ingredients, output, useInputNBT));
	}
	
	@Override
	public void addBrew(IItemStack input, IIngredient ingredient, IItemStack output, boolean useInputNBT) {
		CraftTweakerAPI.apply(new ActionAddBrewingRecipe(input, ingredient, output, useInputNBT));	
	}
	
	
	
	
	public class ActionAddBrewingRecipe implements IAction{
		private final IBrewingRecipe recipe;
		private final String type;
		private final String outName;
		
		public ActionAddBrewingRecipe(IItemStack input, IItemStack ingredient, IItemStack output, boolean useInputNBT) {
			this.type = "Item"; 
			this.outName = output.toString();
			if (useInputNBT) {
				this.recipe = new NBTBrewingRecipeSingle(CraftTweakerMC.getItemStack(input), CraftTweakerMC.getItemStack(ingredient), CraftTweakerMC.getItemStack(output));
			} else {
				this.recipe = new BrewingRecipe(CraftTweakerMC.getItemStack(input), CraftTweakerMC.getItemStack(ingredient), CraftTweakerMC.getItemStack(output));
			}
		}
		
		public ActionAddBrewingRecipe(IItemStack input, IOreDictEntry ingredient, IItemStack output, boolean useInputNBT) {
			this.outName = output.toString();
			this.type = "OreDict";
			this.recipe = new NBTBrewingRecipeOre(input, ingredient.getName(), output, useInputNBT);
		}

		public ActionAddBrewingRecipe(IItemStack input, IIngredient ingredient, IItemStack output, boolean useInputNBT) {
			this.outName = output.toString();
			this.type = "Ingredients";
			this.recipe = new NBTBrewingRecipeOre(input, ingredient, output, useInputNBT);
		}
		
		public ActionAddBrewingRecipe(IItemStack input, IItemStack[] ingredient, IItemStack output, boolean useInputNBT) {
			this.outName = output.toString();
			this.type = "IItemStack[]";
			this.recipe = new NBTBrewingRecipeOre(input, Arrays.asList(ingredient), output, useInputNBT);
		}
		
		public ActionAddBrewingRecipe(IIngredient input, IIngredient ingredient, IItemStack output) {
			this.outName = output.toString();
			this.type = "Multi-Inputs";
			this.recipe = new MultiBrewingRecipe(input, ingredient, output);
		}

		
		
		@Override
		public void apply() {
			BrewingRecipeRegistry.addRecipe(recipe);
		}

		@Override
		public String describe() {
			return "Adding brewing recipe of type " + type + " for " + outName + ", Registry size now: " + BrewingRecipeRegistry.getRecipes().size();
			
			//return Strings.format("Adding Brewing recipe of type %s for %s.", type, outName);
		}
	}
}
