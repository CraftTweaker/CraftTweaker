/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package minetweaker.mods.mfr.machines;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import minetweaker.IUndoableAction;
import minetweaker.MineTweakerAPI;
import minetweaker.annotations.ModOnly;
import minetweaker.api.item.IItemStack;
import minetweaker.api.item.WeightedItemStack;
import minetweaker.api.minecraft.MineTweakerMC;
import static minetweaker.mc164.util.MineTweakerPlatformUtils.getLivingEntityClass;
import minetweaker.mods.mfr.MFRHacks;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import powercrystals.minefactoryreloaded.api.FactoryRegistry;
import powercrystals.minefactoryreloaded.api.IFactoryGrindable;
import powercrystals.minefactoryreloaded.api.MobDrop;
import stanhebben.zenscript.annotations.ZenClass;

/**
 *
 * @author Stan
 */
@ZenClass("mods.mfr.Grinder")
@ModOnly("MineFactoryReloaded")
public class Grinder {
	public static void addBlacklist(String entityClassName) {
		Class<?> entityClass = getLivingEntityClass(entityClassName);
		MineTweakerAPI.apply(new AddBlacklistAction(entityClass));
	}
	
	public static void removeBlacklist(String entityClassName) {
		Class<?> entityClass = getLivingEntityClass(entityClassName);
		if (MFRHacks.grindableBlacklist == null) {
			MineTweakerAPI.logWarning("Cannot remove MFR Grinder blacklist entries");
		} else if (!MFRHacks.grindableBlacklist.contains(entityClass)) {
			MineTweakerAPI.logWarning(entityClassName + " is not blacklisted in the MFR Grinder");
		} else {
			MineTweakerAPI.apply(new RemoveBlacklistAction(entityClass));
		}
	}
	
	public static void addGrindable(String entityClassName) {
		addGrindable(entityClassName, new WeightedItemStack[0]);
	}
	
	public static void addGrindable(String entityClassName, WeightedItemStack possibleDrop) {
		addGrindable(entityClassName, new WeightedItemStack[] { possibleDrop });
	}
	
	public static void addGrindable(String entityClassName, WeightedItemStack[] possibleDrops) {
		Class<?> entityClass = getLivingEntityClass(entityClassName);
		MineTweakerAPI.apply(new AddGrindableAction(entityClass, possibleDrops));
	}
	
	public static void removeGrindable(String entityClassName) {
		Class<?> entityClass = getLivingEntityClass(entityClassName);
		if (MFRHacks.grindables == null) {
			MineTweakerAPI.logWarning("Cannot remove MFR grindables");
		} else if (!MFRHacks.grindables.containsKey(entityClass)) {
			MineTweakerAPI.logError("No such grindable entity: " + entityClassName);
		} else {
			MineTweakerAPI.apply(new RemoveGrindableAction(entityClass));
		}
	}
	
	// #######################
	// ### Private classes ###
	// #######################
	
	private static class SimpleGrindable implements IFactoryGrindable {
		private final Class<?> entityClass;
		private final WeightedItemStack[] drops;
		
		public SimpleGrindable(Class<?> entityClass, WeightedItemStack[] drops) {
			this.entityClass = entityClass;
			this.drops = drops;
		}

		@Override
		public Class<?> getGrindableEntity() {
			return entityClass;
		}

		@Override
		public List<MobDrop> grind(World world, EntityLivingBase entity, Random random) {
			List<MobDrop> dropList = new ArrayList<MobDrop>();
			for (IItemStack itemStack : WeightedItemStack.pickRandomDrops(random, drops)) {
				dropList.add(new MobDrop(1, MineTweakerMC.getItemStack(itemStack)));
			}
			return dropList;
		}

		@Override
		public boolean processEntity(EntityLivingBase entity) {
			entity.attackEntityFrom(DamageSource.generic, entity.getMaxHealth() * 20);
			return entity.isDead;
		}
	}
	
	// ######################
	// ### Action classes ###
	// ######################
	
	private static class AddBlacklistAction implements IUndoableAction {
		private final Class<?> entityClass;
		
		public AddBlacklistAction(Class<?> entityClass) {
			this.entityClass = entityClass;
		}
		
		@Override
		public void apply() {
			FactoryRegistry.registerGrinderBlacklist(entityClass);
		}

		@Override
		public boolean canUndo() {
			return MFRHacks.grindableBlacklist != null;
		}

		@Override
		public void undo() {
			MFRHacks.grindableBlacklist.remove(entityClass);
		}

		@Override
		public String describe() {
			return "Adding " + entityClass.getName() + " to the MFR Grinder blacklist";
		}

		@Override
		public String describeUndo() {
			return "Removing " + entityClass.getName() + " from the MFR Grinder blacklist";
		}

		@Override
		public Object getOverrideKey() {
			return null;
		}
	}
	
	private static class RemoveBlacklistAction implements IUndoableAction {
		private final Class<?> entityClass;
		
		public RemoveBlacklistAction(Class<?> entityClass) {
			this.entityClass = entityClass;
		}

		@Override
		public void apply() {
			MFRHacks.grindableBlacklist.remove(entityClass);
		}

		@Override
		public boolean canUndo() {
			return true;
		}

		@Override
		public void undo() {
			FactoryRegistry.registerGrinderBlacklist(entityClass);
		}

		@Override
		public String describe() {
			return "Removing " + entityClass + " from the MFR Grinder blacklist";
		}

		@Override
		public String describeUndo() {
			return "Restoring " + entityClass + " to the MFR Grinder blacklist";
		}

		@Override
		public Object getOverrideKey() {
			return null;
		}
	}
	
	private static class AddGrindableAction implements IUndoableAction {
		private final SimpleGrindable grindable;
		
		public AddGrindableAction(Class<?> entityClass, WeightedItemStack[] possibleDrops) {
			grindable = new SimpleGrindable(entityClass, possibleDrops);
		}

		@Override
		public void apply() {
			FactoryRegistry.registerGrindable(grindable);
		}

		@Override
		public boolean canUndo() {
			return MFRHacks.grindables != null;
		}

		@Override
		public void undo() {
			MFRHacks.grindables.remove(grindable.entityClass);
		}

		@Override
		public String describe() {
			return "Adding MFR Grindable " + grindable.entityClass.getName();
		}

		@Override
		public String describeUndo() {
			return "Removing MFR Grindable " + grindable.entityClass.getName();
		}

		@Override
		public Object getOverrideKey() {
			return null;
		}
	}
	
	private static class RemoveGrindableAction implements IUndoableAction {
		private final Class<?> entityClass;
		private final IFactoryGrindable grindable;
		
		public RemoveGrindableAction(Class<?> entityClass) {
			this.entityClass = entityClass;
			grindable = MFRHacks.grindables.get(entityClass);
		}
		
		@Override
		public void apply() {
			MFRHacks.grindables.remove(entityClass);
		}

		@Override
		public boolean canUndo() {
			return true;
		}

		@Override
		public void undo() {
			FactoryRegistry.registerGrindable(grindable);
		}

		@Override
		public String describe() {
			return "Removing grindable " + entityClass.getName();
		}

		@Override
		public String describeUndo() {
			return "Restoring grindable " + entityClass.getName();
		}

		@Override
		public Object getOverrideKey() {
			return null;
		}
	}
}
