/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package minetweaker.api.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import minetweaker.api.item.IIngredient;
import minetweaker.api.item.IItemDefinition;
import minetweaker.api.item.IItemStack;

/**
 *
 * @author Stan
 */
public class IngredientMap<T> {
	private final HashMap<IItemDefinition, List<IngredientMapEntry<T>>> entries;

	public IngredientMap() {
		entries = new HashMap<IItemDefinition, List<IngredientMapEntry<T>>>();
	}

	public IngredientMapEntry<T> register(IIngredient ingredient, T entry) {
		Set<IItemDefinition> items = new HashSet<IItemDefinition>();
		for (IItemStack item : ingredient.getItems()) {
			items.add(item.getDefinition());
		}

		IngredientMapEntry<T> actualEntry = new IngredientMapEntry<T>(ingredient, entry);

		for (IItemDefinition item : items) {
			if (!entries.containsKey(item)) {
				entries.put(item, new ArrayList<IngredientMapEntry<T>>());
			}

			entries.get(item).add(actualEntry);
		}

		return actualEntry;
	}

	public void unregister(IngredientMapEntry<T> entry) {
		Set<IItemDefinition> items = new HashSet<IItemDefinition>();
		for (IItemStack item : entry.ingredient.getItems()) {
			items.add(item.getDefinition());
		}

		for (IItemDefinition item : items) {
			if (entries.containsKey(item)) {
				entries.get(item).remove(entry);
			}
		}
	}

	public T getFirstEntry(IItemStack item) {
		for (IngredientMapEntry<T> entry : entries.get(item.getDefinition())) {
			if (entry.ingredient.matches(item)) {
				return entry.entry;
			}
		}

		return null;
	}

	public List<T> getEntries(IItemStack item) {
		List<IngredientMapEntry<T>> entries = this.entries.get(item.getDefinition());
		if (entries != null) {
			ArrayList<T> results = new ArrayList<T>();
			for (IngredientMapEntry<T> entry : entries) {
				if (entry.ingredient.matches(item)) {
					results.add(entry.entry);
				}
			}
			return results;
		} else {
			return Collections.EMPTY_LIST;
		}
	}

	public static class IngredientMapEntry<T> {
		private final IIngredient ingredient;
		private final T entry;

		public IngredientMapEntry(IIngredient ingredient, T entry) {
			this.ingredient = ingredient;
			this.entry = entry;
		}
	}
}
