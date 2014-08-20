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
import static minetweaker.mc1710.util.MineTweakerPlatformUtils.getLivingEntityClass;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import powercrystals.minefactoryreloaded.MFRRegistry;
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
		Class<? extends EntityLivingBase> entityClass = getLivingEntityClass(entityClassName);
		MineTweakerAPI.apply(new AddBlacklistAction(entityClass));
	}
	
	public static void removeBlacklist(String entityClassName) {
		Class<? extends EntityLivingBase> entityClass = getLivingEntityClass(entityClassName);
		if (!MFRRegistry.getGrinderBlacklist().contains(entityClass)) {
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
		Class<? extends EntityLivingBase> entityClass = getLivingEntityClass(entityClassName);
		MineTweakerAPI.apply(new AddGrindableAction(entityClass, possibleDrops));
	}
	
	public static void removeGrindable(String entityClassName) {
		Class<? extends EntityLivingBase> entityClass = getLivingEntityClass(entityClassName);
		if (!MFRRegistry.getGrindables().containsKey(entityClass)) {
			MineTweakerAPI.logError("No such grindable entity: " + entityClassName);
		} else {
			MineTweakerAPI.apply(new RemoveGrindableAction(entityClass));
		}
	}
	
	// #######################
	// ### Private classes ###
	// #######################
	
	private static class SimpleGrindable implements IFactoryGrindable {
		private final Class<? extends EntityLivingBase> entityClass;
		private final WeightedItemStack[] drops;
		
		public SimpleGrindable(Class<? extends EntityLivingBase> entityClass, WeightedItemStack[] drops) {
			this.entityClass = entityClass;
			this.drops = drops;
		}

		@Override
		public Class<? extends EntityLivingBase> getGrindableEntity() {
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
		private final Class<? extends EntityLivingBase> entityClass;
		
		public AddBlacklistAction(Class<? extends EntityLivingBase> entityClass) {
			this.entityClass = entityClass;
		}
		
		@Override
		public void apply() {
			MFRRegistry.registerGrinderBlacklist(entityClass);
		}

		@Override
		public boolean canUndo() {
			return true;
		}

		@Override
		public void undo() {
			MFRRegistry.getGrinderBlacklist().remove(entityClass);
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
		private final Class<? extends EntityLivingBase> entityClass;
		
		public RemoveBlacklistAction(Class<? extends EntityLivingBase> entityClass) {
			this.entityClass = entityClass;
		}

		@Override
		public void apply() {
			MFRRegistry.getGrinderBlacklist().remove(entityClass);
		}

		@Override
		public boolean canUndo() {
			return true;
		}

		@Override
		public void undo() {
			MFRRegistry.registerGrinderBlacklist(entityClass);
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
		
		public AddGrindableAction(Class<? extends EntityLivingBase> entityClass, WeightedItemStack[] possibleDrops) {
			grindable = new SimpleGrindable(entityClass, possibleDrops);
		}

		@Override
		public void apply() {
			MFRRegistry.registerGrindable(grindable);
		}

		@Override
		public boolean canUndo() {
			return true;
		}

		@Override
		public void undo() {
			MFRRegistry.getGrindables().remove(grindable.entityClass);
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
		private final Class<? extends EntityLivingBase> entityClass;
		private final IFactoryGrindable grindable;
		
		public RemoveGrindableAction(Class<? extends EntityLivingBase> entityClass) {
			this.entityClass = entityClass;
			grindable = MFRRegistry.getGrindables().get(entityClass);
		}
		
		@Override
		public void apply() {
			MFRRegistry.getGrindables().remove(entityClass);
		}

		@Override
		public boolean canUndo() {
			return true;
		}

		@Override
		public void undo() {
			MFRRegistry.registerGrindable(grindable);
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
