/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package minetweaker.api.util;

import minetweaker.api.item.IIngredient;
import minetweaker.api.item.IItemDefinition;
import minetweaker.api.item.IItemStack;

import java.util.*;
import java.util.stream.Collectors;

/**
 *
 * @author Stan
 */
public class IngredientMap<T> {
	private final HashMap<IItemDefinition, List<IngredientMapEntry<T>>> entries;

	public IngredientMap() {
		entries = new HashMap<>();
	}

	public IngredientMapEntry<T> register(IIngredient ingredient, T entry) {
		Set<IItemDefinition> items = ingredient.getItems().stream().map(IItemStack::getDefinition).collect(Collectors.toSet());

		IngredientMapEntry<T> actualEntry = new IngredientMapEntry<>(ingredient, entry);

		for (IItemDefinition item : items) {
			if (!entries.containsKey(item)) {
				entries.put(item, new ArrayList<>());
			}

			entries.get(item).add(actualEntry);
		}

		return actualEntry;
	}

	public void unregister(IngredientMapEntry<T> entry) {
		Set<IItemDefinition> items = entry.ingredient.getItems().stream().map(IItemStack::getDefinition).collect(Collectors.toSet());

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
			return entries.stream().filter(entry -> entry.ingredient.matches(item)).map(entry -> entry.entry).collect(Collectors.toCollection(ArrayList::new));
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
