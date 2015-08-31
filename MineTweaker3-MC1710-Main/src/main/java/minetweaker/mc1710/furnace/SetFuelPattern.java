package minetweaker.mc1710.furnace;

import minetweaker.api.item.IIngredient;

public class SetFuelPattern {
	private final IIngredient pattern;
	private final int value;

	public SetFuelPattern(IIngredient pattern, int value) {
		this.pattern = pattern;
		this.value = value;
	}

	public IIngredient getPattern() {
		return pattern;
	}

	public int getValue() {
		return value;
	}
}
