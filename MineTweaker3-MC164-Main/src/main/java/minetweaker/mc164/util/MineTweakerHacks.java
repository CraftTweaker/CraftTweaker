/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package minetweaker.mc164.util;

import java.io.File;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import minetweaker.MineTweakerAPI;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.inventory.SlotCrafting;import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;

/**
 * Common class for all runtime hacks (stuff requiring reflection). It is not
 * unexpected to have it break with new minecraft versions and may need regular
 * adjustment - as such, those have been collected here.
 * 
 * @author Stan Hebben
 */
public class MineTweakerHacks {
	private static final Field NBTTAGCOMPOUND_TAGMAP;
	private static final Field OREDICTIONARY_ORESTACKS;
	private static final Field MINECRAFTSERVER_ANVILFILE;
	private static final Field SHAPEDORERECIPE_WIDTH;
	
	private static final Field INVENTORYCRAFTING_EVENTHANDLER;
	private static final Field SLOTCRAFTING_PLAYER;
	
	static {
		NBTTAGCOMPOUND_TAGMAP = getField(NBTTagCompound.class, MineTweakerObfuscation.NBTTAGCOMPOUND_TAGMAP);
		OREDICTIONARY_ORESTACKS = getField(OreDictionary.class, MineTweakerObfuscation.OREDICTIONARY_ORESTACKS);
		MINECRAFTSERVER_ANVILFILE = getField(MinecraftServer.class, MineTweakerObfuscation.MINECRAFTSERVER_ANVILFILE);
		SHAPEDORERECIPE_WIDTH = getField(ShapedOreRecipe.class, new String[] {"width"});
		
		INVENTORYCRAFTING_EVENTHANDLER = getField(InventoryCrafting.class, MineTweakerObfuscation.INVENTORYCRAFTING_EVENTHANDLER);
		SLOTCRAFTING_PLAYER = getField(SlotCrafting.class, MineTweakerObfuscation.SLOTCRAFTING_PLAYER);
	}
	
	private MineTweakerHacks() {}
	
	public static Map<String, NBTBase> getNBTCompoundMap(NBTTagCompound tag) {
		try {
			return (Map<String, NBTBase>) NBTTAGCOMPOUND_TAGMAP.get(tag);
		} catch (IllegalArgumentException ex) {
			return null;
		} catch (IllegalAccessException ex) {
			return null;
		}
	}
	
	public static Map<Integer, List<ItemStack>> getOreStacks() {
		try {
			return (Map<Integer, List<ItemStack>>) OREDICTIONARY_ORESTACKS.get(null);
		} catch (IllegalAccessException ex) {
			MineTweakerAPI.logger.logError("ERROR - could not load ore dictionary stacks!");
			return null;
		}
	}
	
	public static File getAnvilFile(MinecraftServer server) {
		try {
			return (File) MINECRAFTSERVER_ANVILFILE.get(server);
		} catch (IllegalAccessException ex) {
			MineTweakerAPI.logger.logError("could not load anvil file!");
			return null;
		}
	}
	
	public static File getWorldDirectory(MinecraftServer server) {
		if (server.isDedicatedServer()) {
			return server.getFile("world");
		} else {
			File worldsDir = getAnvilFile(server);
		    if (worldsDir == null) return null;
		    
		    return new File(worldsDir, server.getFolderName());
		}
	}
	
	public static int getShapedOreRecipeWidth(ShapedOreRecipe recipe) {
		try {
			return SHAPEDORERECIPE_WIDTH.getInt(recipe);
		} catch (IllegalAccessException ex) {
			MineTweakerAPI.logger.logError("could not load anvil file!");
			return 3;
		}
	}
	
	public static Container getCraftingContainer(InventoryCrafting inventory) {
		try {
			return (Container) INVENTORYCRAFTING_EVENTHANDLER.get(inventory);
		} catch (IllegalAccessException ex) {
			MineTweakerAPI.logger.logError("could not get inventory eventhandler");
			return null;
		}
	}
	
	public static EntityPlayer getCraftingSlotPlayer(SlotCrafting slot) {
		try {
			return (EntityPlayer) SLOTCRAFTING_PLAYER.get(slot);
		} catch (IllegalAccessException ex) {
			MineTweakerAPI.logger.logError("could not get inventory eventhandler");
			return null;
		}
	}
	
	// #######################
	// ### Private Methods ###
	// #######################
	
	private static Field getField(Class cls, String[] names) {
		for (String name : names) {
			try {
				Field field = cls.getDeclaredField(name);
				field.setAccessible(true);
				return field;
			} catch (NoSuchFieldException ex) {
			} catch (SecurityException ex) {
			}
		}
		
		return null;
	}
}
