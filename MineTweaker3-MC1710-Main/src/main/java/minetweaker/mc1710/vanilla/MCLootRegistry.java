/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package minetweaker.mc1710.vanilla;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import minetweaker.IUndoableAction;
import minetweaker.MineTweakerAPI;
import minetweaker.api.item.IIngredient;
import minetweaker.api.item.WeightedItemStack;
import minetweaker.api.minecraft.MineTweakerMC;
import minetweaker.api.vanilla.ILootRegistry;
import minetweaker.api.vanilla.LootEntry;
import minetweaker.mc1710.util.MineTweakerHacks;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraftforge.common.ChestGenHooks;

/**
 *
 * @author Stan
 */
public class MCLootRegistry implements ILootRegistry {
	private static final Map<String, ChestGenHooks> LOOT = MineTweakerHacks.getChestLoot();

	@Override
	public void addChestLoot(String type, WeightedItemStack item) {
		WeightedRandomChestContent content = new WeightedRandomChestContent(
				MineTweakerMC.getItemStack(item.getStack()),
				1,
				1,
				(int) item.getChance());
		MineTweakerAPI.apply(new AddLootAction(type, content));
	}

	@Override
	public void addChestLoot(String type, WeightedItemStack item, int min, int max) {
		WeightedRandomChestContent content = new WeightedRandomChestContent(
				MineTweakerMC.getItemStack(item.getStack()),
				min,
				max,
				(int) item.getChance());
		MineTweakerAPI.apply(new AddLootAction(type, content));
	}

	@Override
	public void removeChestLoot(String type, IIngredient pattern) {
		MineTweakerAPI.apply(new RemoveLootAction(type, pattern));
	}

	@Override
	public List<LootEntry> getLoot(String type) {
		ChestGenHooks recipe = LOOT.get(type);
		List<WeightedRandomChestContent> contents = MineTweakerHacks.getPrivateObject(recipe, "contents");

		List<LootEntry> results = new ArrayList<LootEntry>();
		for (WeightedRandomChestContent content : contents) {
			results.add(new LootEntry(new WeightedItemStack(
					MineTweakerMC.getIItemStack(content.theItemId),
					content.itemWeight),
					content.theMinimumChanceToGenerateItem,
					content.theMaximumChanceToGenerateItem));
		}
		return results;
	}

	@Override
	public List<String> getLootTypes() {
		Set<String> keys = LOOT.keySet();
		List<String> keyList = new ArrayList<String>();
		keyList.addAll(keys);
		return keyList;
	}

	// ######################
	// ### Action Classes ###
	// ######################

	/**
	 * Ported from ModTweaker.
	 * 
	 * @author JoshieJack
	 */
	private static class AddLootAction implements IUndoableAction {
		private final String chest;
		private final WeightedRandomChestContent content;

		public AddLootAction(String chest, WeightedRandomChestContent content) {
			this.chest = chest;
			this.content = content;
		}

		@Override
		public void apply() {
			ChestGenHooks recipe = LOOT.get(chest);
			List<WeightedRandomChestContent> contents = MineTweakerHacks.getPrivateObject(recipe, "contents");

			contents.add(content);
		}

		@Override
		public boolean canUndo() {
			return true;
		}

		@Override
		public void undo() {
			ChestGenHooks recipe = LOOT.get(chest);
			List<WeightedRandomChestContent> contents = MineTweakerHacks.getPrivateObject(recipe, "contents");

			contents.remove(content);
		}

		public String getRecipeInfo() {
			return content.theItemId.getDisplayName();
		}

		@Override
		public String describe() {
			return "Adding chest loot " + content.theItemId.getDisplayName() + " to loot class " + chest;
		}

		@Override
		public String describeUndo() {
			return "Removing chest loot " + content.theItemId.getDisplayName() + " from loot class " + chest;
		}

		@Override
		public Object getOverrideKey() {
			return null;
		}
	}

	/**
	 * Ported from ModTweaker.
	 * 
	 * @author JoshieJack
	 */
	private static class RemoveLootAction implements IUndoableAction {
		private final String chest;
		private final IIngredient pattern;
		private final List<WeightedRandomChestContent> removed;

		public RemoveLootAction(String chest, IIngredient pattern) {
			this.chest = chest;
			this.pattern = pattern;
			removed = new ArrayList<WeightedRandomChestContent>();
		}

		@Override
		public void apply() {
			removed.clear();

			ChestGenHooks recipe = LOOT.get(chest);
			List<WeightedRandomChestContent> contents = MineTweakerHacks.getPrivateObject(recipe, "contents");

			for (WeightedRandomChestContent r : contents) {
				if (pattern.matches(MineTweakerMC.getIItemStack(r.theItemId))) {
					removed.add(r);
				}
			}

			for (WeightedRandomChestContent r : removed) {
				contents.remove(r);
			}
		}

		@Override
		public boolean canUndo() {
			return true;
		}

		@Override
		public void undo() {
			ChestGenHooks recipe = LOOT.get(chest);
			List<WeightedRandomChestContent> contents = MineTweakerHacks.getPrivateObject(recipe, "contents");

			for (WeightedRandomChestContent entry : removed) {
				contents.add(entry);
			}
		}

		@Override
		public String describe() {
			return "Removing chest loot " + pattern + " for loot type " + chest;
		}

		@Override
		public String describeUndo() {
			return "Restoring chest loot " + pattern + " for loot type " + chest + " (" + removed.size() + " entries)";
		}

		@Override
		public Object getOverrideKey() {
			return null;
		}
	}
}
