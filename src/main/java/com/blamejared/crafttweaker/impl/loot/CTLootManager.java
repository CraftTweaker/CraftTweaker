package com.blamejared.crafttweaker.impl.loot;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.impl.loot.modifiers.CTLootModifierManager;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import org.openzen.zencode.java.ZenCodeGlobals;
import org.openzen.zencode.java.ZenCodeType;

/**
 * The main entry point for everything loot related.
 *
 * This entry point can be obtained via the <code>loot</code> global in scripts and allows you to manipulate everything
 * related to loot or query loot tables.
 *
 * Other mods may also expand the current possibilities, so check their documentation to see what additional entry
 * points are available.
 *
 * @docParam this loot
 */
@ZenRegister
@ZenCodeType.Name("crafttweaker.api.loot.LootManager")
@Document("vanilla/api/loot/LootManager")
public final class CTLootManager {
    @ZenCodeGlobals.Global("loot")
    public static final CTLootManager LOOT_MANAGER = new CTLootManager();

    private CTLootManager() {}

    /**
     * Gets the loot modifiers manager.
     *
     * Refer to {@link CTLootModifierManager} for additional info.
     *
     * @return The loot modifiers manager.
     */
    @ZenCodeType.Getter("modifiers")
    public CTLootModifierManager getModifierManager() {
        return CTLootModifierManager.LOOT_MODIFIER_MANAGER;
    }

    // TODO("Expose additional loot stuff? Like being able to query loot tables manually?")
    // TODO("Maybe other mods may want to @ZenExpand this")
}
