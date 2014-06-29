/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package minetweaker.mc172.furnace;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import net.minecraft.item.ItemStack;

import cpw.mods.fml.common.IFuelHandler;
import cpw.mods.fml.common.registry.GameRegistry;
import minetweaker.MineTweakerAPI;
import minetweaker.api.item.IItemStack;
import static minetweaker.api.minecraft.MineTweakerMC.getIItemStack;

public class FuelTweaker {
	public static final FuelTweaker INSTANCE = new FuelTweaker();
	
	private List<IFuelHandler> original;
	private final HashMap<String, List<SetFuelPattern>> quickList = new HashMap<String, List<SetFuelPattern>>();
	
	private FuelTweaker() {}

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
			MineTweakerAPI.logger.logError("Cannot set fuel for <*>");
			return;
		}
		
		for (IItemStack item : pattern.getPattern().getItems()) {
			if (!quickList.containsKey(item.getName())) {
				quickList.put(item.getName(), new ArrayList<SetFuelPattern>());
			}
			quickList.get(item.getName()).add(pattern);
		}
	}
	
	public void removeFuelPattern(SetFuelPattern pattern) {
		for (IItemStack item : pattern.getPattern().getItems()) {
			if (!quickList.containsKey(item.getName())) {
				quickList.put(item.getName(), new ArrayList<SetFuelPattern>());
			}
			quickList.get(item.getName()).add(pattern);
		}
	}
	
	private class OverridingFuelHandler implements IFuelHandler {
		@Override
		public int getBurnTime(ItemStack fuel) {
			IItemStack stack = getIItemStack(fuel);
			String name = fuel.getUnlocalizedName();
			if (quickList.containsKey(name)) {
				for (SetFuelPattern override : quickList.get(name)) {
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
