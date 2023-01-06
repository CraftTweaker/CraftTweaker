package com.blamejared.crafttweaker.impl.plugin.crafttweaker;

import com.blamejared.crafttweaker.CraftTweakerCommon;
import com.blamejared.crafttweaker.api.mod.Mod;
import org.apache.logging.log4j.Logger;

import java.util.Comparator;
import java.util.SortedSet;
import java.util.TreeSet;

final class CraftTweakerModList {
    
    private final SortedSet<Mod> addedMods = new TreeSet<>(Comparator.comparing(Mod::id));
    
    void add(final Mod info) {
        
        this.addedMods.add(info);
    }
    
    void printToLog() {
        
        final Logger logger = CraftTweakerCommon.logger();
        logger.info("The following mods have explicit CraftTweaker support through the default plugin:");
        this.addedMods.stream().map(this::formatInfo).forEach(logger::info);
    }
    
    private String formatInfo(final Mod info) {
        
        return "- '%s' at version '%s'".formatted(info.id(), info.version());
    }
    
}
