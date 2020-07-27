package com.blamejared.crafttweaker.api.mods;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.impl.blocks.MCBlock;
import com.blamejared.crafttweaker.impl.entity.MCEntityType;
import com.blamejared.crafttweaker.impl.item.MCItemStack;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.loading.moddiscovery.ModInfo;
import net.minecraftforge.registries.ForgeRegistries;
import org.openzen.zencode.java.ZenCodeType;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
    
    @ZenCodeType.Getter("items")
    public List<IItemStack> getItems() {
        return ForgeRegistries.ITEMS.getEntries().stream()
                .filter(resourceLocationItemEntry -> resourceLocationItemEntry.getKey().getNamespace().equals(getModId()))
                .map(Map.Entry::getValue)
                .map(ItemStack::new)
                .map(MCItemStack::new)
                .collect(Collectors.toList());
    }
    
    @ZenCodeType.Getter("blocks")
    public List<MCBlock> getBlocks() {
        return ForgeRegistries.BLOCKS.getEntries().stream()
                .filter(resourceLocationItemEntry -> resourceLocationItemEntry.getKey().getNamespace().equals(getModId()))
                .map(Map.Entry::getValue)
                .map(MCBlock::new)
                .collect(Collectors.toList());
    }
    
    @ZenCodeType.Getter("entitytypes")
    public List<MCEntityType> getEntityTypes() {
        return ForgeRegistries.ENTITIES.getEntries().stream()
                .filter(resourceLocationItemEntry -> resourceLocationItemEntry.getKey().getNamespace().equals(getModId()))
                .map(Map.Entry::getValue)
                .map(MCEntityType::new)
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
