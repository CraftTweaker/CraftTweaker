package com.blamejared.crafttweaker.impl.plugin.crafttweaker;

import com.blamejared.crafttweaker.CraftTweakerCommon;
import com.blamejared.crafttweaker.api.mod.Mod;
import net.minecraft.Optionull;
import org.apache.logging.log4j.Logger;

import java.util.Arrays;
import java.util.Comparator;
import java.util.SortedSet;
import java.util.TreeSet;

final class CraftTweakerModList {
    private record DiscoveredMod(String id, String version, String[] namedSupports) {}
    
    private final SortedSet<DiscoveredMod> addedMods = new TreeSet<>(Comparator.comparing(DiscoveredMod::id));
    
    void add(final Mod info) {
        
        this.addedMods.add(this.toDiscoveredMod(info));
    }
    
    void printToLog() {
        
        final Logger logger = CraftTweakerCommon.logger();
        logger.info("The following mods have explicit CraftTweaker support, provided through the CraftTweaker compatibility plugin:");
        this.addedMods.stream().map(this::formatInfo).forEach(logger::info);
    }
    
    private DiscoveredMod toDiscoveredMod(final Mod info) {
        
        // TODO("Allow reading of named supports (e.g. CreateTweaker supports Create) from the mod file")
        // The above requires some slight reworks, but this is the foundational work
        return new DiscoveredMod(info.id(), info.version(), null);
    }
    
    private String formatInfo(final DiscoveredMod info) {
        
        return "- '%s' version '%s'%s".formatted(
                info.id(),
                info.version(),
                Optionull.mapOrElse(info.namedSupports(), it -> ", supporting mods " + Arrays.toString(it), () -> "")
        );
    }
    
}
