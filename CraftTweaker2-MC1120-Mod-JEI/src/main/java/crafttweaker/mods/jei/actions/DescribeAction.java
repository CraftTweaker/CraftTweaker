package crafttweaker.mods.jei.actions;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.IAction;
import crafttweaker.api.item.IIngredient;
import crafttweaker.api.item.IItemStack;
import crafttweaker.mods.jei.JEIAddonPlugin;

public class DescribeAction implements IAction {
	private final List<Object> stack;
	private final String[] description;
	private final String name;
	
	@Deprecated
	public DescribeAction(IItemStack stack, String[] description) {
		this(Collections.singletonList(stack), description, stack.toString());
	}
	/**
	 * Used to add a JEI Description page to the given items/fluids/...
	 * Objects need to implement {@code getInternal()} and the result of this method needs to be
	 * <ol type = "A">
	 * <li> of a Minecraft ingredient type (e.g. ItemStack, FluidStack,...)</li>
	 * <li> all items in the list need to be of the same class</li>
	 * </ol>
	 * @param stack The objects to be given descriptions
	 * @param description The Description: one or more lines per item, auto wrap if lines are too long
	 * @param name Solely for the User output, should be a representation of stack 
	 */
	public DescribeAction(List<? extends IIngredient> stack, String[] description, String name) {
		this.stack = stack.stream().map(item -> item.getInternal()).collect(Collectors.toList());
		this.description = description;
		this.name = name;
	}

	@Override
	public void apply() {
		if(stack.isEmpty()) {
			CraftTweakerAPI.logError(name + " is empty!");
			return;
		} else if (!checkClasses()) {
			CraftTweakerAPI.logError(name + " needs to consist only of items of the same type!");
			return;
		}
			
		JEIAddonPlugin.modRegistry.addIngredientInfo(stack, stack.get(0).getClass(), description);
	}

	@Override
	public String describe() {
		return "Adding description in JEI for: " + name;
	}
	
	public boolean checkClasses() {
		Class<?> clazz = stack.get(0).getClass();
		return stack.stream().map(item -> item.getClass()).allMatch(i -> i == clazz);
	}
}