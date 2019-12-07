/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package minetweaker.mc1710.game;

import com.google.common.collect.*;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import minetweaker.IUndoableAction;
import minetweaker.MineTweakerAPI;
import minetweaker.api.block.IBlockDefinition;
import minetweaker.api.entity.IEntityDefinition;
import minetweaker.api.event.*;
import minetweaker.api.game.IGame;
import minetweaker.api.item.IItemDefinition;
import minetweaker.api.liquid.ILiquidDefinition;
import minetweaker.api.minecraft.MineTweakerMC;
import minetweaker.api.world.IBiome;
// TODO Find this
// import GuiCannotRemodify;
import minetweaker.mc1710.entity.MCEntityDefinition;
import minetweaker.mc1710.item.MCItemDefinition;
import minetweaker.mc1710.liquid.MCLiquidDefinition;
import minetweaker.mc1710.util.MineTweakerHacks;
import minetweaker.mc1710.util.MineTweakerPlatformUtils;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.item.Item;
import net.minecraft.util.*;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;

/**
 *
 * @author Stan
 */
public class MCGame implements IGame {
	private static final Map<String, String> TRANSLATIONS = MineTweakerHacks.getTranslations();

	public static final MCGame INSTANCE = new MCGame();

	private boolean locked = false;
    private static final List<IEntityDefinition> ENTITY_DEFINITIONS = new ArrayList<IEntityDefinition>();
    
    private MCGame() {
	}

	@Override
	public List<IItemDefinition> getItems() {
		List<IItemDefinition> result = new ArrayList<IItemDefinition>();
		for (String item : (Set<String>) Item.itemRegistry.getKeys()) {
			result.add(new MCItemDefinition(item, (Item) Item.itemRegistry.getObject(item)));
		}
		return result;
	}

	@Override
	public List<IBlockDefinition> getBlocks() {
		List<IBlockDefinition> result = new ArrayList<IBlockDefinition>();
		for (String block : (Set<String>) Block.blockRegistry.getKeys()) {
			result.add(MineTweakerMC.getBlockDefinition((Block) Block.blockRegistry.getObject(block)));
		}

		return result;
	}

	@Override
	public List<ILiquidDefinition> getLiquids() {
		List<ILiquidDefinition> result = new ArrayList<ILiquidDefinition>();
		for (Map.Entry<String, Fluid> entry : FluidRegistry.getRegisteredFluids().entrySet()) {
			result.add(new MCLiquidDefinition(entry.getValue()));
		}
		return result;
	}

	@Override
	public List<IBiome> getBiomes() {
		List<IBiome> result = new ArrayList<IBiome>();
		for (IBiome biome : MineTweakerMC.biomes) {
			if (biome != null) {
				result.add(biome);
			}
		}
		return result;
	}
    
    @Override
    public List<IEntityDefinition> getEntities() {
        final Set<EntityRegistry.EntityRegistration> values = MineTweakerHacks.getEntityClassRegistrations().values();
        if(Iterables.size(values) != ENTITY_DEFINITIONS.size()) {
            ENTITY_DEFINITIONS.clear();
            for(EntityRegistry.EntityRegistration entry : values) {
                ENTITY_DEFINITIONS.add(new MCEntityDefinition(entry));
            }
        }
        return ENTITY_DEFINITIONS;
    }
    
    @Override
    public IEntityDefinition getEntity(String entityName) {
        for(IEntityDefinition ent : getEntities()) {
            if(ent.getName().equalsIgnoreCase(entityName)) {
                return ent;
            }
        }
        boolean needsReloading = false;
        for(EntityRegistry.EntityRegistration res : MineTweakerHacks.getEntityClassRegistrations().values()) {
            if(res.getEntityName().equalsIgnoreCase(entityName)) {
                needsReloading = true;
                break;
            }
        }
        if(needsReloading) {
            ENTITY_DEFINITIONS.clear();
            for(EntityRegistry.EntityRegistration entry : MineTweakerHacks.getEntityClassRegistrations().values()) {
                ENTITY_DEFINITIONS.add(new MCEntityDefinition(entry));
            }
        }
    
        for(IEntityDefinition entity : getEntities()) {
            if(entity.getName().equalsIgnoreCase(entityName)) {
                return entity;
            }
        }
        return null;
        
    }
    
    @Override
	public void setLocalization(String key, String value) {
		MineTweakerAPI.apply(new SetTranslation(null, key, value));
	}

	@Override
	public void setLocalization(String lang, String key, String value) {
		MineTweakerAPI.apply(new SetTranslation(lang, key, value));
	}

	@Override
	public String localize(String key) {
		return LanguageRegistry.instance().getStringLocalization(key);
	}

	@Override
	public String localize(String key, String lang) {
		return LanguageRegistry.instance().getStringLocalization(key, lang);
	}

	@Override
	public void lock()
	{
		locked = true;
	}

	@Override
	public boolean isLocked()
	{
		return locked;
	}

	@Override
	public void signalLockError()
	{
		MineTweakerAPI.getLogger().logError("Reload of scripts blocked (script lock)");

		if (Minecraft.isGuiEnabled()) {
			// Commented out due to unresolved import
			/**
			 * Minecraft.getMinecraft().displayGuiScreen( new GuiCannotRemodify(
			 * "Minecraft has been tweaked for another server",
			 * "with modifications that cannot be rolled back.",
			 * "Please restart your game."));
			 **/
		}
	}

	// ######################
	// ### Action classes ###
	// ######################

	/**
	 * Ported from ModTweaker.
	 * 
	 * @author Joshiejack
	 */
	private static class SetTranslation implements IUndoableAction {
		private String original;
		private final String lang;
		private final String key;
		private final String text;
		private boolean added;

		public SetTranslation(String lang, String key, String text) {
			this.lang = lang;
			this.key = key;
			this.text = text;
		}

		@Override
		public void apply() {
			if (lang == null || MineTweakerPlatformUtils.isLanguageActive(lang)) {
				original = TRANSLATIONS.get(key);
				TRANSLATIONS.put(key, text);
				added = true;
			} else {
				added = false;
			}
		}

		@Override
		public boolean canUndo() {
			return TRANSLATIONS != null;
		}

		@Override
		public void undo() {
			if (added) {
				if (original == null) {
					TRANSLATIONS.remove(key);
				} else {
					TRANSLATIONS.put(key, original);
				}
			}
		}

		@Override
		public String describe() {
			return "Setting localization for the key: " + key + " to " + text;
		}

		@Override
		public String describeUndo() {
			return "Setting localization for the key: " + key + " to " + original;
		}

		@Override
		public Object getOverrideKey() {
			return null;
		}
	}
}
