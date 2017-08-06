package crafttweaker.mc1120.entity;

import crafttweaker.api.entity.IEntityDrop;
import crafttweaker.api.item.IItemStack;
import crafttweaker.util.IntegerRange;

public class EntityDrop implements IEntityDrop {

	private final IItemStack itemStack;
	private final IntegerRange range;
	private final float chance;
	private final boolean playerOnly;

	public EntityDrop(IItemStack itemStack, int min, int max, float chance, boolean playerOnly) {
		this.itemStack = itemStack;
		this.range = new IntegerRange(min, max);
		this.chance = chance;
		this.playerOnly = playerOnly;
	}

	public EntityDrop(IItemStack itemStack, int min, int max, float chance) {
		this(itemStack, min, max, chance, false);
	}

	@Override
	public IItemStack getItemStack() {
		return itemStack;
	}

	@Override
	public int getMin() {
		return range.getMin();
	}

	@Override
	public int getMax() {
		return range.getMax();
	}

	@Override
	public IntegerRange getRange() {
		return range;
	}

	@Override
	public float getChance() {
		return chance;
	}

	@Override
	public boolean isPlayerOnly() {
		return playerOnly;
	}
}
