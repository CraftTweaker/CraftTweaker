package crafttweaker.mc1120.game;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.api.block.IBlockDefinition;
import crafttweaker.api.entity.IEntityDefinition;
import crafttweaker.api.game.IGame;
import crafttweaker.api.item.IItemDefinition;
import crafttweaker.api.liquid.ILiquidDefinition;
import crafttweaker.api.minecraft.CraftTweakerMC;
import crafttweaker.api.potions.IPotion;
import crafttweaker.api.world.IBiome;
import crafttweaker.mc1120.actions.ActionSetTranslation;
import crafttweaker.mc1120.brackets.*;
import crafttweaker.mc1120.entity.MCEntityDefinition;
import crafttweaker.mc1120.item.MCItemDefinition;
import crafttweaker.mc1120.liquid.MCLiquidDefinition;
import crafttweaker.mc1120.potions.*;
import net.minecraft.block.Block;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Stan
 */
public class MCGame implements IGame {
    
    public static final MCGame INSTANCE = new MCGame();
    private static final Map<String, String> TRANSLATIONS = net.minecraft.util.text.translation.I18n.localizedName.languageList;
    private static final List<IEntityDefinition> ENTITY_DEFINITIONS = new ArrayList<>();
    
    private MCGame() {
    }
    
    @Override
    public List<IItemDefinition> getItems() {
        return BracketHandlerItem.getItemNames().keySet().stream().map(item -> new MCItemDefinition(item, BracketHandlerItem.getItemNames().get(item))).collect(Collectors.toList());
    }
    
    @Override
    public List<IBlockDefinition> getBlocks() {
        return Block.REGISTRY.getKeys().stream().map(block -> CraftTweakerMC.getBlockDefinition(Block.REGISTRY.getObject(block))).collect(Collectors.toList());
    }
    
    @Override
    public List<ILiquidDefinition> getLiquids() {
        return FluidRegistry.getRegisteredFluids().entrySet().stream().map(entry -> new MCLiquidDefinition(entry.getValue())).collect(Collectors.toList());
    }
    
    @Override
    public List<IBiome> getBiomes() {
        List<IBiome> result = new ArrayList<>();
        for(IBiome biome : CraftTweakerMC.biomes) {
            if(biome != null) {
                result.add(biome);
            }
        }
        return result;
    }
    
    @Override
    public List<IPotion> getPotions() {
        ArrayList<IPotion> potions = new ArrayList<>();
        BracketHandlerPotion.getPotionNames().forEach((s, potion) -> potions.add(new MCPotion(potion)));
        return potions;
    }
    
    @Override
    public List<IEntityDefinition> getEntities() {
        if(ENTITY_DEFINITIONS.isEmpty()) {
            ForgeRegistries.ENTITIES.forEach((entry) -> ENTITY_DEFINITIONS.add(new MCEntityDefinition(entry)));
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
        for(ResourceLocation res : ForgeRegistries.ENTITIES.getKeys()) {
            if(res.getResourcePath().equalsIgnoreCase(entityName)) {
                needsReloading = true;
                break;
            }
        }
        if(needsReloading) {
            ENTITY_DEFINITIONS.clear();
            ForgeRegistries.ENTITIES.forEach((entry) -> ENTITY_DEFINITIONS.add(new MCEntityDefinition(entry)));
        }
        return getEntities().stream().filter(ent -> ent.getName().equals(entityName)).findFirst().orElse(null);
    }
    
    @Override
    public void setLocalization(String key, String value) {
        CraftTweakerAPI.apply(new ActionSetTranslation(null, key, value));
    }
    
    @Override
    public void setLocalization(String lang, String key, String value) {
        CraftTweakerAPI.apply(new ActionSetTranslation(lang, key, value));
    }
    
    @Override
    public String localize(String key) {
        return I18n.format(key);
    }
    
    @Override
    public String localize(String key, String lang) {
        return I18n.format(key);
    }
    
    public static Map<String, String> getTRANSLATIONS() {
        return TRANSLATIONS;
    }
}
