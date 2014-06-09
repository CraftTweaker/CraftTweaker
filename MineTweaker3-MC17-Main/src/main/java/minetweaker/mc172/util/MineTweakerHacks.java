/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package minetweaker.mc172.util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import minetweaker.MineTweakerAPI;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.oredict.OreDictionary;

/**
 * Common class for all runtime hacks (stuff requiring reflection). It is not
 * unexpected to have it break with new minecraft versions and may need regular
 * adjustment - as such, those have been collected here.
 * 
 * @author Stan Hebben
 */
public class MineTweakerHacks {
	private static final Field NBTTAGLIST_TAGLIST;
	private static final Field OREDICTIONARY_ORESTACKS;
	
	static {
		NBTTAGLIST_TAGLIST = getField(NBTTagList.class, MineTweakerObfuscation.NBTTAGLIST_TAGLIST);
		OREDICTIONARY_ORESTACKS = getField(OreDictionary.class, MineTweakerObfuscation.OREDICTIONARY_ORESTACKS);
	}
	
	private MineTweakerHacks() {}
	
	public static List<NBTBase> getTagList(NBTTagList list) {
		if (NBTTAGLIST_TAGLIST == null) {
			return null;
		}
		try {
			return (List) NBTTAGLIST_TAGLIST.get(list);
		} catch (IllegalArgumentException ex) {
			return null;
		} catch (IllegalAccessException ex) {
			return null;
		}
	}
	
	public static List<IRecipe> getRecipes() {
		Field field = getField(CraftingManager.class, MineTweakerObfuscation.CRAFTINGMANAGER_RECIPES);
		try {
			return (List<IRecipe>) field.get(CraftingManager.getInstance());
		} catch (IllegalAccessException ex) {
			MineTweakerAPI.logger.logError("EPIC ERROR - could not load recipe list!");
			return null;
		}
	}
	
	public static HashMap<Integer, ArrayList<ItemStack>> getOreStacks() {
		try {
			return (HashMap<Integer, ArrayList<ItemStack>>) OREDICTIONARY_ORESTACKS.get(null);
		} catch (IllegalAccessException ex) {
			MineTweakerAPI.logger.logError("ERROR - could not load ore dictionary stacks!");
			return null;
		}
	}
	
	private static Field getField(Class cls, String[] names) {
		for (String name : names) {
			try {
				Field field = cls.getField(name);
				field.setAccessible(true);
				return field;
			} catch (NoSuchFieldException ex) {
			} catch (SecurityException ex) {
			}
		}
		
		return null;
	}
}
