package com.blamejared.crafttweaker.api;

import com.blamejared.crafttweaker.api.mod.Mod;

import java.util.Comparator;
import java.util.SortedSet;
import java.util.TreeSet;

public class CraftTweakerModList {
    
    private final SortedSet<Mod> addedInfos = new TreeSet<>(Comparator.comparing(Mod::id));
    
    public void add(Mod info) {
        
        addedInfos.add(info);
    }
    
    public void printToLog() {
        
        CraftTweakerAPI.LOGGER.info("The following mods have explicit CraftTweaker support:");
        addedInfos.stream().map(this::formatInfo).forEach(CraftTweakerAPI.LOGGER::info);
    }
    
    private String formatInfo(Mod info) {
        
        return "'%s' at version '%s'".formatted(info.id(), info.version());
    }
    
}
