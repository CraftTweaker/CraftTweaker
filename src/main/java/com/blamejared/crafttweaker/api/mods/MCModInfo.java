package com.blamejared.crafttweaker.api.mods;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import net.minecraftforge.fml.loading.moddiscovery.ModInfo;
import org.openzen.zencode.java.ZenCodeType;

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
