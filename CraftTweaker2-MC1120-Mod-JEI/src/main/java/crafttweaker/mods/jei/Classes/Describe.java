package crafttweaker.mods.jei.Classes;

import static crafttweaker.api.minecraft.CraftTweakerMC.getItemStack;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.IAction;
import crafttweaker.api.item.IItemStack;
import crafttweaker.mods.jei.JEIAddonPlugin;
import net.minecraft.item.ItemStack;

public class Describe implements IAction{
	private final IItemStack stack;
	private final String[] description;
	
	public Describe(IItemStack stack, String[] description){
		this.stack = stack;
		this.description = description;
	}

	@Override
	public void apply() {
		if (stack == null){
			CraftTweakerAPI.logError("Cannot describe a null item!");
			return;
		}
		JEIAddonPlugin.modRegistry.addIngredientInfo(getItemStack(stack), ItemStack.class, description);
	}

	@Override
	public String describe() {
		return "Adding description in JEI for: " + stack;
	}
}