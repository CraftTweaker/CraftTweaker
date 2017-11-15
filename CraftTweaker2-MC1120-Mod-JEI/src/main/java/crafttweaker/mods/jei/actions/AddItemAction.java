package crafttweaker.mods.jei.actions;

import static crafttweaker.api.minecraft.CraftTweakerMC.getItemStack;

import java.util.Collections;

import crafttweaker.IAction;
import crafttweaker.api.item.IItemStack;
import crafttweaker.mods.jei.JEIAddonPlugin;
import net.minecraft.item.ItemStack;

public class AddItemAction implements IAction{

	private final IItemStack stack;
	
	public AddItemAction(IItemStack stack){
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
