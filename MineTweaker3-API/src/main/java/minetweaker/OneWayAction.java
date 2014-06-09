/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package minetweaker;

/**
 *
 * @author Stanneke
 */
public abstract class OneWayAction implements IUndoableAction {
	@Override
	public boolean canUndo() {
		return false;
	}

	@Override
	public void undo() {}

	@Override
	public String describeUndo() {
		return "impossibru!";
	}
}
