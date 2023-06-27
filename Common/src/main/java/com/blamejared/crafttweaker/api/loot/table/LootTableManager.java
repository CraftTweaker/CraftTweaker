package com.blamejared.crafttweaker.api.loot.table;


import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.LootDataManager;
import net.minecraft.world.level.storage.loot.LootDataType;
import net.minecraft.world.level.storage.loot.LootTable;
import org.openzen.zencode.java.ZenCodeType;

import java.util.HashSet;
import java.util.Set;

/**
 * Manager for loot tables.
 *
 * <p>An instance of this manager can be obtained via the {@link com.blamejared.crafttweaker.api.loot.LootManager}.</p>
 *
 * <p>These methods can only be called from the server side, so ensure that all calls are inside a {@code level.isClientSide} check or a {@code #onlyIf side server} preprocessor!</p>
 *
 * @docParam this loot.tables
 */
@ZenRegister
@ZenCodeType.Name("crafttweaker.api.loot.table.LootTableManager")
@Document("vanilla/api/loot/table/LootTableManager")
public enum LootTableManager {
    INSTANCE;
    
    /**
     * Gets a table with the given name.
     *
     * <p>If no table is registered with the name, an empty table will be returned.</p>
     *
     * @param name The name of the table to get.
     *
     * @return The found table or an empty table if not found.
     *
     * @docParam name <resource:minecraft:gameplay/cat_morning_gift>
     */
    @ZenCodeType.Method
    public LootTable getTable(ResourceLocation name) {
        
        return getLootData().getLootTable(name);
    }
    
    /**
     * Gets the ids of all registered loot tables.
     *
     * @return The ids of all registered loot tables.
     */
    @ZenCodeType.Method
    @ZenCodeType.Getter("ids")
    public Set<ResourceLocation> getIds() {
        
        return new HashSet<>(getLootData().getKeys(LootDataType.TABLE));
    }
    
    
    private LootDataManager getLootData() {
        
        if(!CraftTweakerAPI.getAccessibleElementsProvider().server().hasResources()) {
            throw new IllegalStateException("Unable to get loot tables! Make sure that this method is only called from the server side!");
        }
        return CraftTweakerAPI.getAccessibleElementsProvider().server().resources().getLootData();
    }
    
}
