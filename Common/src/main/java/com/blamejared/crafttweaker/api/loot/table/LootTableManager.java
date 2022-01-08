package com.blamejared.crafttweaker.api.loot.table;


import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import org.openzen.zencode.java.ZenCodeType;

/**
 * Manager for loot tables.
 *
 * <p>An instance of this manager can be obtained via the {@link com.blamejared.crafttweaker.api.loot.LootManager}.</p>
 *
 * <p>Full capabilities are still work in progress.</p>
 *
 * @docParam this loot.tables
 */
@ZenRegister
@ZenCodeType.Name("crafttweaker.api.loot.table.LootTableManager")
@Document("vanilla/api/loot/table/LootTableManager")
public enum LootTableManager {
    INSTANCE
    
    // TODO("Loot table modifying")
    // TODO("Loot table querying")
}
