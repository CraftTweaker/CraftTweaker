package com.blamejared.crafttweaker.impl.plugin.crafttweaker;

import com.blamejared.crafttweaker.CraftTweakerCommon;
import com.blamejared.crafttweaker.api.CraftTweakerConstants;
import com.blamejared.crafttweaker.api.mod.PlatformMod;
import net.minecraft.Optionull;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.stream.Stream;

final class CraftTweakerModList {
    private record DiscoveredMod(String id, String version, String[] namedSupports) {}
    
    private final SortedSet<DiscoveredMod> addedMods = new TreeSet<>(Comparator.comparing(DiscoveredMod::id));
    
    void add(final PlatformMod info) {
        
        this.addedMods.add(this.toDiscoveredMod(info));
    }
    
    void printToLog() {
        
        final Logger logger = CraftTweakerCommon.logger();
        logger.info("The following mods have explicit CraftTweaker support, provided through the CraftTweaker compatibility plugin:");
        this.addedMods.stream().map(this::formatInfo).forEach(logger::info);
    }
    
    private DiscoveredMod toDiscoveredMod(final PlatformMod info) {

        return new DiscoveredMod(info.id(), info.version(), this.discoverSupports(info));
    }
    
    private String[] discoverSupports(final PlatformMod info) {
        
        // TODO("File name, location, and structure?")
        final Path supportFiles = info.file("/META-INF/" + CraftTweakerConstants.MOD_ID + "/supports.list");
        try (final Stream<String> lines = Files.lines(supportFiles)) {
            return lines.toArray(String[]::new);
        } catch (final IOException e) {
            return null;
        }
    }
    
    private String formatInfo(final DiscoveredMod info) {
        
        return "- '%s' version '%s'%s".formatted(
                info.id(),
                info.version(),
                Optionull.mapOrElse(
                        info.namedSupports(),
                        it -> ", supporting mods " + String.join(", ", it),
                        () -> ""
                )
        );
    }
    
}
