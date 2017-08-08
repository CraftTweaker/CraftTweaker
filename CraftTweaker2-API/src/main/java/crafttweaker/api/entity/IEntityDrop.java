package crafttweaker.api.entity;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IItemStack;
import crafttweaker.util.IntegerRange;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenGetter;

@ZenClass("crafttweaker.entity.IEntityDrop")
@ZenRegister
public interface IEntityDrop {

	@ZenGetter("stack")
	IItemStack getItemStack();

	@ZenGetter("min")
	int getMin();

	@ZenGetter("max")
	int getMax();

	IntegerRange getRange();

	@ZenGetter("chance")
	float getChance();

	@ZenGetter("playerOnly")
	boolean isPlayerOnly();
}
