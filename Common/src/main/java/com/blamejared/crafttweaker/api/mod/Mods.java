package com.blamejared.crafttweaker.api.mod;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.platform.Services;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import org.openzen.zencode.java.ZenCodeGlobals;
import org.openzen.zencode.java.ZenCodeType;

import java.util.List;

/**
 * Holds information on all the mods that are registered.
 * Can be accessed using the `loadedMods` global keyword
 *
 * @docParam this loadedMods
 */
@ZenRegister
@ZenCodeType.Name("crafttweaker.api.mod.Mods")
@Document("vanilla/api/mod/Mods")
public class Mods {
    
    @ZenCodeGlobals.Global("loadedMods")
    public static final Mods INSTANCE = new Mods();
    
    private Mods() {}
    
    /**
     * Gets a list of all mods in the game
     *
     * @return list of Mod
     */
    @ZenCodeType.Method
    @ZenCodeType.Getter("mods")
    public List<Mod> getMods() {
        
        return Services.PLATFORM.getMods();
    }
    
    /**
     * Gets a specific mod
     *
     * @return a specific Mod
     *
     * @docParam modid "minecraft"
     */
    @ZenCodeType.Method
    @ZenCodeType.Nullable
    @ZenCodeType.Operator(ZenCodeType.OperatorType.MEMBERGETTER)
    public Mod getMod(String modid) {
        
        return Services.PLATFORM.getMod(modid).orElse(null);
    }
    
    @ZenCodeType.Nullable
    @ZenCodeType.Operator(ZenCodeType.OperatorType.INDEXGET)
    public Mod getModByIndex(String modid) {
        
        return Services.PLATFORM.getMod(modid).orElse(null);
    }
    
    /**
     * Checks if a mod is laoded
     *
     * @param modid modid to check
     *
     * @return true if the mod is loaded
     *
     * @docParam modid "minecraft"
     */
    @ZenCodeType.Method
    public boolean isModLoaded(String modid) {
        
        return Services.PLATFORM.isModLoaded(modid);
    }
    
    /**
     * Gets the amount of mods loaded
     *
     * @return The amount of mods that are loaded
     */
    @ZenCodeType.Method
    @ZenCodeType.Getter("size")
    public int getSize() {
        
        return getMods().size();
    }
    
}
