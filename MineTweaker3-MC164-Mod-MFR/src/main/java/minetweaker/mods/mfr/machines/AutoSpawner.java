package minetweaker.mods.mfr.machines;

import minetweaker.IUndoableAction;
import minetweaker.MineTweakerAPI;
import minetweaker.annotations.ModOnly;
import minetweaker.mods.mfr.MFRHacks;
import static minetweaker.mc164.util.MineTweakerPlatformUtils.getLivingEntityClass;
import net.minecraft.entity.EntityList;
import powercrystals.minefactoryreloaded.api.FactoryRegistry;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

/**
 *
 * @author Stan
 */
@ZenClass("mods.mfr.AutoSpawner")
@ModOnly("MineFactoryReloaded")
public class AutoSpawner {
	@ZenMethod
	public static void addBlacklist(String entityClassName) {
		MineTweakerAPI.apply(new AutoSpawnerAddBlacklistAction(getLivingEntityClass(entityClassName)));
	}
	
	@ZenMethod
	public static void removeBlacklist(String entityClassName) {
		MineTweakerAPI.apply(new AutoSpawnerRemoveBlacklistAction(getLivingEntityClass(entityClassName)));
	}
	
	// ######################
	// ### Action classes ###
	// ######################
	
	private static class AutoSpawnerAddBlacklistAction implements IUndoableAction {
		private final Class<?> entityClass;
		private final boolean existed;

		public AutoSpawnerAddBlacklistAction(Class<?> entityClass) {
			this.entityClass = entityClass;
			existed = MFRHacks.autoSpawnerBlacklist == null ? false : MFRHacks.autoSpawnerBlacklist.contains(entityClass);
		}

		@Override
		public void apply() {
			if (!existed)
				FactoryRegistry.registerAutoSpawnerBlacklist((String) EntityList.classToStringMapping.get(entityClass));
		}

		@Override
		public boolean canUndo() {
			return MFRHacks.autoSpawnerBlacklist != null;
		}

		@Override
		public void undo() {
			if (!existed)
				MFRHacks.autoSpawnerBlacklist.remove(entityClass);
		}

		@Override
		public String describe() {
			return "Adding auto-spawner blacklist " + entityClass.getCanonicalName();
		}

		@Override
		public String describeUndo() {
			return "Removing auto-spawner blacklist " + entityClass.getCanonicalName();
		}

		@Override
		public Object getOverrideKey() {
			return null;
		}
	}
	
	private static class AutoSpawnerRemoveBlacklistAction implements IUndoableAction {
		private final Class<?> entityClass;
		private final boolean existed;

		public AutoSpawnerRemoveBlacklistAction(Class<?> entityClass) {
			this.entityClass = entityClass;
			existed = MFRHacks.autoSpawnerBlacklist.contains(entityClass);
		}

		@Override
		public void apply() {
			if (existed)
				MFRHacks.autoSpawnerBlacklist.remove(entityClass);
		}

		@Override
		public boolean canUndo() {
			return true;
		}

		@Override
		public void undo() {
			if (existed) MFRHacks.autoSpawnerBlacklist.add(entityClass);
		}

		@Override
		public String describe() {
			return "Removing auto-spawner blacklist " + entityClass.getCanonicalName();
		}

		@Override
		public String describeUndo() {
			return "Restoring auto-spawner blacklist " + entityClass.getCanonicalName();
		}

		@Override
		public Object getOverrideKey() {
			return null;
		}
	}
}
