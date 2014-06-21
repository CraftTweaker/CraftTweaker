/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package minetweaker.mc172.oredict;

import java.util.ArrayList;
import java.util.List;
import minetweaker.IUndoableAction;
import minetweaker.MineTweakerAPI;
import minetweaker.mc172.item.TweakerItemStack;
import minetweaker.mc172.util.MineTweakerHacks;
import minetweaker.api.item.IIngredient;
import minetweaker.api.item.IItemCondition;
import minetweaker.api.item.IItemStack;
import minetweaker.api.item.IItemTransformer;
import minetweaker.api.item.IngredientStack;
import minetweaker.api.oredict.IOreDictEntry;
import minetweaker.api.oredict.IngredientOreDict;
import minetweaker.util.ArrayUtil;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

/**
 *
 * @author Stan
 */
public class OreDictEntry implements IOreDictEntry {
	private final Integer id;
	
	public OreDictEntry(Integer id) {
		this.id = id;
	}
	
	public OreDictEntry(String id) {
		this.id = OreDictionary.getOreID(id);
	}
	
	// ####################################
	// ### IOreDictEntry implementation ###
	// ####################################

	@Override
	public void add(IItemStack item) {
		ItemStack stack = (ItemStack) item.getInternal();
		if (stack == null) {
			MineTweakerAPI.logger.logError("not a valid item");
		} else {
			MineTweakerAPI.tweaker.apply(new ActionAddItem(id, stack));
		}
	}

	@Override
	public void addAll(IOreDictEntry entry) {
		if (entry instanceof OreDictEntry) {
			MineTweakerAPI.tweaker.apply(new ActionAddAll(id, ((OreDictEntry) entry).id));
		} else {
			MineTweakerAPI.logger.logError("not a valid entry");
		}
	}

	@Override
	public void remove(IItemStack item) {
		ItemStack result = null;
		for (ItemStack itemStack : OreDictionary.getOres(id)) {
			if (item.matches(new TweakerItemStack(itemStack))) {
				result = itemStack;
				break;
			}
		}
		
		if (result != null) {
			MineTweakerAPI.tweaker.apply(new ActionRemoveItem(id, result));
		}
	}

	@Override
	public boolean contains(IItemStack item) {
		for (ItemStack itemStack : OreDictionary.getOres(id)) {
			if (item.matches(new TweakerItemStack(itemStack))) {
				return true;
			}
		}
		
		return false;
	}

	@Override
	public void mirror(IOreDictEntry other) {
		if (other instanceof OreDictEntry) {
			MineTweakerAPI.tweaker.apply(new ActionMirror(id, ((OreDictEntry) other).id));
		} else {
			MineTweakerAPI.logger.logError("not a valid oredict entry");
		}
	}

	@Override
	public String getMark() {
		return null;
	}

	@Override
	public int getAmount() {
		return 1;
	}

	@Override
	public List<IItemStack> getItems() {
		List<IItemStack> result = new ArrayList<IItemStack>();
		for (ItemStack item : OreDictionary.getOres(id)) {
			result.add(new TweakerItemStack(item));
		}
		return result;
	}

	@Override
	public IIngredient amount(int amount) {
		return new IngredientStack(this, amount);
	}

	@Override
	public IIngredient transform(IItemTransformer transformer) {
		return new IngredientOreDict(this, null, ArrayUtil.EMPTY_CONDITIONS, new IItemTransformer[] { transformer });
	}

	@Override
	public IIngredient only(IItemCondition condition) {
		return new IngredientOreDict(this, null, new IItemCondition[] { condition }, ArrayUtil.EMPTY_TRANSFORMERS);
	}

	@Override
	public IIngredient marked(String mark) {
		return new IngredientOreDict(this, mark, ArrayUtil.EMPTY_CONDITIONS, ArrayUtil.EMPTY_TRANSFORMERS);
	}

	@Override
	public boolean matches(IItemStack item) {
		return contains(item);
	}

	@Override
	public boolean contains(IIngredient ingredient) {
		List<IItemStack> items = ingredient.getItems();
		for (IItemStack item : items) {
			if (!matches(item)) return false;
		}
		
		return true;
	}

	@Override
	public IItemStack applyTransform(IItemStack item) {
		return item;
	}

	@Override
	public Object getInternal() {
		return OreDictionary.getOreName(id);
	}
	
	// ######################
	// ### Action classes ###
	// ######################
	
	private static class ActionAddItem implements IUndoableAction {
		private final Integer id;
		private final ItemStack item;
		
		public ActionAddItem(Integer id, ItemStack item) {
			this.id = id;
			this.item = item;
		}
		
		@Override
		public void apply() {
			OreDictionary.registerOre(id, item);
		}

		@Override
		public boolean canUndo() {
			return true;
		}

		@Override
		public void undo() {
			OreDictionary.getOres(id).remove(item);
		}

		@Override
		public String describe() {
			return "Adding " + item.getDisplayName() + " to ore dictionary entry " + OreDictionary.getOreName(id);
		}

		@Override
		public String describeUndo() {
			return "Removing " + item.getDisplayName() + " from ore dictionary entry " + OreDictionary.getOreName(id);
		}
	}
	
	private static class ActionMirror implements IUndoableAction {
		private final Integer idTarget;
		private final Integer idSource;
		
		private final ArrayList<ItemStack> targetCopy;
		
		public ActionMirror(Integer idTarget, Integer idSource) {
			this.idTarget = idTarget;
			this.idSource = idSource;
			
			targetCopy = OreDictionary.getOres(idTarget);
		}

		@Override
		public void apply() {
			MineTweakerHacks.getOreStacks().put(idTarget, OreDictionary.getOres(idSource));
		}

		@Override
		public boolean canUndo() {
			return true;
		}

		@Override
		public void undo() {
			MineTweakerHacks.getOreStacks().put(idTarget, targetCopy);
		}

		@Override
		public String describe() {
			return "Mirroring " + OreDictionary.getOreName(idSource) + " to " + OreDictionary.getOreName(idTarget);
		}

		@Override
		public String describeUndo() {
			return "Undoing mirror of " + OreDictionary.getOreName(idSource) + " to " + OreDictionary.getOreName(idTarget);
		}
	}
	
	private static class ActionRemoveItem implements IUndoableAction {
		private final Integer id;
		private final ItemStack item;
		
		public ActionRemoveItem(Integer id, ItemStack item) {
			this.id = id;
			this.item = item;
		}
		
		@Override
		public void apply() {
			OreDictionary.getOres(id).add(item);
		}

		@Override
		public boolean canUndo() {
			return true;
		}

		@Override
		public void undo() {
			OreDictionary.getOres(id).remove(item);
		}

		@Override
		public String describe() {
			return "Adding " + item.getDisplayName() + " to ore dictionary entry " + OreDictionary.getOreName(id);
		}

		@Override
		public String describeUndo() {
			return "Removing " + item.getDisplayName() + " from ore dictionary entry " + OreDictionary.getOreName(id);
		}
	}
	
	private static class ActionAddAll implements IUndoableAction {
		private final Integer idTarget;
		private final Integer idSource;
		
		public ActionAddAll(Integer idTarget, Integer idSource) {
			this.idTarget = idTarget;
			this.idSource = idSource;
		}

		@Override
		public void apply() {
			List<ItemStack> ores = OreDictionary.getOres(idTarget);
			for (ItemStack stack : OreDictionary.getOres(idSource)) {
				ores.add(stack);
			}
		}

		@Override
		public boolean canUndo() {
			return true;
		}

		@Override
		public void undo() {
			List<ItemStack> ores = OreDictionary.getOres(idTarget);
			for (ItemStack stack : OreDictionary.getOres(idSource)) {
				ores.remove(stack);
			}
		}

		@Override
		public String describe() {
			return "Copying contents of ore dictionary entry " + OreDictionary.getOreName(idSource) + " to " + OreDictionary.getOreName(idTarget);
		}

		@Override
		public String describeUndo() {
			return "Removing contents of ore dictionary entry " + OreDictionary.getOreName(idSource) + " from " + OreDictionary.getOreName(idTarget);
		}
	}
}
