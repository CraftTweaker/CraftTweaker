/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package minetweaker.mc1710.util;

import com.google.common.collect.BiMap;
import cpw.mods.fml.common.registry.EntityRegistry;
import java.io.File;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import minetweaker.MineTweakerAPI;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.inventory.SlotCrafting;import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.StringTranslate;
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
	private static final Field NBTTAGLIST_TAGLIST;
	private static final Field OREDICTIONARY_IDTOSTACK;
	private static final Field OREDICTIONARY_IDTOSTACKUN;
	private static final Field MINECRAFTSERVER_ANVILFILE;
	private static final Field SHAPEDORERECIPE_WIDTH;
	private static final Field INVENTORYCRAFTING_EVENTHANDLER;
	private static final Field SLOTCRAFTING_PLAYER;
	
	private static final Field ENTITYREGISTRY_CLASSREGISTRATIONS;
	
	static {
		NBTTAGLIST_TAGLIST = getField(NBTTagList.class, MineTweakerObfuscation.NBTTAGLIST_TAGLIST);
		OREDICTIONARY_IDTOSTACK = getField(OreDictionary.class, MineTweakerObfuscation.OREDICTIONARY_IDTOSTACK);
		OREDICTIONARY_IDTOSTACKUN = getField(OreDictionary.class, MineTweakerObfuscation.OREDICTIONARY_IDTOSTACKUN);
		MINECRAFTSERVER_ANVILFILE = getField(MinecraftServer.class, MineTweakerObfuscation.MINECRAFTSERVER_ANVILFILE);
		SHAPEDORERECIPE_WIDTH = getField(ShapedOreRecipe.class, new String[] {"width"});
		INVENTORYCRAFTING_EVENTHANDLER = getField(InventoryCrafting.class, MineTweakerObfuscation.INVENTORYCRAFTING_EVENTHANDLER);
		SLOTCRAFTING_PLAYER = getField(SlotCrafting.class, MineTweakerObfuscation.SLOTCRAFTING_PLAYER);
		
		ENTITYREGISTRY_CLASSREGISTRATIONS = getField(EntityRegistry.class, new String[] {"entityClassRegistrations"});
	}
	
	private MineTweakerHacks() {}
	
	public static BiMap<Class<? extends Entity>, EntityRegistry.EntityRegistration> getEntityClassRegistrations() {
		try {
			return (BiMap<Class<? extends Entity>, EntityRegistry.EntityRegistration>) ENTITYREGISTRY_CLASSREGISTRATIONS.get(EntityRegistry.instance());
		} catch (IllegalArgumentException ex) {
			return null;
		} catch (IllegalAccessException ex) {
			return null;
		}
	}
	
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
	
	public static List<ArrayList<ItemStack>> getOreIdStacks() {
		try {
			return (List<ArrayList<ItemStack>>) OREDICTIONARY_IDTOSTACK.get(null);
		} catch (IllegalAccessException ex) {
			MineTweakerAPI.logError("ERROR - could not load ore dictionary stacks!");
			return null;
		}
	}
	
	public static List<ArrayList<ItemStack>> getOreIdStacksUn() {
		try {
			return (List<ArrayList<ItemStack>>) OREDICTIONARY_IDTOSTACKUN.get(null);
		} catch (IllegalAccessException ex) {
			MineTweakerAPI.logError("ERROR - could not load ore dictionary stacks!");
			return null;
		}
	}
	
	public static File getAnvilFile(MinecraftServer server) {
		try {
			return (File) MINECRAFTSERVER_ANVILFILE.get(server);
		} catch (IllegalAccessException ex) {
			MineTweakerAPI.logError("could not load anvil file!");
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
			MineTweakerAPI.logError("could not load anvil file!");
			return 3;
		}
	}
	
	public static Container getCraftingContainer(InventoryCrafting inventory) {
		try {
			return (Container) INVENTORYCRAFTING_EVENTHANDLER.get(inventory);
		} catch (IllegalAccessException ex) {
			MineTweakerAPI.logError("could not get inventory eventhandler");
			return null;
		}
	}
	
	public static EntityPlayer getCraftingSlotPlayer(SlotCrafting slot) {
		try {
			return (EntityPlayer) SLOTCRAFTING_PLAYER.get(slot);
		} catch (IllegalAccessException ex) {
			MineTweakerAPI.logError("could not get inventory eventhandler");
			return null;
		}
	}
	
	public static StringTranslate getStringTranslateInstance() {
		try {
			Field field = getField(StringTranslate.class, MineTweakerObfuscation.STRINGTRANSLATE_INSTANCE);
			return (StringTranslate) field.get(null);
		} catch (IllegalAccessException ex) {
			MineTweakerAPI.logError("could not get string translator");
			return null;
		}
	}
	
	public static <T> T getPrivateStaticObject(Class<?> cls, String name) {
		try {
			Field field = cls.getDeclaredField(name);
			field.setAccessible(true);
			return (T) field.get(null);
		} catch (NoSuchFieldException ex) {
			
		} catch (SecurityException ex) {
			
		} catch (IllegalAccessException ex) {
			
		}
		
		return null;
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
