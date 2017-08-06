package crafttweaker.api.entity;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IItemStack;
import crafttweaker.util.IntegerRange;
import stanhebben.zenscript.annotations.ZenClass;

@ZenClass("crafttweaker.entity.IEntityDrop")
@ZenRegister
public interface IEntityDrop {

	IItemStack getItemStack();

	int getMin();

	int getMax();

	IntegerRange getRange();

	float getChance();

	boolean isPlayerOnly();
}
