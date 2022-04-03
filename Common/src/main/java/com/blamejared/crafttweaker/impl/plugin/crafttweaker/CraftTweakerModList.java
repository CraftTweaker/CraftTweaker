package com.blamejared.crafttweaker.impl.plugin.crafttweaker;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.mod.Mod;

import java.util.Comparator;
import java.util.SortedSet;
import java.util.TreeSet;

final class CraftTweakerModList {
    
    private final SortedSet<Mod> addedMods = new TreeSet<>(Comparator.comparing(Mod::id));
    
    void add(final Mod info) {
        
        this.addedMods.add(info);
    }
    
    void printToLog() {
        
        CraftTweakerAPI.LOGGER.info("The following mods have explicit CraftTweaker support through the default plugin:");
        this.addedMods.stream().map(this::formatInfo).forEach(CraftTweakerAPI.LOGGER::info);
    }
    
    private String formatInfo(final Mod info) {
        
        return "- '%s' at version '%s'".formatted(info.id(), info.version());
    }
    
}
