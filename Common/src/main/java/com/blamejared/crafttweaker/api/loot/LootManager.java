package com.blamejared.crafttweaker.api.loot;


import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.loot.modifier.LootModifierManager;
import com.blamejared.crafttweaker.api.loot.table.LootTableManager;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import org.openzen.zencode.java.ZenCodeGlobals;
import org.openzen.zencode.java.ZenCodeType;

/**
 * The main entry point for everything loot related.
 *
 * <p>This entry point can be obtained via the {@code loot} global in scripts and allows you to manipulate everything
 * related to loot or query loot tables.</p>
 *
 * @docParam this loot
 */
@ZenRegister
@ZenCodeType.Name("crafttweaker.api.loot.LootManager")
@Document("vanilla/api/loot/LootManager")
public enum LootManager {
    @ZenCodeGlobals.Global("loot")
    INSTANCE;
    
    /**
     * Gets the loot modifiers manager.
     *
     * <p>Refer to {@link LootModifierManager} for additional info.</p>
     *
     * @return The loot modifiers manager.
     */
    @ZenCodeType.Getter("modifiers")
    public LootModifierManager getModifierManager() {
        
        return LootModifierManager.INSTANCE;
    }
    
    /**
     * Gets the loot table manager.
     *
     * <p>Refer to {@link LootTableManager} for additional info.</p>
     *
     * @return The loot table manager.
     */
    @ZenCodeType.Getter("tables")
    public LootTableManager getTableManager() {
        
        return LootTableManager.INSTANCE;
    }
}