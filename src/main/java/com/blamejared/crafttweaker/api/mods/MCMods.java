package com.blamejared.crafttweaker.api.mods;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import net.minecraftforge.fml.ModList;
import org.openzen.zencode.java.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Holds information on all the mods that are registered.
 * Can be accessed using the `loadedMods` global keyword
 * 
 * @docParam this loadedMods
 */
@ZenRegister
@ZenCodeType.Name("crafttweaker.api.mods.Mods")
@Document("vanilla/api/mods/Mods")
public class MCMods {
    
    /**
     * Gets a list of all mods in the game
     *
     * @return list of MCModInfo
     */
    @ZenCodeType.Getter("mods")
    public List<MCModInfo> getMods() {
        return ModList.get().getMods().stream().map(MCModInfo::new).collect(Collectors.toList());
    }
    
    /**
     * Gets a specific mod
     *
     * @return a specific MCModInfo
     * @docParam modid "minecraft"
     */
    @ZenCodeType.Method
    public MCModInfo getMod(String modid) {
        return ModList.get().getMods().stream().filter(modInfo -> modInfo.getModId().equals(modid)).map(MCModInfo::new).findFirst().orElseThrow(() -> new IllegalArgumentException("No modid with name: \"" + modid + "\"!"));
    }
    
    /**
     * Checks if a mod is laoded
     *
     * @param modid modid to check
     *
     * @return true if the mod is loaded
     * @docParam modid "minecraft"
     */
    @ZenCodeType.Method
    public boolean isModLoaded(String modid) {
        return ModList.get().isLoaded(modid);
    }
    
    /**
     * Gets the amount of mods loaded
     *
     * @return The amount of mods that are loaded
     */
    @ZenCodeType.Getter("size")
    public int getSize() {
        return ModList.get().size();
    }
    
}
