package com.blamejared.crafttweaker.impl.loot;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.impl.loot.modifiers.CTLootModifierManager;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import org.openzen.zencode.java.ZenCodeGlobals;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@ZenCodeType.Name("crafttweaker.api.loot.LootManager")
@Document("vanilla/api/loot/LootManager")
public final class CTLootManager {
    @ZenCodeGlobals.Global("loot")
    public static final CTLootManager LOOT_MANAGER = new CTLootManager();

    private CTLootManager() {}

    @ZenCodeType.Getter("modifiers")
    public CTLootModifierManager getModifierManager() {
        return CTLootModifierManager.LOOT_MODIFIER_MANAGER;
    }

    // TODO("Expose additional loot stuff? Like being able to query loot tables manually?")
    // TODO("Maybe other mods may want to @ZenExpand this")
}
