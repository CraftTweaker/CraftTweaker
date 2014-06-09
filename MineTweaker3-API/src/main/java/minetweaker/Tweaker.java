/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package minetweaker;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import minetweaker.minecraft.item.IIngredient;

/**
 * 
 * 
 * @author Stan Hebben
 */
public class Tweaker implements IMineTweaker {
	List<IUndoableAction> actions = new ArrayList<IUndoableAction>();
	Set<IUndoableAction> wereStuck = new HashSet<IUndoableAction>();
	
	@Override
	public void apply(IUndoableAction action) {
		MineTweakerAPI.logger.logInfo(action.describe());
		action.apply();
	}

	@Override
	public void remove(IIngredient items) {
		GlobalRegistry.remove(items);
	}

	@Override
	public List<IUndoableAction> rollback() {
		List<IUndoableAction> stuck = new ArrayList<IUndoableAction>();
		for (int i = actions.size() - 1; i >= 0; i--) {
			IUndoableAction action = actions.get(i);
			if (action.canUndo()) {
				MineTweakerAPI.logger.logInfo(action.describeUndo());
				action.undo();
			} else {
				stuck.add(0, action);
				wereStuck.add(action);
			}
		}
		return stuck;
	}
}
