package com.blamejared.crafttweaker.api.mods;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.impl.entity.MCEntityType;
import com.blamejared.crafttweaker.impl.item.MCItemStack;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import net.minecraft.block.Block;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.loading.moddiscovery.ModInfo;
import net.minecraftforge.registries.ForgeRegistries;
import org.openzen.zencode.java.ZenCodeType;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Holds information about a loaded mod
 *
 * @docParam this loadedMods.getMod("crafttweaker")
 */
@ZenRegister
@ZenCodeType.Name("crafttweaker.api.mods.ModInfo")
@Document("vanilla/api/mods/ModInfo")
public class MCModInfo {
    
    private final ModInfo modInfo;
    
    public MCModInfo(ModInfo modInfo) {
        
        this.modInfo = modInfo;
    }
    
    /**
     * Gets the modid of the mod
     *
     * @return the modid of the mod
     */
    @ZenCodeType.Getter("modid")
    public String getModId() {
        
        return getModInfo().getModId();
    }
    
    /**
     * Gets the display name of the mod
     *
     * @return the display name of the mod
     */
    @ZenCodeType.Getter("displayName")
    public String getDisplayName() {
        
        return getModInfo().getDisplayName();
    }
    
    /**
     * Gets the version of the mod
     *
     * @return the version of the mod
     */
    @ZenCodeType.Getter("version")
    public String getVersion() {
        
        return getModInfo().getVersion().toString();
    }
    
    /**
     * Gets all known items from that mod
     * <p>
     * Does not take "sub items" into account!
     * That means, that it will e.g. find an enchanted book, but without tags, and only one!
     *
     * @return The items of the mod
     */
    @ZenCodeType.Getter("items")
    public List<IItemStack> getItems() {
        
        return ForgeRegistries.ITEMS.getEntries()
                .stream()
                .filter(resourceLocationItemEntry -> resourceLocationItemEntry.getKey()
                        .getLocation()
                        .getNamespace()
                        .equals(getModId()))
                .map(Map.Entry::getValue)
                .map(ItemStack::new)
                .map(MCItemStack::new)
                .collect(Collectors.toList());
    }
    
    /**
     * Finds all blocks registered for that mod.
     *
     * @return all blocks of the mod.
     */
    @ZenCodeType.Getter("blocks")
    public List<Block> getBlocks() {
        
        return ForgeRegistries.BLOCKS.getEntries()
                .stream()
                .filter(resourceLocationItemEntry -> resourceLocationItemEntry.getKey()
                        .getLocation()
                        .getNamespace()
                        .equals(getModId()))
                .map(Map.Entry::getValue)
                .collect(Collectors.toList());
    }
    
    /**
     * Finds all EntityTypes registered for that mod
     *
     * @return The EntityTypes for the mod.
     */
    @ZenCodeType.Getter("entitytypes")
    public List<MCEntityType> getEntityTypes() {
        
        return ForgeRegistries.ENTITIES.getEntries()
                .stream()
                .filter(resourceLocationItemEntry -> resourceLocationItemEntry.getKey()
                        .getLocation()
                        .getNamespace()
                        .equals(getModId()))
                .map(Map.Entry::getValue)
                .map(MCEntityType::new)
                .collect(Collectors.toList());
    }
    
    /**
     * Finds all EntityTypes registered for that mod
     *
     * @return The EntityTypes for the mod.
     */
    @ZenCodeType.Getter("fluids")
    public List<Fluid> getFluids() {
        
        return ForgeRegistries.FLUIDS.getEntries()
                .stream()
                .filter(registryKeyFluidEntry -> registryKeyFluidEntry.getKey()
                        .getLocation()
                        .getNamespace()
                        .equals(getModId()))
                .map(Map.Entry::getValue)
                .collect(Collectors.toList());
        
    }
    
    
    /**
     * Gets the namespace of the mod
     *
     * @return the namespace of the mod
     */
    @ZenCodeType.Getter("namespace")
    public String getNamespace() {
        
        return getModInfo().getModId();
    }
    
    public ModInfo getModInfo() {
        
        return modInfo;
    }
    
}
