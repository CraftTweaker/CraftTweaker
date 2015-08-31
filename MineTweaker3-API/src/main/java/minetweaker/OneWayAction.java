package minetweaker;

/**
 * Base implementation for a non-undoable action.
 * 
 * @author Stan Hebben
 */
public abstract class OneWayAction implements IUndoableAction {
	@Override
	public boolean canUndo() {
		return false;
	}

	@Override
	public void undo() {
	}

	@Override
	public String describeUndo() {
		return "impossibru!";
	}
}
