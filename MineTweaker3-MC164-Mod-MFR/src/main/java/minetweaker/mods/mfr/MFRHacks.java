/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package minetweaker.mods.mfr;

import java.util.List;
import java.util.Map;
import static minetweaker.mc164.util.MineTweakerHacks.getPrivateStaticObject;
import net.minecraft.item.ItemStack;
import powercrystals.core.random.WeightedRandomItemStack;
import powercrystals.minefactoryreloaded.MFRRegistry;
import powercrystals.minefactoryreloaded.api.IFactoryFertilizable;
import powercrystals.minefactoryreloaded.api.IFactoryFertilizer;
import powercrystals.minefactoryreloaded.api.IFactoryFruit;
import powercrystals.minefactoryreloaded.api.IFactoryGrindable;
import powercrystals.minefactoryreloaded.api.IFactoryHarvestable;
import powercrystals.minefactoryreloaded.api.IFactoryPlantable;
import powercrystals.minefactoryreloaded.api.IFactoryRanchable;
import powercrystals.minefactoryreloaded.api.IMobEggHandler;
import powercrystals.minefactoryreloaded.api.IRandomMobProvider;
import powercrystals.minefactoryreloaded.api.ISafariNetHandler;
import powercrystals.minefactoryreloaded.api.rednet.IRedNetLogicCircuit;

/**
 *
 * @author Stanneke
 */
public class MFRHacks {
	public static Map<Integer, IFactoryPlantable> plantables = null;
	public static Map<Integer, IFactoryHarvestable> harvestables = null;
	public static Map<Integer, IFactoryFertilizer> fertilizers = null;
	public static Map<Integer, IFactoryFertilizable> fertilizables = null;
	public static Map<Class<?>, IFactoryRanchable> ranchables = null;
	public static Map<Class<?>, IFactoryGrindable> grindables = null;
	public static Map<Class<?>, List<ItemStack>> breederFoods = null;
	public static List<Integer> fruitLogBlocks = null;
	public static Map<Integer, IFactoryFruit> fruitBlocks = null;
	public static List<WeightedRandomItemStack> sludgeDrops = null;
	public static List<IMobEggHandler> eggHandlers = null;
	public static List<ISafariNetHandler> safariNetHandlers = null;
	public static List<String> rubberTreeBiomes = null;
	public static List<Class<?>> safariNetBlacklist = null;
	public static List<IRandomMobProvider> randomMobProviders = null;
	public static List<WeightedRandomItemStack> laserOres = null;
	public static List<Class<?>> grindableBlacklist = null;
	public static List<Class<?>> autoSpawnerBlacklist = null;
	public static List<Class<?>> slaughterhouseBlacklist = null;
	public static List<Class<?>> conveyerBlacklist = null;
	public static Map<Integer, List<ItemStack>> laserPreferredOres = null;
	public static List<IRedNetLogicCircuit> redNetCircuits = null;
	
	static {
		try {
			Class<?> registry = MFRRegistry.class;
			
			plantables = getPrivateStaticObject(registry, "_plantables");
			harvestables = getPrivateStaticObject(registry, "_harvestables");
			fertilizers = getPrivateStaticObject(registry, "_fertilizers");
			fertilizables = getPrivateStaticObject(registry, "_fertilizables");
			ranchables = getPrivateStaticObject(registry, "_ranchables");
			grindables = getPrivateStaticObject(registry, "_grindables");
			breederFoods = getPrivateStaticObject(registry, "_breederFoods");
			fruitLogBlocks = getPrivateStaticObject(registry, "_fruitLogBlocks");
			fruitBlocks = getPrivateStaticObject(registry, "_fruitBlocks");
			sludgeDrops = getPrivateStaticObject(registry, "_sludgeDrops");
			eggHandlers = getPrivateStaticObject(registry, "_eggHandlers");
			safariNetHandlers = getPrivateStaticObject(registry, "_safariNetHandlers");
			rubberTreeBiomes = getPrivateStaticObject(registry, "_rubberTreeBiomes");
			safariNetBlacklist = getPrivateStaticObject(registry, "_safariNetBlacklist");
			randomMobProviders = getPrivateStaticObject(registry, "_randomMobProviders");
			laserOres = getPrivateStaticObject(registry, "_laserOres");
			grindableBlacklist = getPrivateStaticObject(registry, "_grindableBlacklist");
			autoSpawnerBlacklist = getPrivateStaticObject(registry, "_autoSpawnerBlacklist");
			slaughterhouseBlacklist = getPrivateStaticObject(registry, "_slaughterhouseBlacklist");
			conveyerBlacklist = getPrivateStaticObject(registry, "_conveyerBlacklist");
			laserPreferredOres = getPrivateStaticObject(registry, "_laserPreferredOres");
			redNetCircuits = getPrivateStaticObject(registry, "_redNetLogicCircuits");
		} catch (ClassCastException ex) {
		}
	}
	
	private MFRHacks() {}
}
