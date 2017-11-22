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
	public void addMultiBrew(IIngredient input, IIngredient ingredient, IItemStack output, boolean hidden) {
		CraftTweakerAPI.apply(new ActionAddBrewingRecipe(input, ingredient, output, hidden));		
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
		private final boolean valid;
		
		public ActionAddBrewingRecipe(IItemStack input, IItemStack ingredient, IItemStack output, boolean useInputNBT) {
			this.type = "Item"; 
			this.outName = output.toString();
			this.valid = true;
			if (useInputNBT) {
				this.recipe = new NBTBrewingRecipeSingle(CraftTweakerMC.getItemStack(input), CraftTweakerMC.getItemStack(ingredient), CraftTweakerMC.getItemStack(output));
			} else {
				this.recipe = new BrewingRecipe(CraftTweakerMC.getItemStack(input), CraftTweakerMC.getItemStack(ingredient), CraftTweakerMC.getItemStack(output));
			}
		}
		
		public ActionAddBrewingRecipe(IItemStack input, IOreDictEntry ingredient, IItemStack output, boolean useInputNBT) {
			this.valid = true;
			this.outName = output.toString();
			this.type = "OreDict";
			this.recipe = new NBTBrewingRecipeOre(input, ingredient.getName(), output, useInputNBT);
		}

		public ActionAddBrewingRecipe(IItemStack input, IIngredient ingredient, IItemStack output, boolean useInputNBT) {
			this.outName = output.toString();
			this.type = "Ingredients";
			this.recipe = new NBTBrewingRecipeOre(input, ingredient, output, useInputNBT);
			this.valid = true;
		}
		
		public ActionAddBrewingRecipe(IItemStack input, IItemStack[] ingredient, IItemStack output, boolean useInputNBT) {
			this.outName = output.toString();
			this.type = "IItemStack[]";
			this.recipe = new NBTBrewingRecipeOre(input, Arrays.asList(ingredient), output, useInputNBT);
			this.valid = true;
		}
		
		public ActionAddBrewingRecipe(IIngredient input, IIngredient ingredient, IItemStack output, boolean hidden) {
			this.outName = output.toString();
			this.type = "Multi-Inputs";
			this.recipe = new MultiBrewingRecipe(input, ingredient, output, !hidden);
			this.valid = ((MultiBrewingRecipe)recipe).isValid();
		}

		
		
		@Override
		public void apply() {
			if(!valid) {
				CraftTweakerAPI.logError(String.format("Brewing recipe of type %s for %s is invalid", type, outName));
				return;
			}
			BrewingRecipeRegistry.addRecipe(recipe);
		}

		@Override
		public String describe() {
			return "Adding brewing recipe of type " + type + " for " + outName + ", Registry size now: " + BrewingRecipeRegistry.getRecipes().size();
			
			//return Strings.format("Adding Brewing recipe of type %s for %s.", type, outName);
		}
	}
}
