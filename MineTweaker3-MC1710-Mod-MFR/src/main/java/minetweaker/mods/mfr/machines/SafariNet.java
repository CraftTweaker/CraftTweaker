/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package minetweaker.mods.mfr.machines;

import minetweaker.IUndoableAction;
import minetweaker.MineTweakerAPI;
import minetweaker.annotations.ModOnly;
import static minetweaker.mc1710.util.MineTweakerPlatformUtils.getLivingEntityClass;
import net.minecraft.entity.EntityLivingBase;
import powercrystals.minefactoryreloaded.MFRRegistry;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

/**
 *
 * @author Stan
 */
@ZenClass("mods.mfr.SafariNet")
@ModOnly("MineFactoryReloaded")
public class SafariNet {
	@ZenMethod
	public static void addBlacklist(String entityClassName) {
		Class<? extends EntityLivingBase> entityClass = getLivingEntityClass(entityClassName);
		MineTweakerAPI.apply(new AddBlacklistAction(entityClass));
	}
	
	@ZenMethod
	public static void removeBlacklist(String entityClassName) {
		Class<? extends EntityLivingBase> entityClass = getLivingEntityClass(entityClassName);
		if (!MFRRegistry.getSafariNetBlacklist().contains(entityClass)) {
			MineTweakerAPI.logWarning(entityClassName + " is not in the safari net blacklist");
		} else {
			MineTweakerAPI.apply(new RemoveBlacklistAction(entityClass));
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
			MFRRegistry.registerSafariNetBlacklist(entityClass);
		}

		@Override
		public boolean canUndo() {
			return true;
		}

		@Override
		public void undo() {
			MFRRegistry.getSafariNetBlacklist().remove(entityClass);
		}

		@Override
		public String describe() {
			return "Blacklisting " + entityClass.getName() + " in the safari net";
		}

		@Override
		public String describeUndo() {
			return "Removing " + entityClass.getName() + " from the safari net blacklist";
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
			MFRRegistry.getSafariNetBlacklist().remove(entityClass);
		}

		@Override
		public boolean canUndo() {
			return true;
		}

		@Override
		public void undo() {
			MFRRegistry.getSafariNetBlacklist().add(entityClass);
		}

		@Override
		public String describe() {
			return "Removing " + entityClass.getName() + " from the safari net blacklist";
		}

		@Override
		public String describeUndo() {
			return "Restoring " + entityClass.getName() + " to the safari net blacklist";
		}

		@Override
		public Object getOverrideKey() {
			return null;
		}
	}
}
