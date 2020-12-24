package com.blamejared.crafttweaker.api;

import net.minecraftforge.forgespi.language.IModInfo;
import net.minecraftforge.forgespi.language.ModFileScanData;

import java.util.Comparator;
import java.util.SortedSet;
import java.util.TreeSet;

public class CraftTweakerModList {
    
    private final SortedSet<IModInfo> addedInfos = new TreeSet<>(Comparator.comparing(IModInfo::getModId));
    
    public void add(ModFileScanData scanData) {
        scanData.getIModInfoData()
                .stream()
                .flatMap(info -> info.getMods().stream())
                .forEach(this::add);
    }
    
    private void add(IModInfo info) {
        addedInfos.add(info);
    }
    
    public void printToLog() {
        CraftTweakerAPI.logInfo("The following mods have explicit CraftTweaker support:");
        addedInfos.stream().map(this::formatInfo).forEach(CraftTweakerAPI::logDump);
    }
    
    private String formatInfo(IModInfo info) {
        return String.format("'%s' at version '%s'", info.getModId(), info.getVersion());
    }
}
