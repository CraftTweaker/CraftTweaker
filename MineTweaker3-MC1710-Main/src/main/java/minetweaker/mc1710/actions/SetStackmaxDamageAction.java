/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package minetweaker.mc1710.actions;

import minetweaker.IUndoableAction;
import net.minecraft.item.ItemStack;

/**
 *
 * @author Jared
 */
public class SetStackmaxDamageAction implements IUndoableAction {

	private final ItemStack stack;
	private final int damage;
	private final int oldDamage;

	public SetStackmaxDamageAction(ItemStack stack, int damage) {
		this.stack = stack;
		this.damage= damage;
		this.oldDamage= stack.getMaxDamage();
	}

	@Override
	public void apply() {
		set(stack, damage);
	}

	@Override
	public boolean canUndo() {
		return true;
	}

	@Override
	public void undo() {
		set(stack, oldDamage);
	}

	@Override
	public String describe() {
		return "Setting max damage of  " + stack.getDisplayName() + " to " + damage;
	}

	@Override
	public String describeUndo() {
		return "Reverting max damage of " + stack.getDisplayName() + " to " + oldDamage;
	}

	private static void set(ItemStack stack, int damage) {
		stack.getItem().setMaxDamage(damage);
	}

	@Override
	public Object getOverrideKey() {
		return null;
	}
}
