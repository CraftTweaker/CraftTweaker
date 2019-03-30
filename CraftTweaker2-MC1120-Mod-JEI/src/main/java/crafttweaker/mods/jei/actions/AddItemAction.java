package crafttweaker.mods.jei.actions;

import static crafttweaker.api.minecraft.CraftTweakerMC.getItemStack;

import java.util.Collections;

import crafttweaker.IAction;
import crafttweaker.api.item.IItemStack;
import crafttweaker.mods.jei.JEIAddonPlugin;
import mezz.jei.api.ingredients.VanillaTypes;

public class AddItemAction implements IAction{

	private final IItemStack stack;
	
	public AddItemAction(IItemStack stack){
		this.stack = stack;
	}
	
	@Override
	public void apply() {
		JEIAddonPlugin.itemRegistry.addIngredientsAtRuntime(VanillaTypes.ITEM, Collections.singletonList(getItemStack(stack)));
	}

	@Override
	public String describe() {
		return String.format("Adding %s to JEI", stack);
	}
	
}
