/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package minetweaker.mc1710.furnace;

import cpw.mods.fml.common.IFuelHandler;
import cpw.mods.fml.common.registry.GameRegistry;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import minetweaker.MineTweakerAPI;
import minetweaker.api.item.IItemStack;
import minetweaker.api.minecraft.MineTweakerMC;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class FuelTweaker {
	public static final FuelTweaker INSTANCE = new FuelTweaker();

	private List<IFuelHandler> original;
	private final HashMap<Item, List<SetFuelPattern>> quickList = new HashMap<Item, List<SetFuelPattern>>();

	private FuelTweaker() {
	}

	public void register() {
		try {
			Field fuelHandlers = GameRegistry.class.getDeclaredField("fuelHandlers");
			fuelHandlers.setAccessible(true);
			original = (List<IFuelHandler>) fuelHandlers.get(null);
			List<IFuelHandler> modified = new ArrayList<IFuelHandler>();
			modified.add(new OverridingFuelHandler());
			fuelHandlers.set(null, modified);
		} catch (NoSuchFieldException ex) {
			System.out.println("[MineTweaker] Error: could not get GameRegistry fuel handlers field. Cannot use custom fuel values.");
		} catch (SecurityException ex) {
			System.out.println("[MineTweaker] Error: could not alter GameRegistry fuel handlers field. Cannot use custom fuel values.");
		} catch (IllegalAccessException ex) {
			System.out.println("[MineTweaker] Error: could not alter GameRegistry fuel handlers field. Cannot use custom fuel values.");
		}
	}

	public void addFuelPattern(SetFuelPattern pattern) {
		List<IItemStack> items = pattern.getPattern().getItems();
		if (items == null) {
			MineTweakerAPI.logError("Cannot set fuel for <*>");
			return;
		}

		for (IItemStack item : pattern.getPattern().getItems()) {
			ItemStack itemStack = MineTweakerMC.getItemStack(item);
			Item mcItem = itemStack.getItem();
			if (!quickList.containsKey(mcItem)) {
				quickList.put(mcItem, new ArrayList<SetFuelPattern>());
			}
			quickList.get(mcItem).add(pattern);
		}
	}

	public void removeFuelPattern(SetFuelPattern pattern) {
		for (IItemStack item : pattern.getPattern().getItems()) {
			ItemStack itemStack = MineTweakerMC.getItemStack(item);
			Item mcItem = itemStack.getItem();
			if (quickList.containsKey(mcItem)) {
				quickList.get(mcItem).remove(pattern);
			}
		}
	}

	private class OverridingFuelHandler implements IFuelHandler {
		@Override
		public int getBurnTime(ItemStack fuel) {
			if (quickList.containsKey(fuel.getItem())) {
				IItemStack stack = MineTweakerMC.getIItemStack(fuel);

				for (SetFuelPattern override : quickList.get(fuel.getItem())) {
					if (override.getPattern().matches(stack)) {
						return override.getValue();
					}
				}
			}

			int max = 0;
			for (IFuelHandler handler : original) {
				max = Math.max(max, handler.getBurnTime(fuel));
			}
			return max;
		}
	}
}
