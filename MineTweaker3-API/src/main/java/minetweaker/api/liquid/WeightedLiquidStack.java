package minetweaker.api.liquid;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenGetter;

/**
 * Contains a weighted item stack. Weighted item stacks simply contain a stack
 * and a weight.
 * 
 * @author Stan Hebben
 */
@ZenClass("minetweaker.item.WeightedItemStack")
public final class WeightedLiquidStack {
	public static List<ILiquidStack> pickRandomDrops(Random random, WeightedLiquidStack[] items) {
		ArrayList<ILiquidStack> result = new ArrayList<ILiquidStack>();

		for (WeightedLiquidStack item : items) {
			if (random.nextFloat() <= item.getChance()) {
				result.add(item.getStack());
			}
		}

		return result;
	}

	private final ILiquidStack stack;
	private final float p;

	public WeightedLiquidStack(ILiquidStack stack, float p) {
		this.stack = stack;
		this.p = p;
	}

	@ZenGetter("stack")
	public ILiquidStack getStack() {
		return stack;
	}

	@ZenGetter("chance")
	public float getChance() {
		return p;
	}

	@ZenGetter("percent")
	public float getPercent() {
		return p * 100;
	}

	// #############################
	// ### Object implementation ###
	// #############################

	@Override
	public int hashCode() {
		int hash = 17;
		hash = 29 * hash + (this.stack != null ? this.stack.hashCode() : 0);
		hash = 29 * hash + Float.floatToIntBits(this.p);
		return hash;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final WeightedLiquidStack other = (WeightedLiquidStack) obj;
		if (this.stack != other.stack && (this.stack == null || !this.stack.equals(other.stack))) {
			return false;
		}
		if (Float.floatToIntBits(this.p) != Float.floatToIntBits(other.p)) {
			return false;
		}
		return true;
	}
}
