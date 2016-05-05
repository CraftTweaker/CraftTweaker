/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package minetweaker.mc18.game;

import minetweaker.IUndoableAction;
import minetweaker.MineTweakerAPI;
import minetweaker.api.block.IBlockDefinition;
import minetweaker.api.entity.IEntityDefinition;
import minetweaker.api.game.IGame;
import minetweaker.api.item.IItemDefinition;
import minetweaker.api.liquid.ILiquidDefinition;
import minetweaker.api.minecraft.MineTweakerMC;
import minetweaker.api.world.IBiome;
import minetweaker.mc18.entity.MCEntityDefinition;
import minetweaker.mc18.item.MCItemDefinition;
import minetweaker.mc18.liquid.MCLiquidDefinition;
import minetweaker.mc18.util.MineTweakerHacks;
import minetweaker.mc18.util.MineTweakerPlatformUtils;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.registry.EntityRegistry;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

// TODO Find this
// import minetweaker.mc18.GuiCannotRemodify;

/**
 * @author Stan
 */
public class MCGame implements IGame{
    public static final MCGame INSTANCE = new MCGame();
    private static final Map<String, String> TRANSLATIONS = MineTweakerHacks.getTranslations();
    private boolean locked = false;

    public MCGame(){
    }

    @Override
    public List<IItemDefinition> getItems(){
        List<IItemDefinition> result = new ArrayList<IItemDefinition>();
        for(ResourceLocation item : (Set<ResourceLocation>) Item.itemRegistry.getKeys()){
            result.add(new MCItemDefinition(item.toString(), Item.itemRegistry.getObject(item)));
        }
        return result;
    }

    @Override
    public List<IBlockDefinition> getBlocks(){
        List<IBlockDefinition> result = new ArrayList<IBlockDefinition>();
        for(ResourceLocation block : (Set<ResourceLocation>) Block.blockRegistry.getKeys()){
            result.add(MineTweakerMC.getBlockDefinition(Block.blockRegistry.getObject(block)));
        }

        return result;
    }

    @Override
    public List<ILiquidDefinition> getLiquids(){
        List<ILiquidDefinition> result = new ArrayList<ILiquidDefinition>();
        for(Map.Entry<String, Fluid> entry : FluidRegistry.getRegisteredFluids().entrySet()){
            result.add(new MCLiquidDefinition(entry.getValue()));
        }
        return result;
    }

    @Override
    public List<IBiome> getBiomes(){
        List<IBiome> result = new ArrayList<IBiome>();
        for(IBiome biome : MineTweakerMC.biomes){
            if(biome != null){
                result.add(biome);
            }
        }
        return result;
    }

    @Override
    public List<IEntityDefinition> getEntities(){
        List<IEntityDefinition> result = new ArrayList<IEntityDefinition>();

        for(EntityRegistry.EntityRegistration entityRegistration : MineTweakerHacks.getEntityClassRegistrations().values()){
            result.add(new MCEntityDefinition(entityRegistration));
        }

        return result;
    }

    @Override
    public void setLocalization(String key, String value){
        MineTweakerAPI.apply(new SetTranslation(null, key, value));
    }

    @Override
    public void setLocalization(String lang, String key, String value){
        MineTweakerAPI.apply(new SetTranslation(lang, key, value));
    }

    @Override
    public String localize(String key){
        return StatCollector.translateToLocal(key);
    }

    @Override
    public String localize(String key, String lang){
        return StatCollector.translateToLocal(key);
    }

    @Override
    public void lock(){
        locked = true;
    }

    @Override
    public boolean isLocked(){
        return locked;
    }

    @Override
    public void signalLockError(){
        MineTweakerAPI.getLogger().logError("Reload of scripts blocked (script lock)");

        if(Minecraft.isGuiEnabled()){
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
    private static class SetTranslation implements IUndoableAction{
        private final String lang;
        private final String key;
        private final String text;
        private String original;
        private boolean added;

        public SetTranslation(String lang, String key, String text){
            this.lang = lang;
            this.key = key;
            this.text = text;
        }

        @Override
        public void apply(){
            if(lang == null || MineTweakerPlatformUtils.isLanguageActive(lang)){
                original = TRANSLATIONS.get(key);
                TRANSLATIONS.put(key, text);
                added = true;
            }else{
                added = false;
            }
        }

        @Override
        public boolean canUndo(){
            return TRANSLATIONS != null;
        }

        @Override
        public void undo(){
            if(added){
                if(original == null){
                    TRANSLATIONS.remove(key);
                }else{
                    TRANSLATIONS.put(key, original);
                }
            }
        }

        @Override
        public String describe(){
            return "Setting localization for the key: " + key + " to " + text;
        }

        @Override
        public String describeUndo(){
            return "Setting localization for the key: " + key + " to " + original;
        }

        @Override
        public Object getOverrideKey(){
            return null;
        }
    }
}
